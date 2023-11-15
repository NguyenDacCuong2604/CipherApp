package model.Hash;

import constant.Constants;

public class RIPEMD_256 extends AbsHash{
    public RIPEMD_256(){
        this.name = Constants.Cipher.RIPEMD_256;
        instance(this.name);
    }
}
