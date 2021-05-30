package com.witaszakjola.partyplanner.backend.service;

import com.witaszakjola.partyplanner.backend.controller.UserController;
import com.witaszakjola.partyplanner.backend.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserController userController;

    public List<UserDto> findAll() {
        return userController.getUsers();
    }

    public List<UserDto> findAll(String filteredUser) {
        return userController.filterUsers(filteredUser);
    }

    public UserDto save(UserDto userDto) {
        return userController.createUser(userDto);
    }

    public void removeUser(UserDto userDto) {
        long userId = userDto.getId();
        userController.removeUser(userId);
    }
}
