package com.example;

import org.json.JSONObject;
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

    public String chat(String userMessage) throws Exception {
        String body = """
                {
                    "model": "gpt-4o-mini",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ]
                }
                """.formatted(userMessage);

        Map<String, String> headers = Map.of(
                "Content-Type", "application/json",
                "Authorization", "Bearer " + apiKey
        );

        String rawResponse = httpClient.post(API_URL, body, headers);
        JSONObject json = new JSONObject(rawResponse);

        return json
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
    
}
