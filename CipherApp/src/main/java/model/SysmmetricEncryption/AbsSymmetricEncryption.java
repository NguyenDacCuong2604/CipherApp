package model.SysmmetricEncryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public abstract class AbsSymmetricEncryption implements ISymmetricEncryption{

    public String name;
    public String method;
    public int size;
    public int iv;
    Cipher cipher;
    SecretKey secretKey;
    IvParameterSpec ivSpec;

}
