package com.grandpaweather.weatherservice.repository;

import com.grandpaweather.weatherservice.domain.WeatherData;
import com.grandpaweather.weatherservice.domain.WeatherTriggerRelation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface WeatherTriggerRelationRepository extends MongoRepository<WeatherTriggerRelation, String> {

    List<WeatherTriggerRelation> getAllByDateInAndCityId(List<LocalDateTime> dates, Long cityId);
}
