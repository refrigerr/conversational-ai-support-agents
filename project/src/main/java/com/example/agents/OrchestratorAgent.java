package com.example.agents;

import com.example.ConversationHistory;
import com.example.OpenAiService;

public class OrchestratorAgent extends Agent{

    public OrchestratorAgent(OpenAiService openAiService, ConversationHistory history){
        super("""
            You are a classifing expert. Your task is to classify user's message into one of the following categories:
            - TECHNICAL (integration, setup, API, configuration issues etc.)
            - BILLING (charges, refunds, plans, billing history)
            - OUT_OF_SCOPE (anything else)

            Reply only with the category word, that best discribes user's message, and nothing else.
            """, 
            openAiService, history);
    }

    @Override
    public String chat(String userMessage){
        String response = openAiService.chat(buildMessages());
        return response;
    }
    
}
