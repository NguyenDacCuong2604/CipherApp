package model.ElectronicSignature;

import constant.Constants;


import javax.swing.*;


public class ElectronicSignature {
    public String name;
    byte[] data;
    public ElectronicSignature(){
        this.name = Constants.Description.E_SIGNATURE;
        instance();
    }
    public boolean loadFileData(String filePath){
        return false;
    }
    private void instance(){

    }
    public boolean verifySignature(String filePath){
       return false;
    }

}
