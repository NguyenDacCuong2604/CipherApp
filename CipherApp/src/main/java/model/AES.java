package model;

import constant.Constants;

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

public class AES extends ASymmetricEncryption{
    public AES(){
        this.size = 16;
        this.iv = 16;
        this.name = Constants.Cipher.AES;
    }
    @Override
    public void instance(String method) {
        try {
            this.method = method;
            this.cipher = Cipher.getInstance(method);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String encrypt(String plainText) {
        try {
            if(method.contains(Constants.Mode.ECB)){
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            }
            else cipher.init(Cipher.ENCRYPT_MODE , this.secretKey, this.ivSpec);
            byte[] messageBytes = plainText.getBytes("UTF-8");
            if(method.contains(Constants.Padding.NOPADDING)) {
                int blockSize = 16; // AES block size is 16 bytes
                int padding = blockSize - (messageBytes.length % blockSize);

                if (padding != blockSize) {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];

                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);

                    // Add zero-padding
                    for (int i = messageBytes.length; i < newLength; i++) {
                        paddedMessage[i] = 0;
                    }

                    messageBytes = paddedMessage;
                } else {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];

                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);

                    // Add zero-padding
                    for (int i = messageBytes.length; i < newLength; i++) {
                        paddedMessage[i] = 0;
                    }

                    messageBytes = paddedMessage;
                }
            }

            byte[] byteEncrypt = cipher.doFinal(messageBytes);
            return Base64.getEncoder().encodeToString(byteEncrypt);
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            byte[] byteEncrypt = Base64.getDecoder().decode(cipherText);
            int padding = 0;
            for (int i = byteEncrypt.length - 1; i >= 0; i--) {
                if (byteEncrypt[i] == 0) {
                    padding++;
                } else {
                    break;
                }
            }

            int originalLength = byteEncrypt.length - padding;
            byte[] originalBytes = new byte[originalLength];
            System.arraycopy(byteEncrypt, 0, originalBytes, 0, originalLength);
            if(method.contains(Constants.Mode.ECB)){
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
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(this.size*8);
            SecretKey key = keyGenerator.generateKey();
            byte[] keyBytes = key.getEncoded();
            String keyString = bytesToHex(keyBytes).substring(0,this.size);
            return keyString;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public SecretKey convertKey(String key) {
        byte[] keyData = key.getBytes(StandardCharsets.UTF_8);

        if (keyData.length != this.size) {
            byte[] paddedKeyData = new byte[this.size];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }

        SecretKey secretKey = new SecretKeySpec(keyData, "AES");
        this.secretKey =  secretKey;
        return  this.secretKey;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    @Override
    public String createIv() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return bytesToHex(iv).substring(0, 16);
    }

    @Override
    public IvParameterSpec convertIv(String ivSpec) {
        byte[] ivData = ivSpec.getBytes(StandardCharsets.UTF_8);

        if (ivData.length != 16) {
            byte[] paddedIvData = new byte[16];
            System.arraycopy(ivData, 0, paddedIvData, 0, ivData.length);
            ivData = paddedIvData;
        }
        this.ivSpec = new IvParameterSpec(ivData);
        return this.ivSpec;
    }
}
