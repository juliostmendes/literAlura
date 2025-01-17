package com.juliostmendes.literAlura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juliostmendes.literAlura.model.AuthorData;
import com.juliostmendes.literAlura.model.BookData;

public class ApiConverter {
    public static BookData convertToBookData(JsonNode resultsNode) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Extract the first language from the "languages" array
            String firstLanguage = null;
            JsonNode languagesNode = resultsNode.get("languages");
            if (languagesNode != null && languagesNode.isArray() && languagesNode.size() > 0) {
                firstLanguage = languagesNode.get(0).asText();
            }

            // Extract the first author from the "authors" array
            AuthorData firstAuthor = null;
            JsonNode authorsNode = resultsNode.get("authors");
            if (authorsNode != null && authorsNode.isArray() && authorsNode.size() > 0) {
                JsonNode authorNode = authorsNode.get(0); // Get the first author
                firstAuthor = objectMapper.treeToValue(authorNode, AuthorData.class);
            }

            // Create a BookData instance manually
            return new BookData(
                    resultsNode.get("title").asText(), // Extract "title"
                    firstLanguage,                      // First language
                    firstAuthor                         // First author
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to BookData", e);
        }
    }
}
