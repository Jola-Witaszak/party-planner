package com.witaszakjola.partyplanner.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    @NotNull
    @JsonProperty(value = "eventId")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @JsonProperty(value = "name")
    private String name;

    @NotNull
    @FutureOrPresent
    @JsonProperty(value = "startDate")
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    @JsonProperty(value = "startTime")
    private LocalTime startTime;

    @JsonProperty(value = "endDate")
    @Future
    private LocalDate endDate;

    @Size(min = 3, max = 255, message
            = "Description must be between 3 and 255 characters")
    @JsonProperty(value = "description")
    private String description;
/*
    @JsonProperty(value = "")
    private Set<GpsPositionDto> gpsPositions;

    @JsonProperty(value = "")
    private Set<UserDto> users;

 */

}
