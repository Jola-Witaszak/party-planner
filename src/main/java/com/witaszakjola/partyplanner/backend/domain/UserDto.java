package com.witaszakjola.partyplanner.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @NotNull
    @JsonProperty(value = "userId")
    private Long id;

    @NotNull
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
