package dev.qaezz;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Inspired by the SCP protocol.\nUsage: ft file user@ip:location");
            return;
        }
        String file = args[0];
        String[] targetInfo = args[1].split(":");
        String targetSsh = targetInfo[0];
        String targetPath = targetInfo[1];
    }
}