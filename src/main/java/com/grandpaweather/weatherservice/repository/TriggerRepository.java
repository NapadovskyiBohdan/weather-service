package com.grandpaweather.weatherservice.repository;

import com.grandpaweather.weatherservice.domain.Trigger;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TriggerRepository extends MongoRepository<Trigger, String> {

    Optional<Trigger> getTriggerByName(String name);

}
