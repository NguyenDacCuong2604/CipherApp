package model.SysmmetricEncryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ISymmetricEncryption {
    void instance(String method);
    String encrypt(String plainText);
    String decrypt(String cipherText);
    String encryptFile(File file);
    String decryptFile(File file);
    String createKey();
    void convertKey(String key);
    String createIv();
    void convertIv(String ivSpec);
}
