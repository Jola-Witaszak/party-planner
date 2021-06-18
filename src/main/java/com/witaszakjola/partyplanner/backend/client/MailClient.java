package com.witaszakjola.partyplanner.backend.client;

import com.witaszakjola.partyplanner.backend.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class MailClient {

    private final RestTemplate restTemplate;

    public String send(Mail mail) {
        URI url = UriComponentsBuilder.fromHttpUrl("https://glacial-taiga-47785.herokuapp.com/v1/email")
                .build().encode().toUri();
        return restTemplate.postForObject(url, mail, String.class);
    }
}
