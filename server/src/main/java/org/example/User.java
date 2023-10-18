package org.example;

public class User {
    private int id;
    private String username;

    User(int idDB, String usernameDB) {
        id = idDB;
        username = usernameDB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}