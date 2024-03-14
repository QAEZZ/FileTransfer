package dev.qaezz;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

public class Listener {
    private Socket socket = null;
    private ServerSocket reciever = null;
    private DataInputStream dataIn = null;

    public void Listen() throws SocketException {
        int port = 773;
        try {
            reciever = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for connections.");
        while (true) {
            try {
                socket = reciever.accept();
                System.out.println("New connection, handling.");
                handleClient(socket);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private void handleClient(Socket socket) throws IOException {
        dataIn = new DataInputStream(socket.getInputStream());
        String payload = dataIn.readUTF();
        System.out.println("Received payload from client:\n" + payload);
        return;
    }
}
