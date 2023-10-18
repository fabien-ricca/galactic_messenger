package com.example.server.controller;

import com.example.server.model.UserModel;
import com.example.server.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {

    private static UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    public static boolean register(String[] inputSplit, PrintWriter out) {

        UserModel user = new UserModel();
            user.setLogin(inputSplit[1]);
            user.setPassword(inputSplit[2]);

        userService.register(user);
        return true;

    }

    public static String login(String[] inputSplit, PrintWriter out) {

        UserModel user = new UserModel();
            user.setLogin(inputSplit[1]);
            user.setPassword(inputSplit[2]);

        // Methode pour recuperer donnees pour connexion

        return "you are logged";
    }
}
