package com.witaszakjola.partyplanner.backend.controller;

import com.witaszakjola.partyplanner.backend.domain.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    public List<UserDto> getUsers() {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/users")
                .build().encode().toUri();

        return getUserDtos(url);

    }

    public List<UserDto> filterUsers(String filteredUser) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/users/search/" + filteredUser)
                .build().encode().toUri();

        return getUserDtos(url);
    }

    private List<UserDto> getUserDtos(URI url) {
        try {
            UserDto[] response = restTemplate.getForObject(url, UserDto[].class);
            return Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getUsername()))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public UserDto createUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/users")
                .build().encode().toUri();

        return restTemplate.postForObject(url, userDto, UserDto.class);
    }


    public void removeUser(long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/users/" + userId)
                .build().encode().toUri();

        restTemplate.delete(url);
    }

    public void updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8083/v1/users")
                .build().encode().toUri();

        restTemplate.put(url, userDto);
    }
}
