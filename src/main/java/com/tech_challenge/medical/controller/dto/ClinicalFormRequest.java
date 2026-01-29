package com.tech_challenge.medical.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ClinicalFormRequest(
        @Valid @NotNull PatientComplaintDto complaint,
        @Valid @NotNull VitalSignsDto vitalSigns,
        @Valid @NotNull MedicalHistoryDto medicalHistory
) {
}
