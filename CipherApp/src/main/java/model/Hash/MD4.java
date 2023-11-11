package model.Hash;

import constant.Constants;

public class MD4 extends AbsHash{
    public MD4(){
        this.name = Constants.Cipher.MD4;
        instance(this.name);
    }
}
