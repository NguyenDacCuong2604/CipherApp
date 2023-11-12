package model.SysmmetricEncryption;

import constant.Constants;

public class Blowfish extends AbsSymmetricEncryption {
    public Blowfish(){
        this.size = 8;
        this.iv = 8;
        this.sizeBlock = 8;
        this.name = Constants.Cipher.BLOWFISH;
    }

}
