package com.example.server.controller;

import com.example.server.model.UserModel;
import com.example.server.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void displayAllUsers(){
        List<UserModel> users = this.userService.findAllUsers();

        System.out.println("Liste des utilisateurs :");
        users.forEach(user -> {
            System.out.println(user.getId() + " * " + user.getPrenom() + " * " + user.getNom());
        });
    }
}
