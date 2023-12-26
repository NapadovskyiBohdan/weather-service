package com.grandpaweather.weatherservice.service;

import com.grandpaweather.weatherservice.domain.WeatherData;
import com.grandpaweather.weatherservice.domain.WeatherTriggerRelation;

import java.util.List;

public interface WeatherTriggerManager {


    List<WeatherTriggerRelation> updateTriggersFromResponse(List<WeatherData> response);
}
