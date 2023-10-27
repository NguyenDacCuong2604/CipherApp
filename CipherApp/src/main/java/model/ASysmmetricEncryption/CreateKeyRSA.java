package model.ASysmmetricEncryption;

import javax.swing.*;
import java.security.*;
import java.util.Base64;

public class CreateKeyRSA {
    PrivateKey privateKey;
    PublicKey publicKey;
    public CreateKeyRSA(){

    }
    public void createKey(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public String getPrivateKey(){
        byte[] keyBytes = this.privateKey.getEncoded();
        String keyString = bytesToChar(keyBytes);
        return keyString;
    }
    public String getPublicKey(){
        byte[] keyBytes = this.publicKey.getEncoded();
        String keyString = bytesToChar(keyBytes);
        return keyString;
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    private String bytesToChar(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}