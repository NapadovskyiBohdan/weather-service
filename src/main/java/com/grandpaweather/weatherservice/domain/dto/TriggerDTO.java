package com.grandpaweather.weatherservice.domain.dto;


import com.grandpaweather.weatherservice.domain.Trigger;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class TriggerDTO  {

    private String id;

    private String name;

    private String description;

    public static TriggerDTO buildDtoFromEntity(Trigger trigger) {
        return TriggerDTO.builder()
                .id(trigger.getId())
                .name(trigger.getName())
                .description(trigger.getDescription())
                .build();
    }

}
