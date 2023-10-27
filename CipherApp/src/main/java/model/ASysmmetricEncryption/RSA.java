package model.ASysmmetricEncryption;

import constant.Constants;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA extends AbsASymmetricEncryption{
    public RSA(){
        this.name = Constants.Cipher.RSA;
    }
    @Override
    public String encrypt(String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try{
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
            byte[] decryptedBytes = cipher.doFinal(cipherBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public PublicKey convertPublicKey(String key) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return this.publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public PrivateKey convertPrivateKey(String key) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return this.privateKey = keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void instance(String method) {
        try {
            this.cipher = Cipher.getInstance(method);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
