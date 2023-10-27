package model.Hash;

import java.security.MessageDigest;

public abstract class AbsHash implements IHash{
    public String name;
    MessageDigest messageDigest;

}
