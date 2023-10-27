package model.ASysmmetricEncryption;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IASymmetricEncryption {
    public String encrypt(String plainText);
    public String decrypt(String cipherText);
    public PublicKey convertPublicKey(String key);
    public PrivateKey convertPrivateKey(String key);
    public void instance(String method);
}
