package multiClientTcp;

import java.io.*;
import java.net.*;

public class ConcurrentDelayedTCPServer {
    public static void main(String[] args) {
        final int PORT = 1234;
        final int MAX_CLIENTS = 10;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré. En attente de connexions...");

            int clientCount = 0;
            while (clientCount < MAX_CLIENTS) {
                // Attente d'une connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté depuis " + clientSocket.getInetAddress());

                // Création d'un nouveau thread pour gérer la communication avec le client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();

                clientCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message du client " + clientSocket.getInetAddress() + ": " + message);

                    // Simulation d'un traitement long en ajoutant un délai de sommeil
                    try {
                        Thread.sleep(2000); // Délai de 2 secondes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    out.println("Message reçu : " + message);
                }
            } catch (IOException e) {
                System.out.println("Client " + clientSocket.getInetAddress() + " déconnecté.");
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Connexion avec le client " + clientSocket.getInetAddress() + " fermée.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
