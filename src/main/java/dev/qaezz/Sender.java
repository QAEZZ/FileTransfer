package dev.qaezz;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Sender {
    private String targetAddress = null;
    private String localFilePath = null;
    private String targetFilePath = null;

    public Sender(String targetAddress, String localFilePath, String targetFilePath) {
        this.targetAddress = targetAddress;
        this.localFilePath = localFilePath;
        this.targetFilePath = targetFilePath;
    }

    public void Send() {
        try {
            byte[] localFileContents = Files.readAllBytes(Paths.get(this.localFilePath));

            String localFileContentsEncoded = Base64.getEncoder().encodeToString(localFileContents);
            String targetFilePathEncoded = Base64.getEncoder().encodeToString(this.targetFilePath.getBytes());
            String payload = String.format("%s:%s", targetFilePathEncoded, localFileContentsEncoded);

            Socket sender = new Socket(this.targetAddress, 773);

            DataOutputStream out = new DataOutputStream(sender.getOutputStream());

            System.out.println("Sending payload...");
            out.writeUTF(payload);
            out.flush();
            out.close();

            sender.close();

            System.out.println("Sent and closed connection!");
            return;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            // e.printStackTrace();
            return;
        }
    }
}
