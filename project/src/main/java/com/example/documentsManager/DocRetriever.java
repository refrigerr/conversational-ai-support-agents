package com.example.documentsManager;

import java.util.List;

import com.example.OpenAiService;

public class DocRetriever {

    private final VectorStore vectorStore;
    private final OpenAiService openAiService;

    public DocRetriever(List<String> chunks, OpenAiService openAiService) {
        this.vectorStore = new VectorStore();
        this.openAiService = openAiService;
        embedChunks(chunks);
    }

    private void embedChunks(List<String> chunks) {
        //System.out.println("Embedding " + chunks.size() + " chunks...");
        for (String chunk : chunks) {
            List<Double> vector = openAiService.embed(chunk);
            vectorStore.add(chunk, vector);
        }
        //System.out.println("Embedding complete.");
    }

    public String retrieveRelevant(String userMessage) {
        List<Double> queryVector = openAiService.embed(userMessage);
        List<String> relevant = vectorStore.findRelevant(queryVector, 3); 

        if (relevant.isEmpty()) {
            //System.out.println("[DocRetriever] No relevant chunks found for: \"" + userMessage + "\"");
            return "No relevant documentation found.";
        }
        // System.out.println("[DocRetriever] Found " + relevant.size() + " relevant chunks for: \"" + userMessage + "\"");
        // for (int i = 0; i < relevant.size(); i++) {
        //     // print just the first line of each chunk (usually the ## heading)
        //     System.out.println("------------ Chunk " + (i + 1) + " ------------");
        //     System.out.println(relevant.get(i));
        // }

        return String.join("\n\n---\n\n", relevant);
    }
}
