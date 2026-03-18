package com.example.agents;

import java.util.ArrayList;
import java.util.List;

import com.example.ConversationHistory;
import com.example.Message;
import com.example.OpenAiService;
import com.example.documentsManager.DocRetriever;

public class TechnicalAgent extends Agent{

    private final DocRetriever docRetriever;

    public TechnicalAgent(OpenAiService openAiService, ConversationHistory history, DocRetriever docRetriever) {
        super("""
                You are a Technical Specialist. Your job is to help users with technical questions.
                Answer ONLY using the documentation provided below.
                If the answer is not present in the documentation:
                - Ask the user for clarification, or
                - Clearly state that the information is not available.
                Do NOT guess or make up answers.
                """, openAiService, history);
        this.docRetriever = docRetriever;
    }

    @Override
    public String chat(String userMessage) {

        String relevantChunks = docRetriever.retrieveRelevant(userMessage);

        String systemWithChunks = systemPrompt + "\n\nDocumentation:\n" + relevantChunks;

        history.addUser(userMessage);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", systemWithChunks));
        messages.addAll(history.getMessages());

        String response = openAiService.chat(messages);
        history.addAssistant(response);
        return response;

        
    }
}
