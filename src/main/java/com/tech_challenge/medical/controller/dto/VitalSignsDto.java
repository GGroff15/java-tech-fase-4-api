package com.tech_challenge.medical.controller.dto;

public record VitalSignsDto(
        Integer heartRate,
        Integer systolicBp,
        Integer diastolicBp,
        Double temperature,
        Integer oxygenSaturation
) {
}
