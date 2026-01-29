package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmotionEventRequest(
        @NotBlank String emotion,
        @NotNull Double confidence,
        @NotBlank String timestamp
) {
}
