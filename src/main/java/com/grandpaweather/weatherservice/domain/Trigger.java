package com.grandpaweather.weatherservice.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "triggers")
public class Trigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String name;

    private String description;

    public static Trigger buildByNameAndDescription(String name, String description) {
        return Trigger.builder()
                .name(name)
                .description(description)
                .build();
    }
}
