package com.example;

import java.util.ArrayList;
import java.util.List;

public class ConversationHistory {
    private final List<Message> messages = new ArrayList<>();

    public void addUser(String content){ 
        messages.add(new Message("user", content)); 
    }
    public void addAssistant(String content){ 
        messages.add(new Message("assistant", content)); 
    }

    public List<Message> getMessages(){ 
        return messages; 

    }
    public void clear(){ 
        messages.clear(); 
    }
}
