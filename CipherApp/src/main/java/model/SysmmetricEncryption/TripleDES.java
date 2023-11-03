package model.SysmmetricEncryption;

import constant.Constants;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class TripleDES extends AbsSymmetricEncryption {
    public TripleDES(){
        this.size = 24;
        this.iv = 8;
        this.name = Constants.Cipher.TRIPLE_DES;
    }
    @Override
    public void instance(String method) {
        try {
            this.method = method;
            if(method.contains(Constants.Padding.ZEROPADDING)){
                String type = method;
                this.cipher = Cipher.getInstance(type.replace(Constants.Padding.ZEROPADDING, Constants.Padding.NOPADDING));
            }
            else this.cipher = Cipher.getInstance(method);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String encrypt(String plainText) {
        try {
            if(secretKey==null) return null;
            if(method.contains(Constants.Mode.ECB)){
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            }
            else cipher.init(Cipher.ENCRYPT_MODE , this.secretKey, this.ivSpec);

            byte[] messageBytes = plainText.getBytes("UTF-8");
            if(method.contains(Constants.Padding.ZEROPADDING)) {
                int blockSize = 8; // TripleDES block size is 8 bytes
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
            if(secretKey==null) return null;
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
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(168);
            SecretKey key = keyGenerator.generateKey();
            byte[] keyBytes = key.getEncoded();
            String keyString = bytesToHex(keyBytes).substring(0, 24);
            return keyString;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public SecretKey convertKey(String key) {
        byte[] keyData = key.getBytes(StandardCharsets.UTF_8);
        if (keyData.length != 24) {
            byte[] paddedKeyData = new byte[24];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }

        SecretKey secretKey = new SecretKeySpec(keyData, "DESede");
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
        byte[] iv = new byte[8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return bytesToHex(iv).substring(0, 8);
    }

    @Override
    public IvParameterSpec convertIv(String ivSpec) {
        byte[] ivData = ivSpec.getBytes(StandardCharsets.UTF_8);
        if(ivData.length == 0){
            ivData = ivGenerate();
        }
        else if (ivData.length != 8) {
            byte[] paddedIvData = new byte[8];
            System.arraycopy(ivData, 0, paddedIvData, 0, ivData.length);
            ivData = paddedIvData;
        }
        this.ivSpec = new IvParameterSpec(ivData);
        return this.ivSpec;
    }
    private byte[] ivGenerate(){
        byte[] data = new byte[8];
        for(int i=0; i<data.length; i++){
            //char 0
            data[i] = 48;
        }
        return data;
    }
}
