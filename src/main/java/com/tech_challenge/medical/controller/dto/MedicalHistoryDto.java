package com.tech_challenge.medical.controller.dto;

import java.util.List;

public record MedicalHistoryDto(
        List<String> conditions,
        List<String> medications,
        List<String> allergies
) {
}
