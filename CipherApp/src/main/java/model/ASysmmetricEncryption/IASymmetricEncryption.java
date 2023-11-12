package model.ASysmmetricEncryption;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IASymmetricEncryption {
    public String encrypt(String plainText, String type, String key);
    public String decrypt(String cipherText, String type, String key);
    public void convertPublicKey(String key);
    public void convertPrivateKey(String key);
    public void instance(String method);
}
