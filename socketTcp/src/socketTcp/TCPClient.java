package socketTcp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost";
        int serverPort = 6789;
        Socket clientSocket = new Socket(serverAddress, serverPort);
        
        OutputStream outToServer = clientSocket.getOutputStream();
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String message = "Hello, Server\n";
        outToServer.write(message.getBytes());

        String response = inFromServer.readLine();
        System.out.println("Response from server: " + response);
        
        clientSocket.close();
    }
}
