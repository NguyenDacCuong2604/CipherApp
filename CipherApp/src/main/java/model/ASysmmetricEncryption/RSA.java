package model.ASysmmetricEncryption;

import constant.Constants;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA extends AbsASymmetricEncryption{
    public RSA(){
        this.name = Constants.Cipher.RSA;
    }
    @Override
    public String encrypt(String plainText) {
        return null;
    }

    @Override
    public String decrypt(String cipherText) {
        return null;
    }

    @Override
    public PublicKey convertPublicKey(String key) {
        return null;
    }

    @Override
    public PrivateKey convertPrivateKey(String key) {
        return null;
    }
}
