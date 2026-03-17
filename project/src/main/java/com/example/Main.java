package com.example;

import java.util.List;

public class Main 
{
    public static void main( String[] args )
    {
        OpenAiService openAiService = new OpenAiService();
        ConversationHistory conversationHistory = new ConversationHistory();
        ExampleAgent exampleAgent = new ExampleAgent(openAiService, conversationHistory);
        
        System.out.println(exampleAgent.chat("Hi my name is Jan"));
        System.out.println(exampleAgent.chat("What is my name"));
        
    }
}
