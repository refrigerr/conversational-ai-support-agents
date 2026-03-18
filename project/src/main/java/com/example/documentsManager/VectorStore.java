package com.example.documentsManager;

import java.util.ArrayList;
import java.util.List;

public class VectorStore {

    private record Entry(String chunk, List<Double> vector) {}

    private final List<Entry> entries = new ArrayList<>();

    public void add(String chunk, List<Double> vector) {
        entries.add(new Entry(chunk, vector));
    }

    public List<String> findRelevant(List<Double> queryVector, int topK) {
        return entries.stream()
                .filter(e -> cosineSimilarity(e.vector(), queryVector) > 0.3) // minimum similarity threshold
                .sorted((a, b) -> Double.compare(
                        cosineSimilarity(b.vector(), queryVector),
                        cosineSimilarity(a.vector(), queryVector)))
                .limit(topK)
                .map(Entry::chunk)
                .toList();
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.size(); i++) {
            dot   += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}