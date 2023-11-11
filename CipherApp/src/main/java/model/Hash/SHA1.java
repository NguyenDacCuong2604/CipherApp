package model.Hash;

import constant.Constants;

public class SHA1 extends AbsHash{
    public SHA1(){
        this.name = Constants.Cipher.SHA_1;
        instance(this.name);
    }
}
