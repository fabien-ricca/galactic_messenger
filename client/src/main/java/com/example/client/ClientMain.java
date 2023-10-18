package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {


    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);        // crée un socket pour se connecter
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter action (enter /help to view all actions) : ");
        return scanner.nextLine();
    }

    public String processAction(String action, ClientMain client) throws IOException {
        String[] actionSplit = action.split(" ");

        switch (actionSplit[0]) {
            case "/register", "/login" -> {
                return client.sendMessage(action);
            }
            case"/deconnect" -> {
                return client.stopConnection();
            }
            case "/help" -> {
                return """
                    Available actions :\s
                    /register [username] [password]\s
                    /login [username] [password]\s
                    """;
            }
            default -> {
                return """
                    This action is not available. Available actions :\s
                    /register [username] [password]\s
                    /login [username] [password]\s
                    """;
            }
        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public String stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        return "deconnected";
    }

    public static void StartingClient(String[] args) throws IOException {
        ClientMain client = new ClientMain();

        String ipServer = args[0];
        int port = Integer.parseInt(args[1]);

        client.startConnection(ipServer, port);
        boolean ask = true;

        String action;

        while (ask) {
            action = client.getAction();

            String messageAction = client.processAction(action, client);

            System.out.println(messageAction);

            if (Objects.equals(action, "/deconnect")) {
                return;
            }else if (messageAction.startsWith("ok")) {
                switch(action) {
                    case "/register" -> {
                        System.out.println("You are registered");
                    }
                    case "/login" -> {
                        System.out.println("you are logged");
                    }
                }
                ask = false;
            }
        }






//        String response = client.sendMessage("hello server");
//        System.out.println("message envoyé");
//
//        if("hello client".equals(response)){
//            System.out.println("Réponse du server : " + response);
//        }
    }
}
