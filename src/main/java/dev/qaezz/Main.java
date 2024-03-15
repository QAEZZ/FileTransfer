package dev.qaezz;

import java.net.SocketException;
import java.util.Objects;

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
                    boolean overwrite = args.length > 1 && Objects.equals(args[1], "--overwrite");
                    listener.Listen(overwrite);
                } catch(SocketException e) {
                    System.out.println("There was an error with the socket: " + e.getMessage());
                    return;
                }
                break;

            case "send":
                String file = args[1];
                String[] targetInfo = args[2].split(":");
                String targetAddress = targetInfo[0];
                String targetPath = targetInfo[1];

                Sender sender = new Sender(targetAddress, file, targetPath);
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