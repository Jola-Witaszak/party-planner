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

    @NotNull(message = "id must be a number")
    @JsonProperty(value = "userId")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @JsonProperty(value = "username")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty
    @NotNull(message = "email cannot be null")
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "attendingParty")
    private Boolean attending_party;

    @JsonProperty(value = "phoneNumber")
    private int phone;
}
