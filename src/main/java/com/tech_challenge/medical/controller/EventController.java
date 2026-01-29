package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.BufferService;
import com.tech_challenge.medical.controller.dto.EmotionEventRequest;
import com.tech_challenge.medical.controller.dto.ObjectDetectionRequest;
import com.tech_challenge.medical.controller.dto.TranscriptChunkRequest;
import com.tech_challenge.medical.domain.events.*;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.triage.Confidence;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/events")
public class EventController {
    private final BufferService bufferService;

    public EventController(BufferService bufferService) {
        this.bufferService = bufferService;
    }

    @PostMapping("/emotion")
    public ResponseEntity<Void> receiveEmotionEvent(
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody EmotionEventRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        EmotionEvent event = EmotionEvent.of(
                EmotionLabel.valueOf(request.emotion().toUpperCase()),
                Confidence.of(request.confidence()),
                Instant.parse(request.timestamp())
        );
        
        bufferService.appendEvent(id, event);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/transcript")
    public ResponseEntity<Void> receiveTranscriptChunk(
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody TranscriptChunkRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        TranscriptChunk chunk = TranscriptChunk.of(
                request.text(),
                Confidence.of(request.confidence()),
                TimeWindow.of(
                        Instant.parse(request.startTime()),
                        Instant.parse(request.endTime())
                )
        );
        
        bufferService.appendEvent(id, chunk);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/object")
    public ResponseEntity<Void> receiveObjectDetection(
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody ObjectDetectionRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        ObjectDetectionEvent detection = ObjectDetectionEvent.of(
                ObjectLabel.of(request.label()),
                Confidence.of(request.confidence()),
                FrameIndex.of(request.frameIndex())
        );
        
        bufferService.appendEvent(id, detection);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
