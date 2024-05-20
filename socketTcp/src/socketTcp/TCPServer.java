package socketTcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Server is listening on port 6789");

        while (true) {
        	// assertion de la connexion 
            Socket connectionSocket = serverSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            OutputStream outToClient = connectionSocket.getOutputStream();

            String clientMessage = inFromClient.readLine();
            System.out.println("Received: " + clientMessage);

            String response = "Message received\n";
            outToClient.write(response.getBytes());

            connectionSocket.close();
        }
    }
}
