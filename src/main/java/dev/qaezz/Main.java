package dev.qaezz;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Inspired by the SCP protocol.\n" + //
                    "Usage: ft listen\n" + //
                    "       ft send <file> <address:path>");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "listen":
                Listener listener = new Listener();
                try {
                    listener.Listen();
                } catch(SocketException e) {
                    System.out.println("There was an error with the socket: " + e.getMessage());
                    return;
                }
                break;

            case "send":
                String file = args[1];
                String[] targetInfo = args[2].split(":");
                String targetAdress = targetInfo[0];
                String targetPath = targetInfo[1];

                System.out.println(file + "\n" + targetAdress + "\n" + targetPath);
                Sender sender = new Sender(targetAdress, file, targetPath);
                sender.Send();
                break;

            default:
                System.out.println("Inspired by the SCP protocol.\n" + //
                        "Usage: ft listen\n" + //
                        "       ft send <file> <address:path>");
                break;
        }
    }
}