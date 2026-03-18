package com.example;

import java.util.List;

import com.example.agents.Agent;
import com.example.agents.ExampleAgent;
import com.example.agents.OrchestratorAgent;

public class Main 
{
    public static void main( String[] args )
    {
        OpenAiService openAiService = new OpenAiService();
        ConversationHistory conversationHistory = new ConversationHistory();
        Agent exampleAgent = new OrchestratorAgent(openAiService, conversationHistory);
        
        System.out.println(exampleAgent.chat("How do i connect my app with yours?"));
        
        
    }
}
