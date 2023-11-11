package model.Hash;

import constant.Constants;

public class SHA3_224 extends AbsHash{
    public SHA3_224(){
        this.name = Constants.Cipher.SHA3_224;
        instance(this.name);
    }
}
