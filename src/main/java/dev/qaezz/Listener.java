package dev.qaezz;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Listener {
    private ServerSocket receiver = null;

    public void Listen(boolean overwrite) throws SocketException {
        int port = 1122;
        try {
            receiver = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Waiting for connections.");
        while (true) {
            try {
                Socket socket = receiver.accept();
                System.out.println("New connection, handling.");
                handleClient(socket, overwrite);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                return;
            }
        }
    }

    private void handleClient(Socket socket, boolean overwrite) throws IOException {
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        String[] payload = dataIn.readUTF().split(":");

        String homeDir = System.getProperty("user.home");
        String filePath = new String(Base64.getDecoder().decode(payload[0]));
        filePath = filePath.replace("~", homeDir); // Java is so shit :broken_heart:

        String fileSize = new String(Base64.getDecoder().decode(payload[1]));
        String fileContent = new String(Base64.getDecoder().decode(payload[2]));

        System.out.printf("Received %s bytes from %s and saving to `%s`.\n", fileSize,
                (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", ""),
                filePath);
        socket.close();

        Path path = Paths.get(filePath);
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (Files.exists(path) && !overwrite) {
            System.out.printf("File `%s` already exists, to overwrite pass in '--overwrite' at the end.\n\n", filePath);
            return;
        } else if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, fileContent);
        System.out.printf("Saved %s bytes to `%s`.\n\n", fileSize, filePath);

    }

}
