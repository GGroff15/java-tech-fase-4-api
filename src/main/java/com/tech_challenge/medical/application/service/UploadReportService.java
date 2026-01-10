package com.tech_challenge.medical.application.service;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.TextUpload;
import com.tech_challenge.medical.domain.Type;
import com.tech_challenge.medical.domain.event.AnalysisCaseCreatedEvent;
import com.tech_challenge.medical.domain.Status;
import com.tech_challenge.medical.infraestructure.AnalysisCaseRepository;
import com.tech_challenge.medical.infraestructure.FileStorage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploadReportService {

    private final FileStorage fileStorage;
    private final AnalysisCaseRepository analysisCaseRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UploadReportService(FileStorage fileStorage, AnalysisCaseRepository analysisCaseRepository, ApplicationEventPublisher eventPublisher) {
        this.fileStorage = fileStorage;
        this.analysisCaseRepository = analysisCaseRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(TextUpload textUpload) throws IOException {
        String rawFilePath = fileStorage.store(textUpload.file());

        AnalysisCase newAnalysisCase = new AnalysisCase();

        newAnalysisCase.setPatientId(textUpload.patientId());
        newAnalysisCase.setType(Type.REPORT);
        newAnalysisCase.setStatus(Status.PENDING);
        newAnalysisCase.setRawFilePath(rawFilePath);
        newAnalysisCase.setSource(textUpload.source());

        AnalysisCase saved = analysisCaseRepository.save(newAnalysisCase);

        eventPublisher.publishEvent(new AnalysisCaseCreatedEvent(this, saved.getId()));
    }
}
