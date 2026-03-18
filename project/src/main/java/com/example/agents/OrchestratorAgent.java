package com.example.agents;

import java.util.List;

import com.example.ConversationHistory;
import com.example.Message;
import com.example.OpenAiService;

public class OrchestratorAgent extends Agent{

    public OrchestratorAgent(OpenAiService openAiService, ConversationHistory history){
        super("""
            You are a routing assistant. Classify the user message into exactly one of these categories:
            
            - TECHNICAL: anything related to technical issues, integration problems, setup, configuration, API usage, errors, troubleshooting
            - BILLING: anything related to billing, charges, payments, refunds, plans, pricing, invoices, billing history, customer accounts
            - OUT_OF_SCOPE: anything that does not fit TECHNICAL or BILLING
            
            Examples:
            "My HubSpot integration keeps failing" → TECHNICAL
            "What is my current plan?" → BILLING
            "Show me billing history for customer_001" → BILLING
            "I was charged twice" → BILLING
            "How do I set up the API?" → TECHNICAL
            "What is the weather?" → OUT_OF_SCOPE
            
            Reply with only the category word: TECHNICAL, BILLING, or OUT_OF_SCOPE. Nothing else.
            """, 
            openAiService, history);
    }

    @Override
    public String chat(String userMessage) {

        List<Message> messages = List.of(
                new Message("system", systemPrompt),
                new Message("user", userMessage)
        );
        return openAiService.chat(messages).trim().toUpperCase();
    }
    
}
