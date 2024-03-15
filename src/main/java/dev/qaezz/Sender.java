package dev.qaezz;

import java.io.DataOutputStream;
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

        String[] targetFilePathSplit = targetFilePath.split("/");
        String[] localFilePathSplit = localFilePath.split("/");

        if (!targetFilePathSplit[targetFilePathSplit.length - 1].contains(".")) {
            this.targetFilePath += "/" + localFilePathSplit[localFilePathSplit.length - 1];
        }
    }

    public void Send() {
        try {
            byte[] localFileContents = Files.readAllBytes(Paths.get(this.localFilePath));
            String localFileSize = Long.toString(Files.size(Paths.get(this.localFilePath)));

            String localFileContentsEncoded = Base64.getEncoder().encodeToString(localFileContents);
            String localFileSizeEncoded = Base64.getEncoder().encodeToString(localFileSize.getBytes());
            String targetFilePathEncoded = Base64.getEncoder().encodeToString(this.targetFilePath.getBytes());
            String payload = String.format("%s:%s:%s", targetFilePathEncoded, localFileSizeEncoded, localFileContentsEncoded);

            Socket sender = new Socket(this.targetAddress, 1122);

            DataOutputStream out = new DataOutputStream(sender.getOutputStream());

            System.out.printf("Sending '%s' (%s bytes) to %s:%s%n.\n", this.localFilePath, localFileSize, this.targetAddress, this.targetFilePath);
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
