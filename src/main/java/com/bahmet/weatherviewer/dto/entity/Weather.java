package com.bahmet.weatherviewer.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("main")
    private String state;

    @JsonProperty("description")
    private String description;

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
