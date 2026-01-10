package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AudioProcessorStrategy implements ProcessorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioProcessorStrategy.class);

    @Override
    public AnalysisResult process(AnalysisCase newAnalysisCase) {
        LOGGER.info("Processing Audio Processor Strategy");
        return new AnalysisResult();
    }
}
