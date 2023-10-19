package com.example.server;

import com.example.server.controller.UserController;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerMain {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public void start(int port) throws IOException {
        String ipServer = "127.0.0.1";

        serverSocket = new ServerSocket(port);      // crée un socket pour écouter les demandes de co
        System.out.println("Server available at " + ipServer + ":" + port);

        clientSocket = serverSocket.accept();       // écoute le socket et accepte la connection entrante
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String input = in.readLine();
        System.out.println(input);
        String[] inputSplit = input.split(" ");

        if (Objects.equals(inputSplit[0], "/register")) {

            out.println(UserController.register(inputSplit, out));

        }
        else if(Objects.equals(inputSplit[0], "/login")){

            out.println(UserController.login(inputSplit, out));
        }

        System.out.println(input);
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }


    /**
     * Lancement du server
     */
    public static void ServerStarting(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);

        ServerMain server = new ServerMain();
        server.start(port);

    }
}
