package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {

    // Socket su client.
    private Socket clientSocket;

    //Output à afficher au client.
    private PrintWriter out;

    // Input saisi par le client.
    private BufferedReader in;


    /**
     * Méthode pour se connecter au server.
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);        // crée un socket pour se connecter
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    /**
     * Méthode pour envoyer un message vers le serveur.
     */
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }


    /**
     * ....
     */
    public void getUserFromServer(String msg) throws IOException {
        out.print(msg);
        System.out.println(in.read());
    }


    /**
     * Méthode pour interrompre la connexion au server.
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    /**
     * Méthode pour démarrer la connection au server.
     */
    public static void StartingClient(String[] args) throws IOException {
        ClientMain client = new ClientMain();

        String ipServer = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            client.startConnection(ipServer, port);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter action (enter /help to view all actions) : ");
        String action = scanner.nextLine();
        String[] actionSplit = action.split(" ");

        if(Objects.equals(actionSplit[0], "/register") || Objects.equals(actionSplit[0], "/login")) {
            client.getUserFromServer(action);

        }else if(Objects.equals(actionSplit[0], "/help")) {
            System.out.print("""
                    Available actions :\s
                    /register [username] [password]\s
                    /login [username] [password]""");
        }





//        String response = client.sendMessage("hello server");
//        System.out.println("message envoyé");
//
//        if("hello client".equals(response)){
//            System.out.println("Réponse du server : " + response);
//        }
    }
}
