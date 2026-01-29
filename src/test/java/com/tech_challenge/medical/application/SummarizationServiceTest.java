package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.events.*;
import com.tech_challenge.medical.domain.form.*;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.domain.summary.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SummarizationService tests")
class SummarizationServiceTest {

    private final SummarizationService service = new SummarizationService();

    @Nested
    @DisplayName("when summarizing a complete session")
    class CompleteSummarization {

        @Test
        @DisplayName("produces a complete clinical summary")
        void producesCompleteSummary() {
            // Given
            SessionBuffer buffer = createBufferWithEvents();
            ClinicalForm form = createCompleteForm();

            // When
            ClinicalSummary summary = service.summarize(buffer, form);

            // Then
            assertAll(
                    () -> assertNotNull(summary.formData()),
                    () -> assertNotNull(summary.emotionSummary()),
                    () -> assertNotNull(summary.transcriptSummary()),
                    () -> assertNotNull(summary.videoSummary()),
                    () -> assertNotNull(summary.sessionStart()),
                    () -> assertNotNull(summary.sessionEnd())
            );
        }

        @Test
        @DisplayName("captures dominant emotion correctly")
        void capturesDominantEmotion() {
            // Given
            SessionBuffer buffer = createBufferWithEmotions();
            ClinicalForm form = createCompleteForm();

            // When
            ClinicalSummary summary = service.summarize(buffer, form);

            // Then
            EmotionLabel dominant = summary.emotionSummary().dominantEmotion();
            assertEquals(EmotionLabel.ANXIOUS, dominant);
        }

        @Test
        @DisplayName("consolidates transcript chunks in time order")
        void consolidatesTranscripts() {
            // Given
            SessionBuffer buffer = createBufferWithTranscripts();
            ClinicalForm form = createCompleteForm();

            // When
            ClinicalSummary summary = service.summarize(buffer, form);

            // Then
            String consolidated = summary.transcriptSummary().consolidatedText();
            assertTrue(consolidated.contains("chest pain"));
            assertTrue(consolidated.contains("shortness of breath"));
        }

        @Test
        @DisplayName("identifies top frequent objects")
        void identifiesTopObjects() {
            // Given
            SessionBuffer buffer = createBufferWithObjects();
            ClinicalForm form = createCompleteForm();

            // When
            ClinicalSummary summary = service.summarize(buffer, form);

            // Then
            VideoSummary videoSummary = summary.videoSummary();
            assertTrue(videoSummary.mostFrequentObjects().size() <= 5);
        }
    }

    @Nested
    @DisplayName("when validating inputs")
    class ValidationTests {

        @Test
        @DisplayName("throws exception when buffer is null")
        void throwsOnNullBuffer() {
            // Given
            ClinicalForm form = createCompleteForm();

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    service.summarize(null, form)
            );
        }

        @Test
        @DisplayName("throws exception when form is null")
        void throwsOnNullForm() {
            // Given
            SessionBuffer buffer = createBufferWithEvents();

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> 
                    service.summarize(buffer, null)
            );
        }
    }

    @Nested
    @DisplayName("when handling empty buffers")
    class EmptyBufferTests {

        @Test
        @DisplayName("handles session with no events")
        void handlesEmptyEvents() {
            // Given
            SessionBuffer buffer = createEmptyBuffer();
            ClinicalForm form = createCompleteForm();

            // When
            ClinicalSummary summary = service.summarize(buffer, form);

            // Then
            assertAll(
                    () -> assertEquals("", summary.transcriptSummary().consolidatedText()),
                    () -> assertEquals(0, summary.videoSummary().totalDetections()),
                    () -> assertNotNull(summary.emotionSummary().dominantEmotion())
            );
        }
    }

    private SessionBuffer createBufferWithEvents() {
        SessionBuffer buffer = SessionBuffer.create(
                com.tech_challenge.medical.domain.session.CorrelationId.generate()
        );
        
        BufferedEvents events = BufferedEvents.empty()
                .addEmotion(createEmotionEvent(EmotionLabel.ANXIOUS))
                .addTranscript(createTranscriptChunk("chest pain"))
                .addObject(createObjectDetection("wheelchair"));
        
        return buffer.updateEvents(events);
    }

    private SessionBuffer createBufferWithEmotions() {
        SessionBuffer buffer = SessionBuffer.create(
                com.tech_challenge.medical.domain.session.CorrelationId.generate()
        );
        
        BufferedEvents events = BufferedEvents.empty()
                .addEmotion(createEmotionEvent(EmotionLabel.ANXIOUS))
                .addEmotion(createEmotionEvent(EmotionLabel.ANXIOUS))
                .addEmotion(createEmotionEvent(EmotionLabel.SAD));
        
        return buffer.updateEvents(events);
    }

    private SessionBuffer createBufferWithTranscripts() {
        SessionBuffer buffer = SessionBuffer.create(
                com.tech_challenge.medical.domain.session.CorrelationId.generate()
        );
        
        BufferedEvents events = BufferedEvents.empty()
                .addTranscript(createTranscriptChunk("chest pain"))
                .addTranscript(createTranscriptChunk("shortness of breath"));
        
        return buffer.updateEvents(events);
    }

    private SessionBuffer createBufferWithObjects() {
        SessionBuffer buffer = SessionBuffer.create(
                com.tech_challenge.medical.domain.session.CorrelationId.generate()
        );
        
        BufferedEvents events = BufferedEvents.empty()
                .addObject(createObjectDetection("wheelchair"))
                .addObject(createObjectDetection("crutches"))
                .addObject(createObjectDetection("wheelchair"));
        
        return buffer.updateEvents(events);
    }

    private SessionBuffer createEmptyBuffer() {
        return SessionBuffer.create(
                com.tech_challenge.medical.domain.session.CorrelationId.generate()
        );
    }

    private EmotionEvent createEmotionEvent(EmotionLabel label) {
        return EmotionEvent.of(
                label,
                com.tech_challenge.medical.domain.triage.Confidence.of(0.85),
                Instant.now()
        );
    }

    private TranscriptChunk createTranscriptChunk(String text) {
        Instant now = Instant.now();
        return TranscriptChunk.of(
                text,
                com.tech_challenge.medical.domain.triage.Confidence.of(0.90),
                TimeWindow.of(now, now.plusSeconds(2))
        );
    }

    private ObjectDetectionEvent createObjectDetection(String label) {
        return ObjectDetectionEvent.of(
                ObjectLabel.of(label),
                com.tech_challenge.medical.domain.triage.Confidence.of(0.88),
                FrameIndex.of(1)
        );
    }

    private ClinicalForm createCompleteForm() {
        return ClinicalForm.of(
                PatientComplaint.of("Chest pain", "2 hours", "Severe"),
                VitalSigns.of(
                        HeartRate.of(95),
                        BloodPressure.of(140, 90),
                        Temperature.ofCelsius(37.0),
                        OxygenSaturation.of(98)
                ),
                MedicalHistory.of(
                        List.of("Hypertension"),
                        List.of("Lisinopril"),
                        List.of()
                )
        );
    }
}
