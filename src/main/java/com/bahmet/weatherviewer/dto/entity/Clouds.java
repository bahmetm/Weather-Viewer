package com.bahmet.weatherviewer.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {
    @JsonProperty("all")
    private Integer cloudiness;

    public Integer getCloudiness() {
        return cloudiness;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "cloudiness=" + cloudiness +
                '}';
    }
}
