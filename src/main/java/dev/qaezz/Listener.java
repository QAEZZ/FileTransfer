package dev.qaezz;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.net.SocketException;

public class Listener {
    private Socket socket = null;
    private ServerSocket reciever = null;
    private DataInputStream dataIn = null;
    public void Listen() throws IOException {
        int port = 773;
        reciever = new ServerSocket(port);

        System.out.println("Waiting for connections.");
        while (true) {
            try (socket = reciever.accept()){
                System.out.println("New connection, handling.");
                handleClient(socket);
            }
            catch (SocketException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private void handleClient(Socket socket) {

    }
}
