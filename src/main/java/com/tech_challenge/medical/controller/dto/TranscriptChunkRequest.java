package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TranscriptChunkRequest(
        @NotBlank String text,
        @NotNull Double confidence,
        @NotBlank String startTime,
        @NotBlank String endTime
) {
}
