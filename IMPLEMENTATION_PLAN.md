# Implementation Plan: Multimodal Medical Patient Screening System

This plan tracks the implementation of a patient screening system that classifies urgency using video, audio, and medical reports, following the Manchester Triage System. Real-time processing is required for video and audio modalities only. Medical reports are processed upon receipt using the OpenAI API, integrated via Spring OpenAI.

## Checkpoints

- [ ] **Define and document color-coded urgency classification logic**
  - [ ] Update RiskLevel and related domain models
- [ ] **Implement/refine modality processors**
  - [ ] Audio: Real-time speech-to-text and emotion analysis
  - [ ] Video: Real-time facial emotion detection
  - [ ] Medical Report: NLP extraction and normalization using Spring OpenAI integration
- [ ] **Aggregate modality results**
  - [ ] Implement aggregation and notification logic in ProcessorService
- [ ] **Integrate external services**
  - [ ] Azure Speech Service for real-time audio
  - [ ] YOLOv8 for real-time video
  - [ ] Spring OpenAI for medical report processing
- [ ] **Implement REST API endpoints**
  - [ ] Audio upload and processing
  - [ ] Video upload and processing
  - [ ] Medical report upload and processing
- [ ] **Persist files and results**
  - [ ] Azure Blob Storage integration
  - [ ] PostgreSQL integration for metadata and results
- [ ] **Document configuration and setup**
  - [ ] Update README.md
  - [ ] Update application.yaml (including Spring OpenAI properties)
