package constant;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public interface Alphabet{
        char[] ENG_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        char[] VIE_ALPHABET = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệghiíìỉĩịklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvxyýỳỷỹỵ".toUpperCase().toCharArray();
    }
    public interface Image{
        String ICON = "assets/images/icon.png";
        String COPY = "assets/Images/copy_output.png";
        String CLOSE = "assets/Images/close.png";
        String CLOSE_BLACK = "assets/Images/close-black.png";
        String MINIMIZE_BLACK = "assets/Images/minimize-sign-black.png";
        String MINIMIZE = "assets/Images/minimize-sign.png";
    }
    public interface ColorUI{
        Color BUTTON = new Color(239, 239, 239);
        Color TEXT_WHITE = new Color(255, 255, 255);
        Color BUTTON_CLICK = new Color(35, 128, 251);
    }
    public interface Font_Text{
        Font BOLD_16 = new Font("Arial", Font.BOLD, 16);
        Font BOLD_14 = new Font("Arial", Font.BOLD, 14);
        Font BOLD_22 = new Font("Arial", Font.BOLD, 22);
        Font PLAIN_16 = new Font("Arial", Font.PLAIN, 16);
        Font PLAIN_20 = new Font("Arial", Font.PLAIN, 20);
    }
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
        String CAST_6 = "CAST6";
        String TWOFISH = "TWOFISH";
        String RSA = "RSA";
        String HILL = "Hill";
        String VIGENERE = "Vigenere";
    }
    public interface Matrix{
        String MATRIX_3x3 = "3x3";
        String MATRIX_2x2 = "2x2";
    }
    public interface Language{
        String VIE = "Vie";
        String ENG = "Eng";
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
        String E_SIGNATURE = "Verify";
        String VERIFY = "Verify";
        String OPEN_FILE = "Open File";
        String RESULT_TEXT = "Result goes here";
        String RESULT = "Result";
        String VERIFY_FILE ="Verify File";
        String ERROR = "Error";
        String CHOOSE_FILE_VERIFY = "Choose File is verify!!!";
        String ENTER_HASHCODE = "Enter to your hashcode";
        String VERIFIED = "Verified";
        String UNVERIFIED = "Unverified";
        String TYPE_HASH = "Type Hash: ";
        String PASTE = "Paste";
        String COPY = "Copy";
        String CLEAR = "Clear";
        String CLOSE_FILE = "Close File";
        String INPUT_FILE = "Input File";
        String EMPTY = "";
        String NO_FILE_CHOSEN = "No file chosen";
        String CHOOSE_FILE = "Choose file";
        String FILE_NOT_EXIST = "File is not exists";
        String HASH = "Hash";
        String ASYMMETRIC = "Asymmetric-key Algorithms";
        String SYMMETRIC = "Symmetric-key Algorithms";
        String AUTO_UPDATE = "Auto Update";
        String COPIED = "Copied: ";
        String COPY_ALL = "Copy All";
        String TEXT = "Text";
        String FILE = "File";
        String ENTER_TEXT = "Enter your text here!!!";
        String ENCRYPT_DECRYPT = "Encryption and Decryption ";
        String RSA_EN = "RSA encryption";
        String RSA_DE = "RSA decryption";
        String ENTER_DECRYPT = "Enter Encrypted Text to Decrypt";
        String ENTER_ENCRYPT = "Enter Plain Text to Encrypt";
        String ENTER_PUBLIC_PRIVATE = "Enter Public/Private key";
        String KEY_TYPE = "Key Type: ";
        String BLANK = "  ";
        String TYPE_CIPHER = "Select Cipher Type";
        String TYPE_MODE = "Select Mode";
        String GENERATE_KEY = "Generate Keys ";
        String ENCRYPTION = "Encryption";
        String DECRYPTION = "Decryption";
        String ENTER_SECRET = "Enter Secret Key";
        String ENTER_IV = "Enter IV (Optional)";
        String GENERATE_IV = "Generate IV";
        String ENTER_INIT_VECTOR = "Enter Initialization Vector";
        String EMPTY_KEY = "Empty Key!!! You can create a key by clicking on the createKey button";
        String CLOSE = "Close";
        String MINIMIZE = "Minimize";
        String CIPHER_APP = "  CipherApp";
    }
    public interface Method{
        //DES
        String DES_ECB_NOPADDING = "DES/ECB/NoPadding";
        String DES_ECB_ZEROPADDING = "DES/ECB/ZeroPadding";
        String DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
        String DES_ECB_ISO10126PADDING = "DES/ECB/ISO10126Padding";
        String DES_CBC_NOPADDING = "DES/CBC/NoPadding";
        String DES_CBC_ZEROPADDING = "DES/CBC/ZeroPadding";
        String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";
        String DES_CBC_ISO10126PADDING = "DES/CBC/ISO10126Padding";
        String DES_CTR_NOPADDING = "DES/CTR/NoPadding";
        String DES_CTR_ZEROPADDING = "DES/CTR/ZeroPadding";

        //Triple DES
        String TRIPDES_ECB_NOPADDING = "DESede/ECB/NoPadding";
        String TRIPDES_ECB_ZEROPADDING = "DESede/ECB/ZeroPadding";
        String TRIPDES_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
        String TRIPDES_ECB_ISO10126PADDING = "DESede/ECB/ISO10126Padding";
        String TRIPDES_CBC_NOPADDING = "DESede/CBC/NoPadding";
        String TRIPDES_CBC_ZEROPADDING = "DESede/CBC/ZeroPadding";
        String TRIPDES_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";
        String TRIPDES_CBC_ISO10126PADDING = "DESede/CBC/ISO10126Padding";

        //AES
        String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
        String AES_ECB_ZEROPADDING = "AES/ECB/ZeroPadding";
        String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
        String AES_ECB_ISO10126PADDING = "AES/ECB/ISO10126Padding";
        String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
        String AES_CBC_ZEROPADDING = "AES/CBC/ZeroPadding";
        String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
        String AES_CBC_ISO10126PADDING = "AES/CBC/ISO10126Padding";

        //Blowfish
        String BLOWFISH_ECB_NOPADDING = "Blowfish/ECB/NoPadding";
        String BLOWFISH_ECB_ZEROPADDING = "Blowfish/ECB/ZeroPadding";
        String BLOWFISH_ECB_PKCS5PADDING = "Blowfish/ECB/PKCS5Padding";
        String BLOWFISH_ECB_ISO10126PADDING = "Blowfish/ECB/ISO10126Padding";
        String BLOWFISH_CBC_NOPADDING = "Blowfish/CBC/NoPadding";
        String BLOWFISH_CBC_ZEROPADDING = "Blowfish/CBC/ZeroPadding";
        String BLOWFISH_CBC_PKCS5PADDING = "Blowfish/CBC/PKCS5Padding";
        String BLOWFISH_CBC_ISO10126PADDING = "Blowfish/CBC/ISO10126Padding";

        //Twofish
        String TWOFISH_ECB_NOPADDING = "Twofish/ECB/NoPadding";
        String TWOFISH_ECB_ZEROPADDING = "Twofish/ECB/ZeroPadding";
        String TWOFISH_ECB_PKCS5PADDING = "Twofish/ECB/PKCS5Padding";
        String TWOFISH_ECB_ISO10126PADDING = "Twofish/ECB/ISO10126Padding";
        String TWOFISH_CBC_NOPADDING = "Twofish/CBC/NoPadding";
        String TWOFISH_CBC_ZEROPADDING = "Twofish/CBC/ZeroPadding";
        String TWOFISH_CBC_PKCS5PADDING = "Twofish/CBC/PKCS5Padding";
        String TWOFISH_CBC_ISO10126PADDING = "Twofish/CBC/ISO10126Padding";

        //Cast6
        String CAST6_ECB_NOPADDING = "CAST6/ECB/NoPadding";
        String CAST6_ECB_ZEROPADDING = "CAST6/ECB/ZeroPadding";
        String CAST6_ECB_PKCS5PADDING = "CAST6/ECB/PKCS5Padding";
        String CAST6_ECB_ISO10126PADDING = "CAST6/ECB/ISO10126Padding";
        String CAST6_CBC_NOPADDING = "CAST6/CBC/NoPadding";
        String CAST6_CBC_ZEROPADDING = "CAST6/CBC/ZeroPadding";
        String CAST6_CBC_PKCS5PADDING = "CAST6/CBC/PKCS5Padding";
        String CAST6_CBC_ISO10126PADDING = "CAST6/CBC/ISO10126Padding";

        //Hill
        String HILL_ENG_3X3 = "Hill/Eng/3x3";
        String HILL_VIE_3x3 = "Hill/Vie/3x3";
        String HILL_ENG_2X2 = "Hill/Eng/2x2";
        String HILL_VIE_2X2 = "Hill/Vie/2x2";

        //Vigenere
        String VIGENERE_ENG = "Vigenere/Eng";
        String VIGENERE_VIE = "Vigenere/Vie";

        //RSA
        String RSA_ECB_PKCS1PADDING = "RSA/ECB/PKCS1Padding";
        String RSA_ECB_OAE = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
        String RSA = "RSA";
    }
    public interface Type{
        String[] TYPE_KEY = {Description.PUBLIC_KEY, Description.PRIVATE_KEY};
    }
    public interface List_Method{
        String[] METHODS_DES = {
                Method.DES_ECB_NOPADDING,
                Method.DES_ECB_ZEROPADDING,
                Method.DES_ECB_PKCS5PADDING,
                Method.DES_ECB_ISO10126PADDING,
                Method.DES_CBC_NOPADDING,
                Method.DES_CBC_ZEROPADDING,
                Method.DES_CBC_PKCS5PADDING,
                Method.DES_CBC_ISO10126PADDING,
                Method.DES_CTR_NOPADDING,
                Method.DES_CTR_ZEROPADDING
        };

        String[] METHODS_TRIPLEDES = {
                Method.TRIPDES_ECB_NOPADDING,
                Method.TRIPDES_ECB_ZEROPADDING,
                Method.TRIPDES_ECB_PKCS5PADDING,
                Method.TRIPDES_ECB_ISO10126PADDING,
                Method.TRIPDES_CBC_NOPADDING,
                Method.TRIPDES_CBC_ZEROPADDING,
                Method.TRIPDES_CBC_PKCS5PADDING,
                Method.TRIPDES_CBC_ISO10126PADDING
        };

        String[] METHODS_BLOWFISH = {
                Method.BLOWFISH_ECB_NOPADDING,
                Method.BLOWFISH_ECB_ZEROPADDING,
                Method.BLOWFISH_ECB_PKCS5PADDING,
                Method.BLOWFISH_ECB_ISO10126PADDING,
                Method.BLOWFISH_CBC_NOPADDING,
                Method.BLOWFISH_CBC_ZEROPADDING,
                Method.BLOWFISH_CBC_PKCS5PADDING,
                Method.BLOWFISH_CBC_ISO10126PADDING
        };

        String[] METHODS_TWOFISH = {
                Method.TWOFISH_ECB_NOPADDING,
                Method.TWOFISH_ECB_ZEROPADDING,
                Method.TWOFISH_ECB_PKCS5PADDING,
                Method.TWOFISH_ECB_ISO10126PADDING,
                Method.TWOFISH_CBC_NOPADDING,
                Method.TWOFISH_CBC_ZEROPADDING,
                Method.TWOFISH_CBC_PKCS5PADDING,
                Method.TWOFISH_CBC_ISO10126PADDING
        };

        String[] METHODS_CAST6 = {
                Method.CAST6_ECB_NOPADDING,
                Method.CAST6_ECB_ZEROPADDING,
                Method.CAST6_ECB_PKCS5PADDING,
                Method.CAST6_ECB_ISO10126PADDING,
                Method.CAST6_CBC_NOPADDING,
                Method.CAST6_CBC_ZEROPADDING,
                Method.CAST6_CBC_PKCS5PADDING,
                Method.CAST6_CBC_ISO10126PADDING
        };

        String[] METHODS_AES = {
                Method.AES_ECB_NOPADDING,
                Method.AES_ECB_ZEROPADDING,
                Method.AES_ECB_PKCS5PADDING,
                Method.AES_ECB_ISO10126PADDING,
                Method.AES_CBC_NOPADDING,
                Method.AES_CBC_ZEROPADDING,
                Method.AES_CBC_PKCS5PADDING,
                Method.AES_CBC_ISO10126PADDING
        };

        String[] METHODS_HILL = {
                Method.HILL_ENG_3X3,
                Method.HILL_VIE_3x3,
                Method.HILL_ENG_2X2,
                Method.HILL_VIE_2X2
        };

        String[] METHODS_VIGENERE = {
                Method.VIGENERE_ENG,
                Method.VIGENERE_VIE
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
        String ZEROPADDING = "ZeroPadding";
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
