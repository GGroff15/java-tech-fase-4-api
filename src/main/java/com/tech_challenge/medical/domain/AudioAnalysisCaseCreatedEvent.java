package com.tech_challenge.medical.domain;

import com.tech_challenge.medical.application.AudioUploadService;
import org.springframework.context.ApplicationEvent;

public class AudioAnalysisCaseCreatedEvent extends ApplicationEvent {
    private final Long analysisCaseId;

    public AudioAnalysisCaseCreatedEvent(AudioUploadService audioUploadService, Long analysisCaseId) {
        super(audioUploadService);
        this.analysisCaseId = analysisCaseId;
    }

    public Long getAnalysisCaseId() {
        return analysisCaseId;
    }
}
