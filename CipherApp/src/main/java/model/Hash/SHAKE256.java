package model.Hash;

import constant.Constants;
import org.bouncycastle.crypto.digests.SHAKEDigest;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SHAKE256 extends AbsHash{
    SHAKEDigest digest;
    public SHAKE256(){
        this.name = Constants.Cipher.SHAKE_256;
        instance(this.name);
    }
    @Override
    public void instance(String method) {
        digest = new SHAKEDigest(256);
    }

    @Override
    public String hashFile(String pathSource) {
        try {
            File file = new File(pathSource);
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            fileInputStream.close();

            byte[] hashBytes = new byte[512];
            digest.doFinal(hashBytes, 0);
            digest.reset();

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

        byte[] hashBytes = new byte[512];
        digest.update(bytesText, 0, bytesText.length);
        digest.doFinal(hashBytes, 0);
        digest.reset();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
