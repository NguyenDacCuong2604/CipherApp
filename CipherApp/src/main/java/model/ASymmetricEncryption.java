package model;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public abstract class ASymmetricEncryption implements ISymmetricEncryption{
    Cipher cipher;
    SecretKey secretKey;
    IvParameterSpec ivSpec;
    public String mode;
    public String padding;

}
