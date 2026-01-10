package com.tech_challenge.medical.application.service;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AudioUpload;
import com.tech_challenge.medical.domain.Status;
import com.tech_challenge.medical.domain.Type;
import com.tech_challenge.medical.domain.event.AnalysisCaseCreatedEvent;
import com.tech_challenge.medical.infraestructure.AnalysisCaseRepository;
import com.tech_challenge.medical.infraestructure.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploadAudioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadAudioService.class);

    private final FileStorage storage;
    private final AnalysisCaseRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public UploadAudioService(FileStorage storage, AnalysisCaseRepository repository, ApplicationEventPublisher eventPublisher) {
        this.storage = storage;
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(AudioUpload audioUpload) throws IOException {
        LOGGER.info("Uploading Audio File");

        String rawFilePath = storage.store(audioUpload.file());

        AnalysisCase newAnalysisCase = new AnalysisCase();
        newAnalysisCase.setPatientId(audioUpload.patientId());
        newAnalysisCase.setType(Type.AUDIO);
        newAnalysisCase.setStatus(Status.PENDING);
        newAnalysisCase.setRawFilePath(rawFilePath);
        newAnalysisCase.setSource(audioUpload.source());

        AnalysisCase saved = repository.save(newAnalysisCase);

        LOGGER.info("Audio AnalysisCase created with ID: {}", saved.getId());

        LOGGER.info("Sending AudioAnalysisCaseCreatedEvent for AnalysisCase ID: {}", saved.getId());
        eventPublisher.publishEvent(new AnalysisCaseCreatedEvent(this, saved.getId()));
    }

}
