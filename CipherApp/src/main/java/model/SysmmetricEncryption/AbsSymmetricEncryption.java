package model.SysmmetricEncryption;

import constant.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public abstract class AbsSymmetricEncryption implements ISymmetricEncryption{

    public String name;
    public String method;
    public int size;
    public int iv;
    public int sizeBlock;
    Cipher cipher;
    SecretKey secretKey;
    IvParameterSpec ivSpec;

    @Override
    public void instance(String method) {
        try {
            Security.addProvider(new BouncyCastleProvider());
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
                int blockSize = sizeBlock;
                int padding = blockSize - (messageBytes.length % blockSize);
                if (padding != blockSize) {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];
                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
                    messageBytes = paddedMessage;
                } else {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];
                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
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
            if(method.contains(Constants.Mode.ECB)){
                cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            }
            else cipher.init(Cipher.DECRYPT_MODE , this.secretKey, this.ivSpec);
            byte[] byteText = cipher.doFinal(byteEncrypt);
            int padding = 0;
            for (int i = byteText.length - 1; i >= 0; i--) {
                if (byteText[i] == 0) {
                    padding++;
                } else {
                    break;
                }
            }
            int originalLength = byteText.length - padding;
            byte[] originalBytes = new byte[originalLength];
            System.arraycopy(byteText, 0, originalBytes, 0, originalLength);
            return new String(originalBytes, "UTF-8");
        }catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                InvalidKeyException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String createKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(name);
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
    public void convertKey(String key) {
        byte[] keyData = key.getBytes(StandardCharsets.UTF_8);
        if (keyData.length <= this.size) {
            byte[] paddedKeyData = new byte[this.size];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }
        SecretKey secretKey = new SecretKeySpec(keyData, name);
        this.secretKey = secretKey;
    }

    @Override
    public String createIv() {
        byte[] ivBytes = new byte[iv];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        return bytesToHex(ivBytes).substring(0, iv);
    }

    @Override
    public void convertIv(String ivSpec) {
        byte[] ivData = ivSpec.getBytes(StandardCharsets.UTF_8);
        if(ivData.length == 0){
            ivData = ivGenerate();
        }
        else if (ivData.length != iv) {
            byte[] paddedIvData = new byte[iv];
            System.arraycopy(ivData, 0, paddedIvData, 0, ivData.length);
            ivData = paddedIvData;
        }
        this.ivSpec = new IvParameterSpec(ivData);
    }


    //hex
    String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    //generate char 0 in iv
    byte[] ivGenerate(){
        byte[] data = new byte[iv];
        for(int i=0; i<data.length; i++){
            //char 0
            data[i] = 48;
        }
        return data;
    }
}
