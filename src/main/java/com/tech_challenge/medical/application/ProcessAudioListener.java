package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.AudioAnalysisCaseCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class ProcessAudioListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessAudioListener.class);

    @Async
    @EventListener
    void on(AudioAnalysisCaseCreatedEvent event) throws InterruptedException {
        LOGGER.info("Processing Audio for AnalysisCase ID: {}", event.getAnalysisCaseId());

        Thread.sleep(10000); // Simulate processing delay

        LOGGER.info("Processed Audio for AnalysisCase ID: {}", event.getAnalysisCaseId());
    }

}
