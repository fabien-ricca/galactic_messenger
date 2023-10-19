package com.example.server.repository;

import com.example.server.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    /**
     * Méthode pour vérifier si un login est présent dans la table 'users'.
     */
    boolean existsByLogin(String login);


    /**
     * Méthode pour récupérer toutes les données d'un utilisateur via son login.
     */
    UserModel findByLogin(String login);
}
