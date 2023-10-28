package model.ElectronicSignature;

import constant.Constants;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.util.Arrays;

public class ElectronicSignature {
    public String name;
    byte[] data;
    public ElectronicSignature(){
        this.name = Constants.Description.E_SIGNATURE;
        instance();
    }
    public boolean loadFileData(String filePath){
        try {
            FileInputStream fis = new FileInputStream(filePath);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            this.data = data;
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    private void instance(){
        Security.addProvider(new BouncyCastleProvider());
    }
    public boolean verifySignature(String filePath){
        try {
//            System.out.println(Arrays.toString(this.data));
            CMSSignedData signedData = new CMSSignedData(new FileInputStream(filePath));
            // Kiểm tra chữ ký số
            // Kiểm tra xem có chữ ký số hay không
//            if (!signedData.getSignerInfos().getSigners().isEmpty()) {
//                return true;
//            } else {
//                return false;
//            }
            return true;
        }
         catch (CMSException e) {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
