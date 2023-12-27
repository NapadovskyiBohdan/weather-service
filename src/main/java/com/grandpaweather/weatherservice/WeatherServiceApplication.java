package com.grandpaweather.weatherservice;

import com.grandpaweather.weatherservice.web.controller.WeatherController;
import io.mongock.runner.springboot.EnableMongock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongock

public class WeatherServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

	public static void main(String[] args) {
		SpringApplication.run(WeatherServiceApplication.class, args);
	}

}
