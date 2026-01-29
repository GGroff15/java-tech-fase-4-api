package com.tech_challenge.medical.controller.dto;

import java.util.List;

public record TriageResultResponse(
        String triageLevel,
        List<String> riskFactors,
        List<String> inconsistencies,
        String notesForPhysician,
        Double confidence
) {
}
