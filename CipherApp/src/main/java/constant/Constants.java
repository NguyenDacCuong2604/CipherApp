package constant;

public class Constants {

    public interface Cipher{
        String SHA_256 = "SHA-256";
        String DES = "DES";
        String AES = "AES";
        String TRIPLE_DES = "TRIPLE-DES";

        String BLOWFISH = "BLOWFISH";
        String RC4 = "RC4";
        String TWOFISH = "TWOFISH";
    }
    public interface Description{
        String PLAINTEXT = "PlainText";
        String CIPHERTEXT = "CipherText";
        String ENCRYPT = "Encrypt";
        String DECRYPT = "Decrypt";
        String KEY = "Key";
        String IV = "InitializationVector";

    }
    public interface Mode{
        String ECB = "ECB";
        String CBC = "CBC";
        String CFB = "CFB";
        String OFB = "OFB";
        String CTR = "CTR";
    }

    public interface List_Mode{
        String[] DES_MODES = {Mode.ECB, Mode.CBC, Mode.CFB, Mode.OFB, Mode.CTR};
    }

    public interface Padding{
        String NOPADDING = "NoPadding";
        String PKCS5PADDING = "PKCS5Padding";
        String ZEROPADDING = "ZeroPadding";
    }
    public interface List_Padding{
        String[] PADDINGS = {Padding.NOPADDING, Padding.PKCS5PADDING, Padding.ZEROPADDING};
    }

}
