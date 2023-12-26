package com.grandpaweather.weatherservice.service.impl;

import com.grandpaweather.weatherservice.domain.Trigger;
import com.grandpaweather.weatherservice.domain.WeatherData;
import com.grandpaweather.weatherservice.domain.WeatherTriggerRelation;
import com.grandpaweather.weatherservice.repository.WeatherTriggerRelationRepository;
import com.grandpaweather.weatherservice.service.WeatherTriggerBuilder;
import com.grandpaweather.weatherservice.service.WeatherTriggerManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherTriggerManagerImpl implements WeatherTriggerManager {

    private final WeatherTriggerBuilder builder;
    private final WeatherTriggerRelationRepository weatherTriggerRelationRepository;
    private static final Logger log = LoggerFactory.getLogger(WeatherTriggerManagerImpl.class);


    @Override
    public List<WeatherTriggerRelation> updateTriggersFromResponse(List<WeatherData> responses) {
        log.info("Update triggers from http response ");
        Map<LocalDateTime, WeatherTriggerRelation> relationHashMap = updateRelationsFromResponse(responses);
        List<WeatherTriggerRelation> relations = calculateTrigger(relationHashMap);
        return weatherTriggerRelationRepository.saveAll(relations);
    }


    private Map<LocalDateTime, WeatherTriggerRelation> updateRelationsFromResponse(List<WeatherData> responses) {
        Map<LocalDateTime, WeatherTriggerRelation> relationHashMap = buildWatherMap(responses);
        updateRelation(relationHashMap, responses);
        return relationHashMap;
    }


    private Map<LocalDateTime, WeatherTriggerRelation> buildWatherMap(List<WeatherData> responses) {
        List<LocalDateTime> dates = responses.stream()
                .map(WeatherData::getDate)
                .toList();
        Long cityId = responses.stream().findFirst().map(r -> r.getCity().getId()).orElseThrow();
        return weatherTriggerRelationRepository.getAllByDateInAndCityId(dates, cityId)
                .stream()
                .collect(Collectors.toMap(WeatherTriggerRelation::getDate, Function.identity()));
    }

    private void updateRelation(Map<LocalDateTime, WeatherTriggerRelation> relationMap, List<WeatherData> responses) {
        responses.forEach(
                response -> {
                    if (relationMap.containsKey(response.getDate())) {
                        updateTriggerRelation(response, relationMap);
                    } else {
                        addResponseToMap(response, relationMap);
                    }
                }
        );
    }

    private void addResponseToMap(WeatherData response, Map<LocalDateTime, WeatherTriggerRelation> relationMap) {
        WeatherTriggerRelation relation = WeatherTriggerRelation.buildFromResponse(response);
        relationMap.put(relation.getDate(), relation);
    }

    private void updateTriggerRelation(WeatherData response, Map<LocalDateTime, WeatherTriggerRelation> relationMap) {
        WeatherTriggerRelation relation = relationMap.get(response.getDate());
        relation.updateFromResponse(response);
    }

    private List<WeatherTriggerRelation> calculateTrigger(Map<LocalDateTime, WeatherTriggerRelation> relationMap) {
        relationMap.keySet()
                .forEach(key -> {
                    LocalDateTime nextDate = key.plusDays(1l);
                    WeatherTriggerRelation currentDayData = relationMap.get(key);
                    Trigger trigger = builder.getTriggerByName(currentDayData.getWeather_main());
                    if(relationMap.get(nextDate) != null)
                        trigger = builder.buildTriggerByWeatherData(currentDayData, relationMap.get(nextDate));
                    currentDayData.setTrigger(trigger);
                });
        return relationMap.keySet().stream().map(relationMap::get).collect(Collectors.toList());
    }


}
