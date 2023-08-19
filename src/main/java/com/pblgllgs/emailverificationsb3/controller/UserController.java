package com.pblgllgs.emailverificationsb3.controller;

import com.pblgllgs.emailverificationsb3.model.User;
import com.pblgllgs.emailverificationsb3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
