package com.witaszakjola.partyplanner.backend.client;

import com.witaszakjola.partyplanner.backend.domain.EventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class PartyClient {

    private final RestTemplate restTemplate;

    private URI getUri() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/events")
                .build().encode().toUri();
    }

    public void create(EventDto eventDto) {
        URI url = getUri();

        restTemplate.postForObject(url, eventDto, EventDto.class);
    }

    public void update(EventDto eventDto) {
        URI url = getUri();

        restTemplate.put(url, eventDto);
    }

    public void remove(long id) {

        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/events/" + id)
                .build().encode().toUri();

        restTemplate.delete(url);
    }

    public List<EventDto> findAll() {
        URI url = getUri();

        return getEventsDto(url);
    }

    private List<EventDto> getEventsDto(URI url) {
        try {
            EventDto[] response = restTemplate.getForObject(url, EventDto[].class);

            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<EventDto> findAll(String filteredEvent) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/events/" + filteredEvent)
                .build().encode().toUri();
        return getEventsDto(url);
    }
}
