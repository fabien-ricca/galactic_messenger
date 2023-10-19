package com.example.server.controller;

import com.example.server.model.UserModel;
import com.example.server.service.PasswordEncryptionService;
import com.example.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

@Controller
public class UserController {

    private static UserService userService;
    private static List<UserModel> connectedUsers = new ArrayList<>();


    /**
     * <strong>*** Constructeur ***</strong>
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Méthode pour inscrire un utilisateur si login et password sont ok.
     */
    public static String register(String[] inputSplit, PrintWriter out) {

        if(!checkLoginExist(inputSplit[1])){
            return "Ce login n'est pas disponible.";
        }

        if(!passwordIsValid(inputSplit[2])){
            return "Le mot de passe doit comporter 8 caractères min, au moins 1 caractère spéciale et au moins 1 majuscule";
        }


        try{
            UserModel user = new UserModel();
            user.setLogin(inputSplit[1]);
            user.setPassword(PasswordEncryptionService.hashPassword(inputSplit[2]));

            userService.register(user);
            return "Inscription réussie.";
        }
        catch(DataIntegrityViolationException e){
            return "Erreur lors de l'inscription en table";
        }
    }


    /**
     * Méthode pour vérifier les informations lors d'une demande de connexion.
     */
    public static String login(String[] inputSplit, PrintWriter out) {
        UserModel user = new UserModel();

        if(!checkLoginExist(inputSplit[1])){
            return "Login ou mot de passe incorrect.";
        }

        user = userService.findUser(inputSplit[1]);

        if(!user.getPassword().equals(PasswordEncryptionService.hashPassword(inputSplit[2]))){
            return "Login ou mot de passe incorrect.";
        }

        connectedUsers.add(user);
        return "Connexion réussie.";
    }


    /**
     * Méthode pour vérifier si le login existe déjà en table.
     */
    private static boolean checkLoginExist(String login){
        return userService.checkLogin(login);
    }


    /**
     * Vérifie que le mot de passe remplisse les conditions suivantes :<br>
     * - Minimum 8 caractères.<br>
     * - Au moins une majuscule.<br>
     * - Au moins un chiffre.<br>
     */
    private static boolean passwordIsValid(String password){
        final String regex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
