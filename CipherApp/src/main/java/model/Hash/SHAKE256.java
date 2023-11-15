package model.Hash;

import constant.Constants;
public class SHAKE256 extends AbsHash{
    public SHAKE256(){
        this.name = Constants.Cipher.SHAKE_256;
        instance(this.name);
    }

}
