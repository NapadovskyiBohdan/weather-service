package com.grandpaweather.weatherservice.service;

import com.grandpaweather.weatherservice.domain.Trigger;
import com.grandpaweather.weatherservice.domain.WeatherTriggerRelation;

public interface WeatherTriggerBuilder {

    Trigger buildTriggerByWeatherData(WeatherTriggerRelation currentDay, WeatherTriggerRelation nextDay);

    Trigger getTriggerByName(String name);

}
