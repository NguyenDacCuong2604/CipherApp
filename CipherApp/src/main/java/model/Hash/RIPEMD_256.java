package model.Hash;

import constant.Constants;
import org.bouncycastle.crypto.digests.RIPEMD256Digest;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

public class RIPEMD_256 extends AbsHash{
    RIPEMD256Digest digest;
    public RIPEMD_256(){
        this.name = Constants.Cipher.RIPEMD_256;
        instance(this.name);
    }
    @Override
    public void instance(String method) {
        digest = new RIPEMD256Digest();
    }

    @Override
    public String hashFile(File file) {
        try {
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            byte[] hashBytes = new byte[digest.getDigestSize()];
            digest.doFinal(hashBytes, 0);
            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String hashText(String plainText) {
        byte[] bytesText = plainText.getBytes();
        digest.update(bytesText, 0, bytesText.length);
        byte[] hashBytes = new byte[digest.getDigestSize()];
        digest.doFinal(hashBytes, 0);
        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
