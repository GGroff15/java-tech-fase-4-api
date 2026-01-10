package com.tech_challenge.medical.application.text_extractor;

import org.springframework.stereotype.Component;

@Component
public class TextExtractorStrategyResolver {

    public String extract(String filePath) {
        String extension = getExtension(filePath);

        TextExtractorStrategy strategy = switch (extension.toLowerCase()) {
            case "pdf" -> new PdfTextExtractorStrategy();
            case "txt" -> new TxtTextExtractorStrategy();
            default -> throw new IllegalArgumentException("Unsupported file extension: " + extension);
        };

        return strategy.extractText(filePath);
    }

    private static String getExtension(String rawFilePath) {
        int dotIndex = rawFilePath.lastIndexOf(".");
        if (dotIndex >= 0) {
            return rawFilePath.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

}
