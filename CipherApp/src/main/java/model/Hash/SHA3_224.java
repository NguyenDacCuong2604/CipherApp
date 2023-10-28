package model.Hash;

import constant.Constants;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3_224 extends AbsHash{
    public SHA3_224(){
        this.name = Constants.Cipher.SHA3_224;
        instance(this.name);
    }

    @Override
    public void instance(String method) {
        try {
            this.messageDigest = MessageDigest.getInstance(method);
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String hashFile(String pathSource) {
        try {
            File file = new File(pathSource);
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                this.messageDigest.update(buffer, 0, bytesRead);
            }

            fileInputStream.close();

            byte[] hashBytes = this.messageDigest.digest();

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String hashText(String plainText) {
        byte[] hashBytes = this.messageDigest.digest(plainText.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
