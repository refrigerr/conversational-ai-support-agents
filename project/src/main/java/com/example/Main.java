package com.example;

import java.util.List;

import com.example.agents.Agent;
import com.example.agents.TechnicalAgent;
import com.example.documentsManager.DocLoader;
import com.example.documentsManager.DocRetriever;

public class Main 
{
    public static void main( String[] args )
    {
        
        ConversationHistory conversationHistory = new ConversationHistory();
        OpenAiService openAiService = new OpenAiService();
        DocLoader docLoader = new DocLoader();
        List<String> chunks = docLoader.getChunks();
        DocRetriever docRetriever = new DocRetriever(chunks);

        
        Agent technicalAgent = new TechnicalAgent(openAiService, conversationHistory, docRetriever);
        String resposne = technicalAgent.chat("What is today weather");
        System.out.println(resposne);
        
    }
}
