package com.witaszakjola.partyplanner.backend.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class MapClient {

    private final RestTemplate restTemplate;

    public String getMap() {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/map")
                .build().encode().toUri();

        return restTemplate.getForObject(url, String.class);
    }
}
