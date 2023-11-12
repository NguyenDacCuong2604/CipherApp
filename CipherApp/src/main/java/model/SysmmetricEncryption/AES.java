package model.SysmmetricEncryption;

import constant.Constants;

public class AES extends AbsSymmetricEncryption {
    public AES(){
        this.size = 16;
        this.iv = 16;
        this.sizeBlock = 16;
        this.name = Constants.Cipher.AES;
    }
}
