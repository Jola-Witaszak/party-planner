package com.witaszakjola.partyplanner.backend.service;

import com.witaszakjola.partyplanner.backend.client.MailClient;
import com.witaszakjola.partyplanner.backend.domain.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailClient mailClient;

    public String sendEmail(Mail mail) {

        return mailClient.send(mail);
    }
}
