package model.SysmmetricEncryption;

import constant.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public abstract class AbsSymmetricEncryption implements ISymmetricEncryption {

    public String name;
    public String method;
    public int size;
    public int iv;
    public int sizeBlock;
    Cipher cipher;
    SecretKey secretKey;
    IvParameterSpec ivSpec;

    @Override
    public void instance(String method) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            this.method = method;
            if (method.contains(Constants.Padding.ZEROPADDING)) {
                String type = method;
                this.cipher = Cipher.getInstance(type.replace(Constants.Padding.ZEROPADDING, Constants.Padding.NOPADDING));
            } else this.cipher = Cipher.getInstance(method);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public String encrypt(String plainText) {
        try {
            if (secretKey == null) return null;
            if (method.contains(Constants.Mode.ECB)) {
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            } else cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);
            byte[] messageBytes = plainText.getBytes("UTF-8");
            if (method.contains(Constants.Padding.ZEROPADDING)) {
                int blockSize = sizeBlock;
                int padding = blockSize - (messageBytes.length % blockSize);
                if (padding != blockSize) {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];
                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
                    messageBytes = paddedMessage;
                } else {
                    int newLength = messageBytes.length + padding;
                    byte[] paddedMessage = new byte[newLength];
                    System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
                    messageBytes = paddedMessage;
                }
            }
            byte[] byteEncrypt = cipher.doFinal(messageBytes);
            return Base64.getEncoder().encodeToString(byteEncrypt);
        } catch (InvalidAlgorithmParameterException | UnsupportedEncodingException | IllegalBlockSizeException |
                 BadPaddingException | InvalidKeyException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String decrypt(String cipherText) {
        try {
            if (secretKey == null) return null;
            byte[] byteEncrypt = Base64.getDecoder().decode(cipherText);
            if (method.contains(Constants.Mode.ECB)) {
                cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            } else cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivSpec);
            byte[] byteText = cipher.doFinal(byteEncrypt);
            int padding = 0;
            for (int i = byteText.length - 1; i >= 0; i--) {
                if (byteText[i] == 0) {
                    padding++;
                } else {
                    break;
                }
            }
            int originalLength = byteText.length - padding;
            byte[] originalBytes = new byte[originalLength];
            System.arraycopy(byteText, 0, originalBytes, 0, originalLength);
            return new String(originalBytes, "UTF-8");
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private String fileName(String originalPath) {
        // Chuyển đổi đường dẫn thành đối tượng Path
        Path path = Paths.get(originalPath);

        // Lấy phần đường dẫn và tên file từ đối tượng Path
        Path directory = path.getParent();
        String filename = path.getFileName().toString();

        // Kiểm tra xem file có phần mở rộng hay không
        int lastDotIndex = filename.lastIndexOf(".");
        String name, extension;

        if (lastDotIndex != -1) {
            // Nếu có phần mở rộng, tách tên file và đuôi mở rộng
            name = filename.substring(0, lastDotIndex);
            extension = filename.substring(lastDotIndex);
        } else {
            // Nếu không có phần mở rộng, sử dụng toàn bộ tên file và không có phần mở rộng
            name = filename;
            extension = "";
        }

        // Thêm "_encrypt" vào tên file
        String encryptedFilename = name + "_encrypt" + extension;

        // Kết hợp đường dẫn và tên file mới để tạo ra đường dẫn mới
        Path encryptedPath = directory.resolve(encryptedFilename);

        return encryptedPath.toString();
    }

    @Override
    public String encryptFile(File file) {
        if (secretKey == null) return null;
        if (!file.exists()) return null;
        try {
            if (method.contains(Constants.Mode.ECB)) {
                cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            } else cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            String desFile = fileName(file.getAbsolutePath());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
            byte[] arr = new byte[2304];
            int byteRead;
            while ((byteRead = bis.read(arr)) != -1) {
                byte[] byteEncrypt = this.cipher.update(arr, 0, byteRead);
                bos.write(byteEncrypt);
            }
            byte[] output = this.cipher.doFinal();
            if (output != null) bos.write(output);
            bis.close();
            bos.flush();
            bos.close();
            return "Encrypted From " + file.getName() + " to " + desFile;
        } catch (IllegalBlockSizeException | IOException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String createKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(name);
            SecretKey key = keyGenerator.generateKey();
            byte[] keyBytes = key.getEncoded();
            String keyString = bytesToHex(keyBytes).substring(0, this.size);
            return keyString;
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public void convertKey(String key) {
        byte[] keyData = key.getBytes(StandardCharsets.UTF_8);
        if (keyData.length <= this.size) {
            byte[] paddedKeyData = new byte[this.size];
            System.arraycopy(keyData, 0, paddedKeyData, 0, keyData.length);
            keyData = paddedKeyData;
        }
        SecretKey secretKey = new SecretKeySpec(keyData, name);
        this.secretKey = secretKey;
    }

    @Override
    public String createIv() {
        byte[] ivBytes = new byte[iv];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        return bytesToHex(ivBytes).substring(0, iv);
    }

    @Override
    public void convertIv(String ivSpec) {
        byte[] ivData = ivSpec.getBytes(StandardCharsets.UTF_8);
        if (ivData.length == 0) {
            ivData = ivGenerate();
        } else if (ivData.length != iv) {
            byte[] paddedIvData = new byte[iv];
            System.arraycopy(ivData, 0, paddedIvData, 0, ivData.length);
            ivData = paddedIvData;
        }
        this.ivSpec = new IvParameterSpec(ivData);
    }


    //hex
    String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    //generate char 0 in iv
    byte[] ivGenerate() {
        byte[] data = new byte[iv];
        for (int i = 0; i < data.length; i++) {
            //char 0
            data[i] = 48;
        }
        return data;
    }
}
