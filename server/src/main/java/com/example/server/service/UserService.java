package com.example.server.service;

import com.example.server.model.UserModel;
import com.example.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Propriété permettant d'accéder aux méthodes de UserRepository.
    private final UserRepository userRepository;


    /**
     * <strong>*** Constructeur ***</strong>
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Méthode pour ajouter un utilisateur dans la table `users`.
     */
    public UserModel register(UserModel user){
        return userRepository.save(user);
    }


    /**
     * Méthode pour vérifier si un login existe dans la table `users`.<br>
     * Retourne true si existe.
     */
    public boolean checkLogin(String login){
        return userRepository.existsByLogin(login);
    }


    /**
     * Méthode pour récupérer les données d'un utilisateur via son login.<br>
     * Retourne un objet UserModel de l'utilisateur.
     */
    public UserModel findUser(String login){
        return userRepository.findByLogin(login);
    }
}
