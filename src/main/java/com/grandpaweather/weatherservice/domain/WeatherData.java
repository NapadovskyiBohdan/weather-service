package com.grandpaweather.weatherservice.domain;

import com.grandpaweather.weatherservice.domain.response.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData implements Serializable {

    private LocalDateTime date;
    private City city;
    private MainResponse main;
    private List<Weather> weather;
    private Clouds clouds;
    private Integer visibility;
    private double pop;
    private SystemResponse sys;


    public static List<WeatherData> createListOfWeatherData(ForecastResponse response) {
        List<WeatherResponse> responseList = response.getList();
        return responseList.stream()
                .filter(w -> dateIsStartOfDate(w.getDt(), responseList.indexOf(w)))
                .map(w -> createWeatherData(response, w))
                .sorted(Comparator.comparing(WeatherData::getDate).reversed())
                .collect(Collectors.toList());

    }


    private static boolean dateIsStartOfDate(long dt, int index) {
        if(index ==0) {
            return true;
        } else {
            LocalDateTime currentDate = getDateFromLinuxTimeStamp(dt);
            LocalDateTime beginDay = currentDate.toLocalDate().atStartOfDay();
            return currentDate.equals(beginDay);
        }
    }

    private static LocalDateTime getDateFromLinuxTimeStamp(long dt) {
        ZoneId zoneId = ZoneId.of("UTC");
        Instant instDate = Instant.ofEpochSecond(dt);
        return LocalDateTime.ofInstant(instDate, zoneId);
    }


    private static WeatherData createWeatherData(ForecastResponse response, WeatherResponse w) {
        return WeatherData.builder()
                .city(response.getCity())
                .date(getDateFromLinuxTimeStamp(w.getDt()))
                .main(w.getMain())
                .weather(w.getWeather())
                .clouds(w.getClouds())
                .visibility(w.getVisibility())
                .pop(w.getPop())
                .sys(w.getSys())
                .build();
    }

}
