package com.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
     * Crée un socket, et crée le writer et le reader pour communiquer avec le server
     */
    public void startConnection(String ip, int port) throws IOException{
        try {
            clientSocket = new Socket(ip, port);        // crée un socket pour se connecter au server
            out = new PrintWriter(clientSocket.getOutputStream(), true);        // écrit au server
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));      // lis la réponse du server
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Demande au user de choisir une action et récupère sa réponse
     * @return La réponse du user
     */
    public String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter action (enter /help to view all actions) : ");
        return scanner.nextLine();
    }

    /**
     * Appelle les méthodes du server en fonction des actions
     * @return La réponse du server
     */
    public String processAction(String action, ClientMain client) throws IOException {
        String[] actionSplit = action.split(" ");

        switch (actionSplit[0]) {
            case "/register", "/login" -> {
                return client.sendMessage(action);
            }
            case"/disconnect" -> {
                return client.stopConnection();
            }
//            case"/private_chat" -> {
//                return;
//            }
//            case"/accept" -> {
//                return;
//            }
//            case"/decline" -> {
//                return;
//            }
//            case"/exit_private_chat" -> {
//                return;
//            }
            case "/help" -> {
                return """
                    Available actions :\s
                    /register [username] [password]\s
                    /login [username] [password]\s
                    /disconnect\s
                    """;
            }
            default -> {
                return """
                    This action is not available. Available actions :\s
                    /register [username] [password]\s
                    /login [username] [password]\s
                    /disconnect\s
                    """;
            }
        }
    }

    /**
     * Envoie une string au server
     * @return La réponse du server
     */
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    /**
     * Demande au client quoi faire (register, login, help) et le process
     */
    public void askAndProcessActionBeforeLogin(ClientMain client) throws IOException {
        while (true) {
            String action = client.getAction();

            String messageAction = client.processAction(action, client);

            if (Objects.equals(action, "/disconnect")) {
                return;
            } else if (messageAction.startsWith("ok")) {
                if (action.startsWith("/register")) {
                    System.out.println("You are registered");
                } else if (action.startsWith("/login")) {
                    System.out.println("You are logged");
                }
                break;
            } else {
                System.out.println(messageAction);
            }
        }
    }

    /**
     * ....
     */
    public void getUserFromServer(String msg) throws IOException {
        out.print(msg);
        System.out.println(in.read());
    }

    /**
     * Interromp la connexion au server
     * @return Une string pour pouvoir vérifier que la déconnection a bien été effectuée
     */
    public String stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        return "disconnected";
    }

    /**
     * Appelle toutes les méthodes dont on a besoin pour faire fonctionner le client
     */
    public static void StartingClient(String[] args) throws IOException {
        // ajouter les erreurs d'arguments

        ClientMain client = new ClientMain();

        String ipServer = args[0];
        int port = Integer.parseInt(args[1]);

        client.startConnection(ipServer, port);

        client.askAndProcessActionBeforeLogin(client);
    }
}
