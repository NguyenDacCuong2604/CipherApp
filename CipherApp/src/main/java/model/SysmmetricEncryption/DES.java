package model.SysmmetricEncryption;

import constant.Constants;


public class DES extends AbsSymmetricEncryption {
    public DES(){
        this.size = 8;
        this.iv = 8;
        this.sizeBlock = 8;
        this.name = Constants.Cipher.DES;
    }
}
