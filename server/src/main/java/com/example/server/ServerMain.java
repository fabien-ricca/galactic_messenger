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

    // Socket du server.
    private ServerSocket serverSocket;

    // Socket du client.
    private Socket client;

    /**
     * Méthode pour démarrer le server.
     */
    public void start(int port) throws IOException {
        String ipServer = "127.0.0.1";

        serverSocket = new ServerSocket(port);      // crée un socket pour écouter les demandes de co
        System.out.println("Server available at " + ipServer + ":" + port);
    }

    /**
     * Méthode pour accepter la connection et créer les outils pour communiquer avec le client
     */
    public void connect() throws IOException {
        client = serverSocket.accept();       // écoute le socket et accepte la connection entrante

        System.out.println("New client connected" + client.getInetAddress().getHostAddress());

        ClientHandler clientSock = new ClientHandler(client);
        new Thread(clientSock).start();
    }


    /**
     * Méthode pour éteindre le server.
     */
    public void close() throws IOException {
        client.close();
        serverSocket.close();
    }


    /**
     * Méthode pour instancier le server.
     */
    public static void ServerStarting(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);

        ServerMain server = new ServerMain();
        try {
            server.start(port);

            while (true) {
                server.connect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Output vers le client.
        PrintWriter out = null;

        // Input depuis le client.
        BufferedReader in = null;

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        @Override
        public void run() {

            try {
                // get the outputstream of client
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // get the inputstream of client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    sentData(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Méthode pour envoyer une String au client
         */
        public void sentData(String input) {
            String[] inputSplit = input.split(" ");
            String result;

            if (Objects.equals(inputSplit[0], "/register")) {
                result = UserController.register(inputSplit);

                out.println(!result.startsWith("Error") ? "ok register" : result);
            }
            else if(Objects.equals(inputSplit[0], "/login")){
                result = UserController.login(inputSplit);

                out.println(!result.startsWith("Incorrect") ? "ok login" : result);
            }

            System.out.println(input);
        }
    }
}
