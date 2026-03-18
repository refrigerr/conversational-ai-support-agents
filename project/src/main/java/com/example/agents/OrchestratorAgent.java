package com.example.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ConversationHistory;
import com.example.Message;
import com.example.OpenAiService;

public class OrchestratorAgent extends Agent{

    public OrchestratorAgent(OpenAiService openAiService, ConversationHistory history){
        super("", openAiService, history);
    }

    @Override
    public String chat(String userMessage) {

        List<Message> recentHistory = history.getMessages().stream()
            .filter(m -> m.getRole().equals("user") || m.getRole().equals("assistant"))
            .collect(Collectors.toList());

        int start = Math.max(0, recentHistory.size() - 6);
        List<Message> context = recentHistory.subList(start, recentHistory.size());

        StringBuilder contextSummary = new StringBuilder();
        for (Message msg : context) {
            contextSummary.append(msg.getRole()).append(": ").append(msg.getContent()).append("\n");
        }

        String routingPrompt = """
                You are a routing assistant. Your only job is to classify the latest user message.
                
                Categories:
                - TECHNICAL: technical issues, integration problems, setup, configuration, API usage, errors, troubleshooting
                - BILLING: billing, charges, payments, refunds, refund policy, plans, pricing, invoices, billing history, customer accounts
                - OUT_OF_SCOPE: anything that does not fit TECHNICAL or BILLING
                
                Examples:
                "My HubSpot integration keeps failing" → TECHNICAL
                "What is my current plan?" → BILLING
                "Show me billing history for customer_001" → BILLING
                "I was charged twice" → BILLING
                "What is your refund policy?" → BILLING
                "How do I set up the API?" → TECHNICAL
                "What is the weather?" → OUT_OF_SCOPE
                
                Recent conversation:
                %s
                
                Latest user message: "%s"
                
                Reply with only the category word: TECHNICAL, BILLING, or OUT_OF_SCOPE. Nothing else.
                """.formatted(contextSummary.toString(), userMessage);

        List<Message> messages = List.of(
                new Message("system", routingPrompt),
                new Message("user", userMessage)
        );

        return openAiService.chat(messages).trim().toUpperCase();
    }
    
}
