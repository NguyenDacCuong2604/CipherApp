package org.example;

import constant.Constants;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DesDemo {
    public static void main(String[] args) throws Exception {

        PrivateKey privateKey = (PrivateKey) convertPublicKey("MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAhkGSOyi6bTyk9LwasQ3DZZFS4VGo8o/pDZKMO8aCZdZpSCQod0XoH2BVy7uJsh9u59LdD1S1fY7ZPUhGT9zio0Pk4vh7cIll+k0wsQtpQ83rq61SPU5BV/thjeoF3jemvW3tKe7JIn60fj9yz+lk8SnJVrg0xISMyArZJMaiRdILijy+nnB1/1x/BerPgymbqKZkpv8on09IsD6aSqml69+zL1VVxprr2lSTuobeFRbQGjPI64zS/Utd/UCU2P6M60Wt4BCkwnoTUetO2IxS0hkxrpYBQvhchds1nhENcVoDVZxnpdQGw6LUaemmanbys3WeyR49xaNLI/gASAPBXBW+aiZpMtcRV18dFw2k8NwIxRGvGqs5ZY8dxTe0xUYWNXT3+AQFm5hEHGKclhdeQi+OsYmbKwlUDgBnwhTs4kVN1xMfBbCTG9cUIniVzt7tydAqdKY+29W8II2whhSQBi6tUKg3LaJ2ddMA6BlJUqH/ZJqaiEWP7Pz85TvqC6pOLzNv8G9UUKbixpc33LvNq4eFfHch/xSsQBbnYT0xRWUYYyPfyEgMH4VmnM3q1KUAuA4s03mCWEczRHvN9nJbo68PMO4VQQCHLgKy2e1WuF+GJdBqOC7+n18KiuCPTIbgD0UwjQIrpmwaJU8SujtK6P12ZBVuWnAir7JaeIvl9+kCAwEAAQ==");

    }
    public String encrypt(String plainText, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static PublicKey convertPublicKey(String key) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
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
