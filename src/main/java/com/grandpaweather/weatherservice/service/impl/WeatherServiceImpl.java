package com.grandpaweather.weatherservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grandpaweather.weatherservice.domain.WeatherData;
import com.grandpaweather.weatherservice.domain.WeatherTriggerRelation;
import com.grandpaweather.weatherservice.domain.dto.WeatherTriggerDTO;
import com.grandpaweather.weatherservice.domain.request.WeatherRequest;
import com.grandpaweather.weatherservice.domain.response.ForecastResponse;
import com.grandpaweather.weatherservice.helper.URLGenerator;
import com.grandpaweather.weatherservice.service.WeatherService;
import com.grandpaweather.weatherservice.service.WeatherTriggerManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final URLGenerator urlGenerator;
    private final WeatherTriggerManager weatherTriggerManager;
    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Override
    public ForecastResponse getForecastResponseByRequest(WeatherRequest request) {
        log.info("Getting weather forecast response by request {} ", request);
        String response = generateRestResponse(request);
        return generateForecastWeatherResponseByWebResponse(response);
    }

    @Override
    public List<WeatherTriggerDTO> getWeatherForCurrentDayByRequest(WeatherRequest request) {
        List<WeatherTriggerRelation> relations = calculateTriggerWeatherByRequest(request);
        return relations.stream().map(WeatherTriggerDTO::buildDTOFromEntity)
                .sorted(Comparator.comparing(WeatherTriggerDTO::getDate))
                .collect(Collectors.toList());
    }


    private List<WeatherTriggerRelation> calculateTriggerWeatherByRequest(WeatherRequest request) {
        log.info("Calculating trigger for weather by request {}", request);
        ForecastResponse response = getForecastResponseByRequest(request);
        List<WeatherData> weatherData = WeatherData.createListOfWeatherData(response);
        return weatherTriggerManager.updateTriggersFromResponse(weatherData);
    }

    private String generateRestResponse(WeatherRequest request) {
        String url = urlGenerator.generateURL(request);
        return restTemplate.getForObject(url, String.class);
    }

    private ForecastResponse generateForecastWeatherResponseByWebResponse(String response) {
        try {
            return objectMapper.readValue(response, ForecastResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
