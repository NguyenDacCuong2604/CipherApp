package model;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public abstract class ASymmetricEncryption implements ISymmetricEncryption{
    protected SecretKey secretKey;
}
