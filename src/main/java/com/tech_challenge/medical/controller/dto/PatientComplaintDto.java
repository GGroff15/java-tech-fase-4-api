package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PatientComplaintDto(
        @NotBlank String description,
        @NotBlank String duration,
        @NotBlank String severity
) {
}
