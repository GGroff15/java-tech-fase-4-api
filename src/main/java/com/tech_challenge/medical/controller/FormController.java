package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.TriageService;
import com.tech_challenge.medical.controller.dto.ClinicalFormRequest;
import com.tech_challenge.medical.controller.dto.TriageResultResponse;
import com.tech_challenge.medical.domain.form.*;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.triage.TriageResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forms")
public class FormController {
    private final TriageService triageService;

    public FormController(TriageService triageService) {
        this.triageService = triageService;
    }

    @PostMapping("/{correlationId}")
    public ResponseEntity<TriageResultResponse> submitForm(
            @PathVariable String correlationId,
            @Valid @RequestBody ClinicalFormRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        ClinicalForm form = mapToForm(request);
        TriageResult result = triageService.processTriage(id, form);
        TriageResultResponse response = mapToResponse(result);
        
        return ResponseEntity.ok(response);
    }

    private ClinicalForm mapToForm(ClinicalFormRequest request) {
        PatientComplaint complaint = PatientComplaint.of(
                request.complaint().description(),
                request.complaint().duration(),
                request.complaint().severity()
        );

        VitalSigns vitalSigns = VitalSigns.of(
                request.vitalSigns().heartRate() != null 
                        ? HeartRate.of(request.vitalSigns().heartRate()) 
                        : null,
                request.vitalSigns().systolicBp() != null && request.vitalSigns().diastolicBp() != null
                        ? BloodPressure.of(request.vitalSigns().systolicBp(), request.vitalSigns().diastolicBp())
                        : null,
                request.vitalSigns().temperature() != null
                        ? Temperature.ofCelsius(request.vitalSigns().temperature())
                        : null,
                request.vitalSigns().oxygenSaturation() != null
                        ? OxygenSaturation.of(request.vitalSigns().oxygenSaturation())
                        : null
        );

        MedicalHistory medicalHistory = MedicalHistory.of(
                request.medicalHistory().conditions(),
                request.medicalHistory().medications(),
                request.medicalHistory().allergies()
        );

        return ClinicalForm.of(complaint, vitalSigns, medicalHistory);
    }

    private TriageResultResponse mapToResponse(TriageResult result) {
        return new TriageResultResponse(
                result.triageLevel().name(),
                result.riskFactors().asStringList(),
                result.inconsistencies().asStringList(),
                result.notesForPhysician().asString(),
                result.confidence().asDouble()
        );
    }
}
