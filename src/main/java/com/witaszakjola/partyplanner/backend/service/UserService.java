package com.witaszakjola.partyplanner.backend.service;

import com.witaszakjola.partyplanner.backend.client.UserClient;
import com.witaszakjola.partyplanner.backend.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserClient userClient;

    public List<UserDto> findAll() {
        return userClient.getUsers();
    }

    public List<UserDto> findAll(String filteredUser) {
        return userClient.filterUsers(filteredUser);
    }

    public UserDto save(UserDto userDto) {
        return userClient.createUser(userDto);
    }

    public void removeUser(UserDto userDto) {
        long userId = userDto.getId();
        userClient.removeUser(userId);
    }

    public void update(UserDto userDto) {
        userClient.updateUser(userDto);
    }
}
