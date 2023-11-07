package com.bahmet.weatherviewer.service;

import com.bahmet.weatherviewer.dao.LocationDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.dto.GeocodingApiResponse;
import com.bahmet.weatherviewer.dto.WeatherApiResponse;
import com.bahmet.weatherviewer.dto.WeatherDTO;
import com.bahmet.weatherviewer.dto.weatherEnum.TimeOfDay;
import com.bahmet.weatherviewer.dto.weatherEnum.WeatherCondition;
import com.bahmet.weatherviewer.model.Location;
import com.bahmet.weatherviewer.model.User;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WeatherService {
    private final WeatherApiService weatherApiService;
    private final GeoCodingApiService geocodingApiService;
    private final LocationDAO locationDAO;
    private final UserDAO userDAO;

    public WeatherService(WeatherApiService weatherApiService, GeoCodingApiService geoCodingApiService, LocationDAO locationDAO, UserDAO userDAO) {
        this.weatherApiService = weatherApiService;
        this.geocodingApiService = geoCodingApiService;
        this.locationDAO = locationDAO;
        this.userDAO = userDAO;
    }

    private static WeatherDTO buildLocationWeatherDTO(WeatherApiResponse weatherApiResponse) {
        return new WeatherDTO(WeatherCondition.getWeatherConditionForCode(weatherApiResponse.getWeather().get(0).getId()), TimeOfDay.getTimeOfDayByTime(weatherApiResponse.getDateTime()), weatherApiResponse.getWeather().get(0).getDescription(), weatherApiResponse.getMain().getTemperature(), weatherApiResponse.getMain().getTemperatureFeelsLike(), weatherApiResponse.getMain().getTemperatureMinimum(), weatherApiResponse.getMain().getTemperatureMaximum(), weatherApiResponse.getMain().getHumidity(), weatherApiResponse.getMain().getPressure(), weatherApiResponse.getWind().getSpeed(), weatherApiResponse.getClouds().getCloudiness(), weatherApiResponse.getDateTime(), weatherApiResponse.getSys().getSunrise(), weatherApiResponse.getSys().getSunset());
    }

    public Optional<Map<Location, WeatherDTO>> getUserWeather(User user) {
        Hibernate.initialize(user.getLocations());

        List<Location> locations = user.getLocations();

        if (locations.isEmpty()) {
            return Optional.empty();
        }

        Map<Location, WeatherDTO> locationWeatherMap = locations.stream().collect(Collectors.toMap(location -> location, location -> buildLocationWeatherDTO(weatherApiService.getWeatherByLocation(location))));

        return Optional.of(locationWeatherMap);
    }

    public void removeUserLocation(User user, String location_id) {
        Location location = locationDAO.findById(Integer.parseInt(location_id)).orElseThrow(() -> new InvalidParameterException("Location not found."));

        if (user.getLocations().contains(location)) {
            user.getLocations().remove(location);
            locationDAO.update(location);
            userDAO.update(user);
        }
    }

    public void addUserLocation(User user, String locationName, String locationLatitudeParam, String locationLongitudeParam) {
        BigDecimal locationLatitude = new BigDecimal(locationLatitudeParam);
        BigDecimal locationLongitude = new BigDecimal(locationLongitudeParam);

        Optional<Location> locationOptional = locationDAO.findByCoordinates(locationLatitude, locationLongitude);

        if (locationOptional.isPresent()) {
            Location location = locationOptional.get();

            user.getLocations().add(location);

            userDAO.update(user);
        } else {
            Location location = new Location(locationName, locationLatitude, locationLongitude);

            user.getLocations().add(location);

            locationDAO.save(location);
            userDAO.update(user);
        }
    }

    public Optional<List<GeocodingApiResponse>> findLocations(String searchQuery) {
        String preparedSearchQuery = searchQuery.replaceAll(" ", "+");

        List<GeocodingApiResponse> foundLocations = geocodingApiService.getGeoDataByLocationName(preparedSearchQuery);

        if (foundLocations == null || foundLocations.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(foundLocations);
    }
}
