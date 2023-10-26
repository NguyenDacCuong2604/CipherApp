package constant;

import java.util.HashMap;
import java.util.Map;

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
    public interface Method{
        //DES
        String DES_ECB_NOPADDING = "DES/ECB/NoPadding";
        String DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
        String DES_ECB_ISO10126PADDING = "DES/ECB/ISO10126Padding";
        String DES_CBC_NOPADDING = "DES/CBC/NoPadding";
        String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
        String DES_CBC_ISO10126PADDING = "DES/CBC/ISO10126Padding";
        String DES_CTR_NOPADDING = "DES/CTR/NoPadding";
        String DES_CTS_NOPADDING = "DES/CTS/NoPadding";
        String DES_CFB_NOPADDING = "DES/EFB/NoPadding";
        String DES_CFB_PKCS5PADDING = "DES/CFB/PKCS5Padding";
        String DES_CFB_ISO10126PADDING = "DES/CFB/ISO10126Padding";
        String DES_OFB_NOPADDING = "DES/OFB/NoPadding";
        String DES_OFB_PKCS5PADDING = "DES/OFB/PKCS5Padding";
        String DES_OFB_ISO10126PADDING = "DES/OFB/ISO10126Padding";

        //Triple DES
        String TRIPDES_ECB_NOPADDING = "DESede/ECB/NoPadding";
        String TRIPDES_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
        String TRIPDES_ECB_ISO10126PADDING = "DESede/ECB/ISO10126Padding";
        String TRIPDES_CBC_NOPADDING = "DESede/CBC/NoPadding";
        String TRIPDES_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";
        String TRIPDES_CBC_ISO10126PADDING = "DESede/CBC/ISO10126Padding";
    }
    public interface List_Method{
        String[] METHODS_DES = {
                Method.DES_ECB_NOPADDING,
                Method.DES_ECB_PKCS5PADDING,
                Method.DES_ECB_ISO10126PADDING,
                Method.DES_CBC_NOPADDING,
                Method.DES_CBC_PKCS5PADDING,
                Method.DES_CBC_ISO10126PADDING,
                Method.DES_CTR_NOPADDING,
                Method.DES_CTS_NOPADDING,
                Method.DES_CFB_NOPADDING,
                Method.DES_CFB_PKCS5PADDING,
                Method.DES_CFB_ISO10126PADDING,
                Method.DES_OFB_NOPADDING,
                Method.DES_OFB_PKCS5PADDING,
                Method.DES_OFB_ISO10126PADDING
        };

        String[] METHODS_TRIPLEDES = {
                Method.TRIPDES_ECB_NOPADDING,
                Method.TRIPDES_ECB_PKCS5PADDING,
                Method.TRIPDES_ECB_ISO10126PADDING,
                Method.TRIPDES_CBC_NOPADDING,
                Method.TRIPDES_CBC_PKCS5PADDING,
                Method.TRIPDES_CBC_ISO10126PADDING,
        };
    }
    public interface Mode{
        String ECB = "ECB";
    }
    public interface Padding{
        String NOPADDING = "NoPadding";
    }
}
