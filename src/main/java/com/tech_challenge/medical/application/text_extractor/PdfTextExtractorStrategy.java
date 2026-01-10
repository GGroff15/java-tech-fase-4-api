package com.tech_challenge.medical.application.text_extractor;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PdfTextExtractorStrategy implements TextExtractorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfTextExtractorStrategy.class);

    @Override
    public String extractText(String filePath) {

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (Exception e) {
            LOGGER.error("Error on extracting text from PDF {}", filePath, e);
        }

        return "";
    }
}
