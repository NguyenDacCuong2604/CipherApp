package model.SysmmetricEncryption;

import constant.Constants;

public class Twofish extends AbsSymmetricEncryption{
    public Twofish(){
        super();
        this.size = 16;
        this.iv = 16;
        this.sizeBlock = 16;
        this.name = Constants.Cipher.TWOFISH;
    }

}
