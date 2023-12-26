package com.grandpaweather.weatherservice.web.controller;

import com.grandpaweather.weatherservice.service.WeatherService;
import com.grandpaweather.weatherservice.domain.request.WeatherRequest;
import com.grandpaweather.weatherservice.domain.dto.WeatherTriggerDTO;
import com.grandpaweather.weatherservice.service.impl.WeatherTriggerBuilderImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    private static final Logger log = LoggerFactory.getLogger(WeatherTriggerBuilderImpl.class);


    @PostMapping("/weather")
    public ResponseEntity<WeatherTriggerDTO> getWeatherForCurrentDayByRequest(@RequestBody @Valid WeatherRequest request) {
        return ResponseEntity.ok().body(weatherService.getWeatherForCurrentDayByRequest(request));
    }

    @PostMapping("/weathers")
    public ResponseEntity<List<WeatherTriggerDTO>> getWeatherForFewDaysByRequest(@RequestBody @Valid WeatherRequest request) {
        return ResponseEntity.ok().body(weatherService.getWeatherDataForFewDaysByRequest(request));
    }


}
