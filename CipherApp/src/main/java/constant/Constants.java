package constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public interface Cipher{
        String MD4 = "MD4";
        String MD5 = "MD5";
        String SHA_256 = "SHA-256";
        String SHA_1 = "SHA-1";
        String SHA_512 = "SHA-512";
        String SHA3_224 = "SHA3-224";
        String CRC_32 = "CRC-32";
        String SHAKE_256 = "SHAKE256";
        String RIPEMD_256 = "RIPEMD256";
        String DES = "DES";
        String AES = "AES";
        String TRIPLE_DES = "TRIPLE-DES";

        String BLOWFISH = "BLOWFISH";
        String RC4 = "RC4";
        String TWOFISH = "TWOFISH";
        String RSA = "RSA";
    }
    public interface Description{
        String PLAINTEXT = "PlainText";
        String CIPHERTEXT = "CipherText";
        String ENCRYPT = "Encrypt";
        String DECRYPT = "Decrypt";
        String KEY = "Key";
        String SIZE = "Size";
        String IV = "InitializationVector";
        String PUBLIC_KEY = "Public Key";
        String PRIVATE_KEY = "Private Key";
        String ENCRYPT_OUTPUT = "Encrypted Output";
        String DECRYPT_OUTPUT = "Decrypted Output";
        String INPUT = "Input";
        String OUTPUT = "Output";
        String E_SIGNATURE = "E_Signature";
        String VERIFY = "Verify";
        String OPEN_FILE = "Open File";
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

        //AES
        String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
        String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
        String AES_ECB_ISO10126PADDING = "AES/ECB/ISO10126Padding";
        String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
        String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
        String AES_CBC_ISO10126PADDING = "AES/CBC/ISO10126Padding";

        //RSA
        String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";
        String RSA_ECB_OAE = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        String RSA = "RSA";
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

        String[] METHODS_AES = {
                Method.AES_ECB_NOPADDING,
                Method.AES_ECB_PKCS5PADDING,
                Method.AES_ECB_ISO10126PADDING,
                Method.AES_CBC_NOPADDING,
                Method.AES_CBC_PKCS5PADDING,
                Method.AES_CBC_ISO10126PADDING,
        };

        String[] METHODS_RSA = {
                Method.RSA,
                Method.RSA_ECB_PKCS1PADDING,
                Method.RSA_ECB_OAE
        };
    }
    public interface Mode{
        String ECB = "ECB";
    }
    public interface Padding{
        String NOPADDING = "NoPadding";
    }
    public interface Size{
        String BITS256 = "256bits";
        String BITS192 = "192bits";
        String BITS128 = "128bits";
        String BIT515 = "515 bit";
        String BIT1024 = "1024 bit";
        String BIT2048 = "2048 bit";
        String BIT3072 = "3072 bit";
        String BIT4096 = "4096 bit";
    }
    public interface List_Size{
        String[] SIZE_AES = {Size.BITS128, Size.BITS192, Size.BITS256};
        String[] SIZE_RSA = {Size.BIT515, Size.BIT1024, Size.BIT2048, Size.BIT3072, Size.BIT4096};
    }
}
