package model.SysmmetricEncryption;

import constant.Constants;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.util.Random;

public class Hill extends AbsSymmetricEncryption {
    char[] alphaBet;
    int[][] keyMatrix;
    int length;
    int sizeMatrix;

    public Hill() {
        this.name = Constants.Cipher.HILL;
        this.size = 24;
    }

    @Override
    public void instance(String method) {
        if (method.contains(Constants.Matrix.MATRIX_3x3)) {
            this.sizeMatrix = 3;
        } else {
            this.sizeMatrix = 2;
        }
        //create matrix
        keyMatrix = new int[this.sizeMatrix][this.sizeMatrix];

        if (method.contains(Constants.Language.VIE)) {
            alphaBet = Constants.Alphabet.VIE_ALPHABET;
        } else {
            alphaBet = Constants.Alphabet.ENG_ALPHABET;
        }
        this.length = alphaBet.length;
    }


    @Override
    public String encrypt(String plainText) {
        if(keyMatrix==null){
            return null;
        }
        StringBuilder cipherText = new StringBuilder();
        char[] charText = plainText.toCharArray();
        char[] exceptionText = new char[charText.length];
        char[] upperCase = new char[charText.length];

        StringBuilder phraseText = new StringBuilder();
        for(int index=0 ; index<charText.length; index++){
            if(isContain(charText[index], alphaBet)){
                if(Character.isLowerCase(charText[index])){
                    upperCase[index] = 1;
                }
                phraseText.append(Character.toUpperCase(charText[index]));
            }
            else exceptionText[index] = charText[index];
        }

        while (phraseText.length() % sizeMatrix != 0) {
            phraseText.append(alphaBet[alphaBet.length - 1]);
        }
        int[] input = new int[sizeMatrix];

        for (int i = 0; i < phraseText.length() / sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                input[j] = indexText(phraseText.charAt(i * sizeMatrix + j), alphaBet);
            }
            for (int row = 0; row < sizeMatrix; row++) {
                int value = 0;
                for (int col = 0; col < sizeMatrix; col++) {
                    value += keyMatrix[row][col] * input[col];
                }
                while (value < 0) {
                    value += length;
                }
                cipherText.append(alphaBet[value % length]);
            }
        }
        //exception
        for(int i=0; i< exceptionText.length; i++){
            if(exceptionText[i]!=0){
                cipherText.insert(i, exceptionText[i]);
            }
        }
        //uppercase
        for(int i=0; i< upperCase.length; i++){
            if(upperCase[i]!=0){
                cipherText.setCharAt(i, Character.toLowerCase(cipherText.charAt(i)));
            }
        }
        return cipherText.toString();
    }

    private int indexText(char character, char[] alphaBet) {
        for (int i = 0; i < alphaBet.length; i++) {
            if (character == alphaBet[i]) return i;
        }
        return -1;
    }

    private boolean isContain(char character, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (Character.toUpperCase(character) == alphabet[i]) return true;
        }
        return false;
    }

    @Override
    public String decrypt(String cipherText) {
        if(keyMatrix==null){
            return null;
        }
        StringBuilder decryptText = new StringBuilder();
        char[] charText = cipherText.toCharArray();
        char[] exceptionText = new char[charText.length];
        char[] upperCase = new char[charText.length];

        StringBuilder phraseText = new StringBuilder();
        for(int index=0 ; index<charText.length; index++){
            if(isContain(charText[index], alphaBet)){
                if(Character.isLowerCase(charText[index])){
                    upperCase[index] = 1;
                }
                phraseText.append(Character.toUpperCase(charText[index]));
            }
            else exceptionText[index] = charText[index];
        }

        while (phraseText.length() % sizeMatrix != 0) {
            phraseText.append(alphaBet[alphaBet.length - 1]);
        }
        int[][] inverseMatrix = inverse(keyMatrix);
        if (inverseMatrix == null) {
            JOptionPane.showMessageDialog(null, "Does not exist in the inverted matrix", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int[] input = new int[sizeMatrix];
        for (int i = 0; i < phraseText.length() / sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                input[j] = indexText(phraseText.charAt(i * sizeMatrix + j), alphaBet);
            }
            for (int row = 0; row < sizeMatrix; row++) {
                int value = 0;
                for (int col = 0; col < sizeMatrix; col++) {
                    value += inverseMatrix[row][col] * input[col];
                }
                while (value < 0) {
                    value += length;
                }
                decryptText.append(alphaBet[value % length]);
            }
        }
        //exception
        for(int i=0; i< exceptionText.length; i++){
            if(exceptionText[i]!=0){
                decryptText.insert(i, exceptionText[i]);
            }
        }
        //uppercase
        for(int i=0; i< upperCase.length; i++){
            if(upperCase[i]!=0){
                decryptText.setCharAt(i, Character.toLowerCase(decryptText.charAt(i)));
            }
        }
        return decryptText.toString();
    }

    private int[][] inverse(int[][] intMatrix) {
        int n = intMatrix.length;
        double[][] doubleMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                doubleMatrix[i][j] = (double) intMatrix[i][j];
            }
        }
        RealMatrix matrix = MatrixUtils.createRealMatrix(doubleMatrix);
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        RealMatrix inverseMatrix = svd.getSolver().getInverse();
        if(inverseMatrix==null) return null;
        LUDecomposition luDecomposition = new LUDecomposition(matrix);
        double determinant = luDecomposition.getDeterminant();
        int intDeterminant = (int)(Math.round(determinant)) % length;
        while (intDeterminant<0){
            intDeterminant+=length;
        }
        int modDeterminant = intDeterminant % length;
        int k=1;
        if(modDeterminant==0) modDeterminant+=length;
        while(((modDeterminant*k)%length)!=1){
            k++;
        }
        int[][] inverseIntMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double entry = inverseMatrix.getEntry(i, j)*determinant;
                while (entry <0){
                    entry+=length;
                }
                int value = (int) Math.round(entry%length);
                inverseIntMatrix[i][j] = (value*k)%length;
            }
        }
        return inverseIntMatrix;
    }

    @Override
    public String createKey() {
        generateRandomKeyMatrix();
        return formatMatrixToString(keyMatrix);
    }

    private int[][] generateRandomKeyMatrix() {
        Random random = new Random();
        do {
            for (int i = 0; i < sizeMatrix; i++) {
                for (int j = 0; j < sizeMatrix; j++) {
                    keyMatrix[i][j] = random.nextInt(length);
                }
            }
        } while (!isInvertible(keyMatrix));
        return keyMatrix;
    }

    private String formatMatrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    sb.append(" ");
                }
            }
            if (i < matrix.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    @Override
    public void convertKey(String key) {
        convertKeyMatrix(key);
    }

    public int[][] convertKeyMatrix(String key) {

        String[] keyParts = key.split(" ");
        //a = b x b
        if (keyParts.length != sizeMatrix * sizeMatrix) {
            JOptionPane.showMessageDialog(null, "Invalid key string. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
            return keyMatrix = null;
        }
        try {
            //convert int
            for (int i = 0; i < sizeMatrix; i++) {
                for (int j = 0; j < sizeMatrix; j++) {
                    keyMatrix[i][j] = Integer.parseInt(keyParts[i * sizeMatrix + j]);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error converting string to key matrix.", "Error", JOptionPane.ERROR_MESSAGE);
            return keyMatrix = null;
        }
        if (isInvertible(keyMatrix)) {
            return keyMatrix;
        } else {
            JOptionPane.showMessageDialog(null, "The key matrix cannot be inversed. Please check again.", "Error", JOptionPane.ERROR_MESSAGE);
            return keyMatrix = null;
        }
    }

    //Kiem tra nghich dao
    private boolean isInvertible(int[][] intMatrix) {
        double[][] doubleMatrix = new double[intMatrix.length][intMatrix[0].length];
        for (int i = 0; i < intMatrix.length; i++) {
            for (int j = 0; j < intMatrix[0].length; j++) {
                doubleMatrix[i][j] = intMatrix[i][j];
            }
        }
        RealMatrix matrixReal = MatrixUtils.createRealMatrix(doubleMatrix);
        // Kiểm tra tính nghịch đảo của ma trận
        SingularValueDecomposition svd = new SingularValueDecomposition(matrixReal);
        RealMatrix inverseMatrix = svd.getSolver().getInverse();
        return inverseMatrix!=null;
    }

    @Override
    public String createIv() {
        return null;
    }

    @Override
    public void convertIv(String ivSpec) {
    }
}
