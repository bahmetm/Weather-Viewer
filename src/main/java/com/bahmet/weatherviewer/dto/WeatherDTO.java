package com.bahmet.weatherviewer.dto;

import com.bahmet.weatherviewer.dto.weatherEnum.TimeOfDay;
import com.bahmet.weatherviewer.dto.weatherEnum.WeatherCondition;

import java.time.LocalDateTime;

public class WeatherDTO {
    private WeatherCondition weatherCondition;
    private TimeOfDay timeOfDay;
    private String description;
    private Double temperature;
    private Double feelsLike;
    private Double temperatureMinimum;
    private Double temperatureMaximum;
    private Integer humidity;
    private Integer pressure;
    private Double windSpeed;
    private Integer cloudiness;
    private LocalDateTime date;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    public WeatherDTO(WeatherCondition weatherCondition, TimeOfDay timeOfDay, String description, Double temperature, Double feelsLike, Double temperatureMinimum, Double temperatureMaximum, Integer humidity, Integer pressure, Double windSpeed, Integer cloudiness, LocalDateTime date, LocalDateTime sunrise, LocalDateTime sunset) {
        this.weatherCondition = weatherCondition;
        this.timeOfDay = timeOfDay;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.temperatureMinimum = temperatureMinimum;
        this.temperatureMaximum = temperatureMaximum;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.cloudiness = cloudiness;
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(TimeOfDay timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTemperatureMinimum() {
        return temperatureMinimum;
    }

    public void setTemperatureMinimum(Double temperatureMinimum) {
        this.temperatureMinimum = temperatureMinimum;
    }

    public Double getTemperatureMaximum() {
        return temperatureMaximum;
    }

    public void setTemperatureMaximum(Double temperatureMaximum) {
        this.temperatureMaximum = temperatureMaximum;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getSunrise() {
        return sunrise;
    }

    public void setSunrise(LocalDateTime sunrise) {
        this.sunrise = sunrise;
    }

    public LocalDateTime getSunset() {
        return sunset;
    }

    public void setSunset(LocalDateTime sunset) {
        this.sunset = sunset;
    }
}
