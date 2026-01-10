package com.tech_challenge.medical.api;

import com.tech_challenge.medical.api.dto.AudioUploadRequest;
import com.tech_challenge.medical.application.service.UploadAudioService;
import com.tech_challenge.medical.domain.AudioUpload;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/audios")
class AudioController {

    private final UploadAudioService service;

    AudioController(UploadAudioService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> upload(@Valid AudioUploadRequest request) throws IOException {

        AudioUpload audioUpload = new AudioUpload(request.file(), request.patientId(), request.source());

        service.execute(audioUpload);

        return ResponseEntity.ok().build();
    }

}
