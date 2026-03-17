package com.example;

import java.util.ArrayList;
import java.util.List;

public class ExampleAgent extends Agent{

    public ExampleAgent(OpenAiService openAiService, ConversationHistory history){
        super("You are funny agent, you always respond in a funny way", openAiService, history);
    }

    @Override
    public String chat(String userMessage) {
        history.addUser(userMessage);
        String response = openAiService.chat(buildMessages());
        history.addAssistant(response);
        return response;
    }
   
}
