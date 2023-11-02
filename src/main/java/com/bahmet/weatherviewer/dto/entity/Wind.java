package com.bahmet.weatherviewer.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    @JsonProperty("speed")
    private Double speed;

    @JsonProperty("deg")
    private Double deg;

    @JsonProperty("gust")
    private Double gust;

    public Double getSpeed() {
        return speed;
    }

    public Double getDeg() {
        return deg;
    }

    public Double getGust() {
        return gust;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                ", gust=" + gust +
                '}';
    }
}
