package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.application.text_extractor.TextExtractorStrategyResolver;
import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;
import com.tech_challenge.medical.domain.RiskLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportProcessorStrategy implements ProcessorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportProcessorStrategy.class);

    private final TextExtractorStrategyResolver textExtractor;

    public ReportProcessorStrategy(TextExtractorStrategyResolver textExtractor) {
        this.textExtractor = textExtractor;
    }

    @Override
    public AnalysisResult process(AnalysisCase analysisCase) {
        LOGGER.info("Processing report analysis case {}", analysisCase.getId());

        String rawFilePath = analysisCase.getRawFilePath();

        String fileText = textExtractor.extract(rawFilePath);

        // Send text to AI model for analysis
        System.out.println(fileText);

        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setRiskLevel(RiskLevel.MEDIUM);
        analysisResult.setFindings("");
        analysisResult.setSummary("");
        analysisResult.setAnalysisCaseId(analysisCase.getId());

        LOGGER.info("Processed report analysis case {}", analysisCase.getId());

        return analysisResult;
    }
}
