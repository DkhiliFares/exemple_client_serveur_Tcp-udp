package multiClientTcp;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // Mettez l'adresse IP ou le nom d'hôte du serveur ici
        final int PORT = 1234; // Mettez le port sur lequel le serveur écoute ici

        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connecté au serveur. Vous pouvez commencer à envoyer des messages.");

            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);

                // Lecture de la réponse du serveur
                String serverResponse = in.readLine();
                System.out.println("Réponse du serveur : " + serverResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
