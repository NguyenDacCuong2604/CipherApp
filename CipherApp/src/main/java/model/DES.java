package model;

import constant.Constants;
import screens.CipherScreen;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DES extends ASymmetricEncryption{
    @Override
    public void instance(String name, String mode, String padding) {
        this.mode = mode;
        this.padding = padding;
        String transformation = name+"/" + mode + "/" + padding;
        try {
            this.cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String encrypt(String plainText){
        try {
            byte[] messageBytes = plainText.getBytes("UTF-8");
            if(mode.equals(Constants.Mode.ECB)){
                cipher.init(Cipher.ENCRYPT_MODE , this.secretKey);
            }
            else cipher.init(Cipher.ENCRYPT_MODE , this.secretKey, this.ivSpec);
            byte[] byteEncrypt = cipher.doFinal(messageBytes);
            return Base64.getEncoder().encodeToString(byteEncrypt);
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    @Override
    public String decrypt(String cipherText){
        try {
            byte[] byteEncrypt = Base64.getDecoder().decode(cipherText);
            if(mode.equals(Constants.Mode.ECB)){
                cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            }
            else cipher.init(Cipher.DECRYPT_MODE , this.secretKey, this.ivSpec);
            byte[] byteText = cipher.doFinal(byteEncrypt);
            return new String(byteText, "UTF-8");
        }catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                InvalidKeyException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String createKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            SecretKey key = keyGenerator.generateKey();
            byte[] keyBytes = key.getEncoded();
            String keyString = bytesToHex(keyBytes).substring(0, 8); // Lấy 8 ký tự đầu
            return keyString;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    @Override
    public SecretKey convertKey(String key) {
        byte[] keyData = key.getBytes(StandardCharsets.UTF_8);

        if (keyData.length != 8) {
            byte[] paddedKeyData = new byte[8];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }

        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            KeySpec keySpec = new DESKeySpec(keyData);
            this.secretKey =  keyFactory.generateSecret(keySpec);
            return  this.secretKey;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String createIv() {
        byte[] iv = new byte[8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return bytesToHex(iv).substring(0, 8);
    }

    @Override
    public IvParameterSpec convertIv(String ivSpec) {
        byte[] ivData = ivSpec.getBytes(StandardCharsets.UTF_8);

        if (ivData.length != 8) {
            byte[] paddedIvData = new byte[8];
            System.arraycopy(ivData, 0, paddedIvData, 0, ivData.length);
            ivData = paddedIvData;
        }
        this.ivSpec = new IvParameterSpec(ivData);
        return this.ivSpec;
    }
}
