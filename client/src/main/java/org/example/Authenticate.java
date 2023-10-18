package org.example;

import java.io.IOException;

public class Authenticate {

    public static void register(String[] actionSplit, Main client) throws IOException {
        User user = new User();
        client.getUserFromServer(actionSplit[0] + " " + actionSplit[1] + " " + actionSplit[2]);
    }

    public static void login(String[] actionSplit, Main client) {

    }
}
