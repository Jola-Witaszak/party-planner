package com.witaszakjola.partyplanner.backend.service;

import com.witaszakjola.partyplanner.backend.client.MapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapClient mapClient;

    public String downloadMap() {
        return mapClient.getMap();
    }
}
