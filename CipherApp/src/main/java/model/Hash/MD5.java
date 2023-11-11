package model.Hash;

import constant.Constants;

public class MD5 extends AbsHash{
    public MD5(){
        this.name = Constants.Cipher.MD5;
        instance(this.name);
    }
}
