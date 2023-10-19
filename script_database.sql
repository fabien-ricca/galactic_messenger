-- Création de la base de données
CREATE DATABASE IF NOT EXISTS galactic_messenger;
USE galactic_messenger;


-- Création des tables
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50),
    password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sender_id INT,
    receiver_id INT,
    thread_id INT,
    content TEXT,
    time DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS thread (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS thread_users (
    thread_id INT,
    user_id INT,
    PRIMARY KEY (thread_id, user_id)
);



-- Ajout des clés étrangères
ALTER TABLE messages
    ADD CONSTRAINT FK_sender
        FOREIGN KEY (sender_id) REFERENCES users(id),
    ADD CONSTRAINT FK_receiver
        FOREIGN KEY (receiver_id) REFERENCES users(id),
    ADD CONSTRAINT FK_thread
        FOREIGN KEY (thread_id) REFERENCES thread(id);

ALTER TABLE thread_users
    ADD CONSTRAINT FK_thread_users_thread
        FOREIGN KEY (thread_id) REFERENCES thread(id),
    ADD CONSTRAINT FK_thread_users_user
        FOREIGN KEY (user_id) REFERENCES users(id);



-- Ajout d'un utilisateur
-- Mot de passe en clair : Bonjour@123
INSERT INTO users (login, password) VALUES ('Piou-piou', '8e5a8b53018b19939d3ab7ac67d24a01f0c42f2a368d4c5755f0e5368379948bd0ee566be87361794b9f6f914ef46562d2afb4a6e52342611bdcb426d98d8fe2');
INSERT INTO users (login, password) VALUES ('Padawan', '8e5a8b53018b19939d3ab7ac67d24a01f0c42f2a368d4c5755f0e5368379948bd0ee566be87361794b9f6f914ef46562d2afb4a6e52342611bdcb426d98d8fe2');
INSERT INTO users (login, password) VALUES ('Aiste', '8e5a8b53018b19939d3ab7ac67d24a01f0c42f2a368d4c5755f0e5368379948bd0ee566be87361794b9f6f914ef46562d2afb4a6e52342611bdcb426d98d8fe2');
INSERT INTO users (login, password) VALUES ('Ajanis', '8e5a8b53018b19939d3ab7ac67d24a01f0c42f2a368d4c5755f0e5368379948bd0ee566be87361794b9f6f914ef46562d2afb4a6e52342611bdcb426d98d8fe2');
