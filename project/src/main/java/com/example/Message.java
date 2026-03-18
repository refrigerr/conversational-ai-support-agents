package com.example;

import org.json.JSONArray;

public class Message {
    private final String role;
    private final String content;
    private final String toolCallId;
    private final JSONArray toolCalls;

    // regular message
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
        this.toolCallId = null;
        this.toolCalls = null;
    }

    // tool result message (role = "tool")
    public Message(String role, String content, String toolCallId) {
        this.role = role;
        this.content = content;
        this.toolCallId = toolCallId;
        this.toolCalls = null;
    }

    // assistant message with tool_calls
    public Message(String role, JSONArray toolCalls) {
        this.role = role;
        this.content = null;
        this.toolCallId = null;
        this.toolCalls = toolCalls;
    }

    public String getRole()         { return role; }
    public String getContent()      { return content; }
    public String getToolCallId()   { return toolCallId; }
    public JSONArray getToolCalls() { return toolCalls; }
}