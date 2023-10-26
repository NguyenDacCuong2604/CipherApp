package org.example;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Des {
    private SecretKey key;

    private SecretKey createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        key = keyGenerator.generateKey();
        return key;
    }

    private byte[] encrypt(String plainText) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] byteText = plainText.getBytes("UTF-8");
        return cipher.doFinal(byteText);
    }
    private String encryptBase64(String plainText) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] byteText = plainText.getBytes("UTF-8");
        byte[] byteEncrypt = cipher.doFinal(byteText);
        return Base64.getEncoder().encodeToString(byteEncrypt);
    }
    private String encryptBase64NoPadding(String plainText) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(1 , key);
        byte[] byteText = plainText.getBytes("UTF-8");
        byte[] byteEncrypt = cipher.doFinal(byteText);
        return Base64.getEncoder().encodeToString(byteEncrypt);
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    private String decrypt(byte[] byteEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }

    private String decrypt(String encryptText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key);
        byte[] byteEncrypt = Base64.getDecoder().decode(encryptText);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }
    private String decryptNoPadding(String encryptText) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(2, key);
        byte[] byteEncrypt = Base64.getDecoder().decode(encryptText);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }

    private boolean encryptFile(String sourceFile, String desFile) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(key == null) throw new NoSuchAlgorithmException();
        File source = new File(sourceFile);
        if(!source.exists()) return false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1 , key);
        byte[] arr = new byte[64];
        int byteRead;
        while((byteRead = bis.read(arr))!=-1){
            byte[] byteEncrypt = cipher.update(arr, 0, byteRead);
            bos.write(byteEncrypt);
        }
        byte[] output = cipher.doFinal();
        if(output!=null) bos.write(output);
        bis.close();
        bos.flush();
        bos.close();
        return true;
    }
    private boolean decryptFile(String sourceFile, String desFile) throws IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {
        if(key == null) throw new NoSuchAlgorithmException();
        File source = new File(sourceFile);
        if(!source.exists()) return false;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2 , key);
        byte[] arr = new byte[64];
        int byteRead;
        while((byteRead = bis.read(arr))!=-1){
            byte[] byteDecrypt = cipher.update(arr, 0, byteRead);
            bos.write(byteDecrypt);
        }
        byte[] output = cipher.doFinal();
        if(output!=null) bos.write(output);
        bis.close();
        bos.flush();
        bos.close();
        return true;
    }
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
            return  keyFactory.generateSecret(keySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            return null;
        }
    }



    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Des des = new Des();
        System.out.println(des.createKey().toString());
        String plainText = "N";
        System.out.println(plainText);
        System.out.println();
        des.setKey(des.convertKey("cuong"));
        byte[] keyByte = des.key.getEncoded();
        System.out.println(Arrays.toString(keyByte));

        String encryptText = des.encryptBase64(plainText);
        System.out.println(encryptText);
        String decryptText = des.decrypt(encryptText);
        System.out.println(decryptText);
        System.out.println();
        String encryptTextNoPadding = des.encryptBase64NoPadding(plainText);
        System.out.println(encryptTextNoPadding);
        String decryptTextNoPadding = des.decryptNoPadding(encryptTextNoPadding);
        System.out.println(decryptTextNoPadding);
        System.out.println();
    }


}

