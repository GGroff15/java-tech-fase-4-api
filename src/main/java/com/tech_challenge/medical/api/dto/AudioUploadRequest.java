package com.tech_challenge.medical.api.dto;

import com.tech_challenge.medical.api.validation.FileType;
import org.springframework.web.multipart.MultipartFile;

public record AudioUploadRequest(
        @FileType(types = {"audio/mpeg", "audio/wav", "audio/mp3"})
        MultipartFile file,
        Long patientId,
        String source
) {
}
