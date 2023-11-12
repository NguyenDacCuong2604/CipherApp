package model.SysmmetricEncryption;

import constant.Constants;

public class TripleDES extends AbsSymmetricEncryption {
    public TripleDES(){
        this.size = 24;
        this.iv = 8;
        this.sizeBlock = 8;
        this.name = Constants.Cipher.TRIPLE_DES;
    }

}
