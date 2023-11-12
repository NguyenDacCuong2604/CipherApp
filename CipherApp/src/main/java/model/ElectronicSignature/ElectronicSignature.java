package model.ElectronicSignature;

import constant.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;


public class ElectronicSignature {
    public String name;
    MessageDigest messageDigest;
    public ElectronicSignature(){
        this.name = Constants.Description.E_SIGNATURE;
        instance(Constants.Cipher.SHA_256);
    }
    public void instance(String method){
        try {
            Security.addProvider(new BouncyCastleProvider());
            this.messageDigest = MessageDigest.getInstance(method);
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean verify(String filePath, String hashCode){
        try {
            File file = new File(filePath);
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
            return hexString.toString().equalsIgnoreCase(hashCode);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
