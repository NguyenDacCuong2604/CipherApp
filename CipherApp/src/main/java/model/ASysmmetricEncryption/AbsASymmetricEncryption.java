package model.ASysmmetricEncryption;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public abstract class AbsASymmetricEncryption implements IASymmetricEncryption{
    public String name;
    Cipher cipher;
    PublicKey publicKey;
    PrivateKey privateKey;
}
