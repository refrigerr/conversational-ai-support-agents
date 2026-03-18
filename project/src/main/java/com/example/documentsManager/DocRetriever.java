package com.example.documentsManager;

import java.util.List;

public class DocRetriever {

    private final List<String> chunks;

    public DocRetriever(List<String> chunks) {
        this.chunks = chunks;
    }

    public String retrieveRelevant(String userMessage) {
        String[] queryWords = userMessage.toLowerCase().split("\\s+");

        List<String> top = chunks.stream()
                .filter(chunk -> score(chunk.toLowerCase(), queryWords) > 0)
                .sorted((a, b) -> Integer.compare(
                        score(b.toLowerCase(), queryWords),
                        score(a.toLowerCase(), queryWords)))
                .limit(3)
                .toList();

        if (top.isEmpty()) {
            return "No relevant documentation found.";
        }

        return String.join("\n\n---\n\n", top);
    }

    private int score(String chunk, String[] queryWords) {
        int score = 0;
        for (String word : queryWords) {
            if (word.length() > 3 && chunk.contains(word)) {
                score++;
            }
        }
        return score;
    }
}
