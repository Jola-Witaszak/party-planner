package com.witaszakjola.partyplanner.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@Data
@NoArgsConstructor
public class Mail {

    @NotNull
    @NotEmpty
    private String mailTo;

    @NotNull
    @NotEmpty
    private String subject;

    @NotNull
    @NotEmpty
    private String message;

}
