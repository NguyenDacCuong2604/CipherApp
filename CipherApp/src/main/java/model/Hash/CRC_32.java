package model.Hash;

import constant.Constants;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.zip.CRC32;

public class CRC_32 extends AbsHash{
    CRC32 crc32;
    public CRC_32(){
        this.name = Constants.Cipher.CRC_32;
        instance(this.name);
    }
    @Override
    public void instance(String method) {
        this.crc32 = new CRC32();
    }

    @Override
    public String hashFile(File file) {
        try {
            crc32.reset();
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, bytesRead);
            }
            fileInputStream.close();
            return String.format(Locale.US,"%08x", crc32.getValue());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String hashText(String plainText) {
        byte[] bytesText = plainText.getBytes();
        crc32.reset();
        crc32.update(bytesText);
        return String.format(Locale.US,"%08x", crc32.getValue());
    }
}
