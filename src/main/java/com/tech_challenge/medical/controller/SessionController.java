package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.SessionService;
import com.tech_challenge.medical.controller.dto.SessionCreatedResponse;
import com.tech_challenge.medical.domain.session.CorrelationId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionCreatedResponse> createSession() {
        CorrelationId correlationId = sessionService.createSession();
        SessionCreatedResponse response = SessionCreatedResponse.of(correlationId.asString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
