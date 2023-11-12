package model.SysmmetricEncryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ISymmetricEncryption {
    public void instance(String method);
    public String encrypt(String plainText);
    public String decrypt(String cipherText);
    public String createKey();
    public void convertKey(String key);
    public String createIv();
    public void convertIv(String ivSpec);
}
