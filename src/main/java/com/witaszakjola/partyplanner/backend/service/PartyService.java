package com.witaszakjola.partyplanner.backend.service;

import com.witaszakjola.partyplanner.backend.client.PartyClient;
import com.witaszakjola.partyplanner.backend.domain.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyClient partyClient;

    public void save(EventDto eventDto) {
        partyClient.create(eventDto);
    }

    public void update(EventDto eventDto) {
        partyClient.update(eventDto);
    }

    public void removeUser(EventDto eventDto) {
        long id = eventDto.getId();
        partyClient.remove(id);
    }

    public List<EventDto> findAll() {
        return partyClient.findAll();
    }

    public List<EventDto> findAll(String filteredParty) {
        return partyClient.findAll(filteredParty);
    }
}
