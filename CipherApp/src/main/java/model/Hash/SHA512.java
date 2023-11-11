package model.Hash;

import constant.Constants;

public class SHA512 extends AbsHash{
    public SHA512(){
        this.name = Constants.Cipher.SHA_512;
        instance(this.name);
    }
}
