package com.grandpaweather.weatherservice.service;

import com.grandpaweather.weatherservice.domain.dto.WeatherTriggerDTO;
import com.grandpaweather.weatherservice.domain.request.WeatherRequest;
import com.grandpaweather.weatherservice.domain.response.ForecastResponse;

import java.util.List;

public interface WeatherService {

    WeatherTriggerDTO getWeatherForCurrentDayByRequest(WeatherRequest request);

    List<WeatherTriggerDTO> getWeatherDataForFewDaysByRequest(WeatherRequest request);

    ForecastResponse getForecastResponseByRequest(WeatherRequest request);
}
