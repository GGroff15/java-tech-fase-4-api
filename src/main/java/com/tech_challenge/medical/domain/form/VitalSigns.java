package com.tech_challenge.medical.domain.form;

import java.util.Map;

public final class VitalSigns {
    private final HeartRate heartRate;
    private final BloodPressure bloodPressure;
    private final Temperature temperature;
    private final OxygenSaturation oxygenSaturation;

    private VitalSigns(HeartRate heartRate, BloodPressure bloodPressure, Temperature temperature, OxygenSaturation oxygenSaturation) {
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.oxygenSaturation = oxygenSaturation;
    }

    public static VitalSigns of(HeartRate heartRate, BloodPressure bloodPressure, Temperature temperature, OxygenSaturation oxygenSaturation) {
        return new VitalSigns(heartRate, bloodPressure, temperature, oxygenSaturation);
    }

    public HeartRate heartRate() {
        return heartRate;
    }

    public BloodPressure bloodPressure() {
        return bloodPressure;
    }

    public Temperature temperature() {
        return temperature;
    }

    public OxygenSaturation oxygenSaturation() {
        return oxygenSaturation;
    }

    public Map<String, Object> asMap() {
        return Map.of(
                "heartRate", heartRate != null ? heartRate.asInt() : null,
                "bloodPressure", bloodPressure != null ? bloodPressure.asString() : null,
                "temperature", temperature != null ? temperature.asDouble() : null,
                "oxygenSaturation", oxygenSaturation != null ? oxygenSaturation.asInt() : null
        );
    }
}
