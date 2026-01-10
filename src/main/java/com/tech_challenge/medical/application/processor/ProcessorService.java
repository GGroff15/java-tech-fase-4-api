package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorService.class);

    private final ReportProcessorStrategy reportProcessor;
    private final VideoProcessorStrategy videoProcessor;
    private final AudioProcessorStrategy audioProcessor;

    public ProcessorService(ReportProcessorStrategy reportProcessor, VideoProcessorStrategy videoProcessor, AudioProcessorStrategy audioProcessor) {
        this.reportProcessor = reportProcessor;
        this.videoProcessor = videoProcessor;
        this.audioProcessor = audioProcessor;
    }

    public AnalysisResult process(AnalysisCase analysisCase) {
        LOGGER.info("Processing analysis case {}, type {}", analysisCase.getId(), analysisCase.getType());

        return switch (analysisCase.getType()) {
            case REPORT -> reportProcessor.process(analysisCase);
            case VIDEO -> videoProcessor.process(analysisCase);
            case AUDIO -> audioProcessor.process(analysisCase);
        };
    }

}
