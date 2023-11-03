package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public static void main(String[] args) {
        String plainText = "Nguyen Dac Cuong";
        String filePath = "D:\\Download\\How To Make Desktop Look Awesome (PART 8).zip";
        System.out.println(hashPlainText(plainText));
        System.out.println(hashFileText(filePath));
    }
    public static String hashPlainText(String plainText){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes());

            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available.");
            return null;
        }
    }

    public static String hashFileText(String filePath){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();

            byte[] hash = digest.digest();

            return bytesToHex(hash);

         } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

        public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

