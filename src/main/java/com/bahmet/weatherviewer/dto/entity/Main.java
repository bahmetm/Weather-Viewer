package com.bahmet.weatherviewer.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double temperatureFeelsLike;

    @JsonProperty("temp_min")
    private Double temperatureMinimum;

    @JsonProperty("temp_max")
    private Double temperatureMaximum;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    public Double getTemperature() {
        return temperature;
    }

    public Double getTemperatureFeelsLike() {
        return temperatureFeelsLike;
    }

    public Double getTemperatureMinimum() {
        return temperatureMinimum;
    }

    public Double getTemperatureMaximum() {
        return temperatureMaximum;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temperature=" + temperature +
                ", temperatureFeelsLike=" + temperatureFeelsLike +
                ", temperatureMin=" + temperatureMinimum +
                ", temperatureMax=" + temperatureMaximum +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
