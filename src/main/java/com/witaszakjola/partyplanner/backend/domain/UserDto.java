package com.witaszakjola.partyplanner.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @JsonProperty(value = "userId")
    private Long id;

    @NotNull
    @JsonProperty(value = "username")
    private String username;

    @Email
    @NotEmpty
    @NotNull
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "attendingParty")
    private Boolean attending_party;

    @JsonProperty(value = "phoneNumber")
    private int phone;
}
