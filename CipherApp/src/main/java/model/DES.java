package model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DES extends ASymmetricEncryption{
    @Override
    public String encrypt(String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        if(this.secretKey == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE , this.secretKey);
        byte[] byteText = plainText.getBytes("UTF-8");
        byte[] byteEncrypt = cipher.doFinal(byteText);
        return Base64.getEncoder().encodeToString(byteEncrypt);
    }

    @Override
    public String decrypt(String cipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(this.secretKey == null) throw new NoSuchAlgorithmException();
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        byte[] byteEncrypt = Base64.getDecoder().decode(cipherText);
        byte[] byteText = cipher.doFinal(byteEncrypt);
        return new String(byteText);
    }

    @Override
    public String createKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);
        this.secretKey = keyGenerator.generateKey();
        byte[] keyBytes = this.secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    @Override
    public SecretKey convertKey(String key) {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        // Create a SecretKey from the key bytes
        this.secretKey =  new SecretKeySpec(keyBytes, "DES");
        return this.secretKey;
    }
}
