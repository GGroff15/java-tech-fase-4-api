package com.tech_challenge.medical.api;

import com.tech_challenge.medical.api.dto.TextUploadRequest;
import com.tech_challenge.medical.domain.TextUpload;
import com.tech_challenge.medical.application.service.UploadReportService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RestController
@RequestMapping("/api/texts")
class TextController {

    private final UploadReportService service;

    TextController(UploadReportService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@Valid TextUploadRequest request) throws IOException {

        TextUpload textUpload = new TextUpload(request.file(), request.patientId(), request.source());

        service.execute(textUpload);

        return ResponseEntity.ok().build();
    }

}
