package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpClientService {

    private final HttpClient client;

    public HttpClientService() {
        this.client = HttpClient.newBuilder().build();
    }

    public String get(String url, Map<String, String> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET();

            headers.forEach(requestBuilder::header);

            HttpResponse<String> response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("GET request failed for URL: " + url, e);
        }
    }

    public String post(String url, String jsonBody, Map<String, String> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
                
            headers.forEach(requestBuilder::header);

            HttpRequest request = requestBuilder.build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("Post request failed for URL: " + url, e);
        }

    }
   
}
