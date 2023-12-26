package com.grandpaweather.weatherservice.config.dbmigrations;

import com.grandpaweather.weatherservice.domain.Trigger;
import com.grandpaweather.weatherservice.repository.TriggerRepository;
import com.grandpaweather.weatherservice.service.impl.WeatherTriggerBuilderImpl;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;


@ChangeUnit(id="trigger-initializer", order = "1", author = "Bohdan")
public class TriggerInitializerChangeUnit {

    private static final Logger log = LoggerFactory.getLogger(WeatherTriggerBuilderImpl.class);

    @BeforeExecution
    public void beforeExecution(MongoTemplate mongoTemplate) {
        log.info("Creating collection in mongo db");
        mongoTemplate.createCollection("triggers");
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("triggers");
    }

    @Execution
    public void execution(TriggerRepository triggerRepository) {
        log.info("Saving triggers in mongo collection");
        List<Trigger> triggers = createListOfTriggers();
        triggerRepository.saveAll(triggers);
    }

    @RollbackExecution
    public void rollbackExecution(TriggerRepository triggerRepository) {
        log.error("Error by creating triggers");
        triggerRepository.deleteAll();
    }


    private List<Trigger> createListOfTriggers() {
        log.info("Creating triggers for saving in mongo collection");
        List<Trigger> triggers = new ArrayList<>();
        triggers.add(Trigger.buildByNameAndDescription("coldSnap","Snap cold"));
        triggers.add(Trigger.buildByNameAndDescription("suddenWarming","sudden warming"));
        triggers.add(Trigger.buildByNameAndDescription("fog","Fog"));
        triggers.add(Trigger.buildByNameAndDescription("sand","Sand"));
        triggers.add(Trigger.buildByNameAndDescription("thunderstorm","thunderstorm"));
        triggers.add(Trigger.buildByNameAndDescription("snow","Snow"));
        triggers.add(Trigger.buildByNameAndDescription("dust","Dust"));
        triggers.add(Trigger.buildByNameAndDescription("suddenRain","Sudden rain"));
        triggers.add(Trigger.buildByNameAndDescription("clouds","Clouds"));
        triggers.add(Trigger.buildByNameAndDescription("rain","Rain"));
        return triggers;

    }



}
