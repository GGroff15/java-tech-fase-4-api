package com.tech_challenge.medical.application.text_extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TxtTextExtractorStrategy implements TextExtractorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtTextExtractorStrategy.class);

    @Override
    public String extractText(String filePath) {
        File file = new File(filePath);

        try {
            Files.readString(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Error reading TXT file: {}", filePath, e);
        }

        return "";
    }
}
