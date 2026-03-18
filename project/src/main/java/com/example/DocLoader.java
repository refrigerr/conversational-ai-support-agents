package com.example;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DocLoader {
    
    private final List<String> rawDocuments = new ArrayList<>();

    public DocLoader() {

        loadDoc("docs/api_usage.md");
        loadDoc("docs/integration.md");
        loadDoc("docs/setup.md");
        loadDoc("docs/troubleshooting.md");

    }

    private void loadDoc(String path) {

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {

            if (is == null) {
                System.err.println("Document not found: " + path);
                return;
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            rawDocuments.add(content);
            System.out.println("Loaded: " + path);

        } catch (Exception e) {
            System.err.println("Failed to load: " + path + " - " + e.getMessage());
        }
    }
}
