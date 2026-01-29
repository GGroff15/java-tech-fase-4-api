package com.tech_challenge.medical.controller.dto;

public record SessionCreatedResponse(
        String correlationId
) {
    public static SessionCreatedResponse of(String correlationId) {
        return new SessionCreatedResponse(correlationId);
    }
}
