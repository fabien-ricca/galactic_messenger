package com.example.server.repository;

import com.example.server.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    boolean existsByLogin(String login);

    UserModel findByLogin(String login);
}
