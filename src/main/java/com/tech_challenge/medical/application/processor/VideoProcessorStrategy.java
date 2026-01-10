package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VideoProcessorStrategy implements ProcessorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoProcessorStrategy.class);

    @Override
    public AnalysisResult process(AnalysisCase newAnalysisCase) {
        LOGGER.info("Processing Video Processor Strategy");
        return new AnalysisResult();
    }
}
