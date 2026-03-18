package com.example.agents;

import java.util.ArrayList;
import java.util.List;

import com.example.ConversationHistory;
import com.example.Message;
import com.example.OpenAiService;

public abstract class Agent {

    protected final OpenAiService openAiService;
    protected final ConversationHistory history;
    protected final String systemPrompt;

    public Agent(String systemPrompt, OpenAiService openAiService, ConversationHistory history) {
        this.systemPrompt = systemPrompt;
        this.openAiService = openAiService;
        this.history = history;
    }

    protected List<Message> buildMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", systemPrompt));  // agent's own system prompt
        messages.addAll(history.getMessages());             // shared conversation history
        return messages;
    }

    public abstract String chat(String userMessage);
    
}
