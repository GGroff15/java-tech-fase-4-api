package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;

public interface ProcessorStrategy {

    AnalysisResult process(AnalysisCase newAnalysisCase);

}
