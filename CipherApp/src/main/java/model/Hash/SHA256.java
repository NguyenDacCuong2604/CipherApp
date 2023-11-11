package model.Hash;

import constant.Constants;

public class SHA256 extends AbsHash{
    public SHA256(){
        this.name = Constants.Cipher.SHA_256;
        instance(this.name);
    }
}
