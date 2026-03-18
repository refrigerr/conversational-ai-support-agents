package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class OpenAiService {

    private final HttpClientService httpClient;
    private final String apiKey;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiService() {
        this.httpClient = new HttpClientService();
        this.apiKey = System.getenv("OPENAI_API_KEY");

        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IllegalStateException("OPENAI_API_KEY environment variable is not set");
        }
    }

    public String chat(List<Message> messages) {
        try {
            JSONArray messagesArray = buildMessagesArray(messages);

            String body = new JSONObject()
                    .put("model", "gpt-4o-mini")
                    .put("messages", messagesArray)
                    .toString();

            Map<String, String> headers = Map.of(
                    "Content-Type", "application/json",
                    "Authorization", "Bearer " + apiKey
            );

            String rawResponse = httpClient.post(API_URL, body, headers);

            if (rawResponse == null) {
                return "I'm sorry, I couldn't reach the AI service. Please try again.";
            }

            JSONObject json = new JSONObject(rawResponse);
            return json
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

        } catch (Exception e) {
            System.err.println("LLM call failed: " + e.getMessage());
            return "I'm sorry, I encountered an error. Please try again.";
        }
    }

    public String chatWithTools(List<Message> messages, JSONArray tools) {
        try {
            JSONArray messagesArray = buildMessagesArray(messages);

            String body = new JSONObject()
                    .put("model", "gpt-4o-mini")
                    .put("messages", messagesArray)
                    .put("tools", tools)
                    .toString();

            Map<String, String> headers = Map.of(
                    "Content-Type", "application/json",
                    "Authorization", "Bearer " + apiKey
            );

            return httpClient.post(API_URL, body, headers); // return raw response so BillingAgent can inspect tool_calls

        } catch (Exception e) {
            System.err.println("LLM call failed: " + e.getMessage());
            return null;
        }
    }

    // extract into shared helper so both chat() and chatWithTools() use it
    private JSONArray buildMessagesArray(List<Message> messages) {
        JSONArray array = new JSONArray();
        for (Message message : messages) {
            JSONObject obj = new JSONObject();
            obj.put("role", message.getRole());

            if (message.getContent() != null) {
                obj.put("content", message.getContent());
            } else {
                obj.put("content", JSONObject.NULL);
            }

            if (message.getToolCalls() != null) {
                obj.put("tool_calls", message.getToolCalls());
            }

            if (message.getToolCallId() != null) {
                obj.put("tool_call_id", message.getToolCallId());
            }

            array.put(obj);
        }
        return array;
    }
    
}
