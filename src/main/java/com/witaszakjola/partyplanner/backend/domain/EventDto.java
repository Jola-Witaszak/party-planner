package com.witaszakjola.partyplanner.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {

    @NotNull
    @JsonProperty(value = "eventId")
    private Long id;

    @NotNull
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

    @JsonProperty(value = "description")
    private String description;
}
