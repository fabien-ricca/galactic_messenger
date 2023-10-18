package org.example;

import java.io.PrintWriter;

public class Authenticate {
    private String usernameDB;
    private String passwordDB;

    public static void register(String[] inputSplit, PrintWriter out) {
        // send to DB

        int id = 1;

        User user = new User(id, inputSplit[1]);
        out.print(user);
    }

    public static void login(String[] inputSplit, PrintWriter out) {
        // check if password is the same as DB password

        out.println("you are logged");
    }
}
