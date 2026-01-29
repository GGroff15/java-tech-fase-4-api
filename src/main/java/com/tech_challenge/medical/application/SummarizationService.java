package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.events.BufferedEvents;
import com.tech_challenge.medical.domain.form.ClinicalForm;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.domain.summary.ClinicalSummary;
import com.tech_challenge.medical.domain.summary.EmotionSummary;
import com.tech_challenge.medical.domain.summary.TranscriptSummary;
import com.tech_challenge.medical.domain.summary.VideoSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SummarizationService {
    private static final Logger log = LoggerFactory.getLogger(SummarizationService.class);
    private static final int TOP_N_OBJECTS = 5;

    public ClinicalSummary summarize(SessionBuffer buffer, ClinicalForm form) {
        if (buffer == null) {
            throw new IllegalArgumentException("Buffer cannot be null");
        }
        if (form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        BufferedEvents events = buffer.events();
        
        EmotionSummary emotionSummary = summarizeEmotions(events);
        TranscriptSummary transcriptSummary = summarizeTranscripts(events);
        VideoSummary videoSummary = summarizeVideo(events);
        
        log.info("Summarized session: {}", buffer.correlationId().asString());
        
        return ClinicalSummary.of(
                form,
                emotionSummary,
                transcriptSummary,
                videoSummary,
                buffer.createdAt(),
                Instant.now()
        );
    }

    private EmotionSummary summarizeEmotions(BufferedEvents events) {
        return EmotionSummary.of(
                events.emotions().dominantEmotion(),
                events.emotions().distributionPercentages(),
                !events.emotions().highConfidencePeaks().isEmpty()
        );
    }

    private TranscriptSummary summarizeTranscripts(BufferedEvents events) {
        return TranscriptSummary.of(
                events.transcripts().consolidatedText(),
                events.transcripts().hasLowConfidenceSegments()
        );
    }

    private VideoSummary summarizeVideo(BufferedEvents events) {
        return VideoSummary.of(
                events.objects().mostFrequentObjects(TOP_N_OBJECTS),
                events.objects().size()
        );
    }
}
