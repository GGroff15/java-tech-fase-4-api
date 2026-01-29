package com.tech_challenge.medical.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "triage")
public class TriageProperties {
    private final Llm llm = new Llm();
    private final Session session = new Session();
    private final Security security = new Security();

    public Llm llm() {
        return llm;
    }

    public Session session() {
        return session;
    }

    public Security security() {
        return security;
    }

    public static class Llm {
        private String promptTemplate;

        public String promptTemplate() {
            return promptTemplate;
        }

        public void setPromptTemplate(String promptTemplate) {
            this.promptTemplate = promptTemplate;
        }
    }

    public static class Session {
        private long ttlMinutes;
        private long cleanupIntervalMs;

        public long ttlMinutes() {
            return ttlMinutes;
        }

        public void setTtlMinutes(long ttlMinutes) {
            this.ttlMinutes = ttlMinutes;
        }

        public long cleanupIntervalMs() {
            return cleanupIntervalMs;
        }

        public void setCleanupIntervalMs(long cleanupIntervalMs) {
            this.cleanupIntervalMs = cleanupIntervalMs;
        }
    }

    public static class Security {
        private String apiKey;

        public String apiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
