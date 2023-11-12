package model.SysmmetricEncryption;

import constant.Constants;

public class Cast6 extends AbsSymmetricEncryption{
    public Cast6(){
        this.size = 32;
        this.iv = 16;
        this.sizeBlock = 16;
        this.name = Constants.Cipher.CAST_6;

    }
}
