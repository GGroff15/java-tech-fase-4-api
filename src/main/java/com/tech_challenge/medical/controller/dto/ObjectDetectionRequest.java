package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ObjectDetectionRequest(
        @NotBlank String label,
        @NotNull Double confidence,
        @NotNull Long frameIndex
) {
}
