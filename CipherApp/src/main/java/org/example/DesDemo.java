package org.example;

import constant.Constants;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class DesDemo {
    public static void main(String[] args) throws Exception {
        // Create a KeyGenerator for TripleDES
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");

        // Generate a TripleDES key with a specific key size (e.g., 168 bits)
        keyGen.init(168);
        SecretKey tripleDesKey = keyGen.generateKey();

        // The generated key can be used for encryption and decryption
        System.out.println("Generated TripleDES Key: " + tripleDesKey.getEncoded().length);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    public static String keyToString(SecretKey key) {
        byte[] keyBytes = key.getEncoded();
        String keyString = bytesToHex(keyBytes).substring(0, 8); // Lấy 8 ký tự đầu
        return keyString;
    }



    public static String encryptBase64(String plainText, SecretKey key, String iv) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] messageBytes = plainText.getBytes("UTF-8");
        System.out.println(messageBytes.length);
        // Add zero-padding to the message
        int blockSize = 8; // DES block size is 8 bytes
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
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("DES/CTR/PKCS5Padding");
        cipher.init(1 , key, ivParameterSpec);
//        byte[] byteText = plainText.getBytes("UTF-8");
        byte[] byteEncrypt = cipher.doFinal(messageBytes);
        return Base64.getEncoder().encodeToString(byteEncrypt);
    }
    public static String decryptBase64(String encryptedText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            Cipher cipher = Cipher.getInstance("DES/CTR/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            // Remove zero-padding
//            int padding = 0;
//            for (int i = decryptedBytes.length - 1; i >= 0; i--) {
//                if (decryptedBytes[i] == 0) {
//                    padding++;
//                } else {
//                    break;
//                }
//            }

//            int originalLength = decryptedBytes.length - padding;
//            byte[] originalBytes = new byte[originalLength];
//            System.arraycopy(decryptedBytes, 0, originalBytes, 0, originalLength);

            return new String(decryptedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Handle encoding exception
            return null;
        }
    }
    public static SecretKey createDESKeyFromString(String inputKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        byte[] keyData = inputKey.getBytes(StandardCharsets.UTF_8);

        if (keyData.length != 8) {
            byte[] paddedKeyData = new byte[8];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }


        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        KeySpec keySpec = new DESKeySpec(keyData);
        return keyFactory.generateSecret(keySpec);
    }

    public  static SecretKey generateDESKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyData = new byte[8];
        secureRandom.nextBytes(keyData);
        // Tạo ngẫu nhiên 8 byte để tạo khóa DES
        // Trong thực tế, bạn có thể sử dụng nguồn ngẫu nhiên bảo mật hơn để tạo khóa
        // Đây chỉ là ví dụ đơn giản
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(new DESKeySpec(keyData));
        return key;
    }


}
