package com.bahmet.weatherviewer.dto;

import com.bahmet.weatherviewer.dto.entity.Clouds;
import com.bahmet.weatherviewer.dto.entity.Main;
import com.bahmet.weatherviewer.dto.entity.Wind;
import com.bahmet.weatherviewer.dto.entity.Weather;
import com.bahmet.weatherviewer.util.UnixTimestampDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private LocalDateTime dateTime;

    @JsonProperty("sys")
    private Sys sys;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        @JsonProperty("sunrise")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunrise;;

        @JsonProperty("sunset")
        @JsonDeserialize(using = UnixTimestampDeserializer.class)
        private LocalDateTime sunset;

        public LocalDateTime getSunrise() {
            return sunrise;
        }

        public LocalDateTime getSunset() {
            return sunset;
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Sys getSys() {
        return sys;
    }

    @Override
    public String toString() {
        return "WeatherApiResponse{" +
                "weather=" + weather +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dateTime=" + dateTime +
                ", sys=" + sys +
                '}';
    }
}
