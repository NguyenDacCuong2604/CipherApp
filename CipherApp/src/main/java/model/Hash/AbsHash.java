package model.Hash;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

public abstract class AbsHash implements IHash{
    public String name;
    MessageDigest messageDigest;
    @Override
    public void instance(String method){
        try {
            Security.addProvider(new BouncyCastleProvider());
            this.messageDigest = MessageDigest.getInstance(method);
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    @Override
    public String hashFile(File file){
        try {
            this.messageDigest.reset();
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[1024];
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
    public String hashText(String plainText){
        this.messageDigest.reset();
        byte[] hashBytes = this.messageDigest.digest(plainText.getBytes());
        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
