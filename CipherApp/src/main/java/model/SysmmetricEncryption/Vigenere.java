package model.SysmmetricEncryption;

import constant.Constants;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Vigenere extends AbsSymmetricEncryption{
    char[] alphaBet;
    String key;
    int length;
    public Vigenere(){
        this.name = Constants.Cipher.VIGENERE;
        this.size = 100;
        instance(Constants.Method.VIGENERE_ENG);
    }
    @Override
    public void instance(String method) {
        if (method.equals(Constants.Method.VIGENERE_VIE)) {
            alphaBet = Constants.Alphabet.VIE_ALPHABET;
        } else {
            alphaBet = Constants.Alphabet.ENG_ALPHABET;
        }
        this.length = alphaBet.length;
    }

    private static int indexOf(char[] array, char target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public String encrypt(String plainText) {
        if(this.key==null)return null;
        StringBuilder ciphertext = new StringBuilder();
        char[] charText = plainText.toCharArray();
        char[] exceptionText = new char[charText.length];

        StringBuilder phraseText = new StringBuilder();
        for(int index=0 ; index<charText.length; index++){
            if(checkAlpha(charText[index])){
                phraseText.append(charText[index]);
            }
            else exceptionText[index] = charText[index];
        }


        for (int i = 0; i < phraseText.length(); i++) {
            char plainChar = phraseText.charAt(i);
            char keyChar = key.charAt(i % key.length());

            if (Character.isLetter(plainChar)) {
                int plainIndex = indexOf(alphaBet, Character.toUpperCase(plainChar));
                int keyIndex = indexOf(alphaBet, Character.toUpperCase(keyChar));

                if (plainIndex >= 0) {
                    char encryptedChar = alphaBet[(plainIndex + keyIndex) % length];
                    if (Character.isLowerCase(plainChar)) {
                        encryptedChar = Character.toLowerCase(encryptedChar);
                    }
                    ciphertext.append(encryptedChar);
                } else {
                    // Preserve non-alphabetic characters
                    ciphertext.append(plainChar);
                }
            } else {
                // Preserve non-alphabetic characters
                ciphertext.append(plainChar);
            }
        }
        //exception
        for(int i=0; i< exceptionText.length; i++){
            if(exceptionText[i]!=0){
                ciphertext.insert(i, exceptionText[i]);
            }
        }
        return ciphertext.toString();
    }

    @Override
    public String decrypt(String cipherText) {
        if(this.key==null) return null;
        StringBuilder plaintext = new StringBuilder();

        char[] charText = cipherText.toCharArray();
        char[] exceptionText = new char[charText.length];

        StringBuilder phraseText = new StringBuilder();
        for(int index=0 ; index<charText.length; index++){
            if(checkAlpha(charText[index])){
                phraseText.append(charText[index]);
            }
            else exceptionText[index] = charText[index];
        }

        for (int i = 0; i < phraseText.length(); i++) {
            char cipherChar = phraseText.charAt(i);
            char keyChar = key.charAt(i % key.length());

            if (Character.isLetter(cipherChar)) {
                int cipherIndex = indexOf(alphaBet, Character.toUpperCase(cipherChar));
                int keyIndex = indexOf(alphaBet, Character.toUpperCase(keyChar));

                if (cipherIndex >= 0) {
                    char decryptedChar = alphaBet[(cipherIndex - keyIndex + length) % length];
                    if (Character.isLowerCase(cipherChar)) {
                        decryptedChar = Character.toLowerCase(decryptedChar);
                    }
                    plaintext.append(decryptedChar);
                } else {
                    // Preserve non-alphabetic characters
                    plaintext.append(cipherChar);
                }
            } else {
                // Preserve non-alphabetic characters
                plaintext.append(cipherChar);
            }
        }

        //exception
        for(int i=0; i< exceptionText.length; i++){
            if(exceptionText[i]!=0){
                plaintext.insert(i, exceptionText[i]);
            }
        }
        return plaintext.toString();
    }

    @Override
    public String createKey() {
        return generateKey();
    }
    private String generateKey(){
        Random random = new Random();
        int n = random.nextInt(length - 2) + 2;

        ArrayList<Character> characterList = new ArrayList<>();
        for (char c : alphaBet) {
            characterList.add(c);
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(characterList.size());
            char randomChar = characterList.get(randomIndex);
            result.append(randomChar);
            characterList.remove(randomIndex);
        }

        return result.toString();
    }
    @Override
    public void convertKey(String key) {
        if(key.length()<2){
            JOptionPane.showMessageDialog(null, "The value must be at least 2 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String keyText = key.toUpperCase();
        if(!isValid(keyText)){
            JOptionPane.showMessageDialog(null, "Invalid Key!!", "Error", JOptionPane.ERROR_MESSAGE);
        }else {
            this.key = keyText;
        }
    }
    private boolean checkAlpha(char c){
        for (char character : alphaBet){
            if(Character.toUpperCase(c) == Character.toUpperCase(character)) return true;
        }
        return false;
    }
    private boolean isValid(String input){
        if (input == null || input.isEmpty()) {
            return false;
        }

        for(char character : input.toCharArray()){
            if(!checkAlpha(character)) return false;
        }
        return true;
    }

    @Override
    public String createIv() {
        return null;
    }

    @Override
    public void convertIv(String ivSpec) {
    }
}
