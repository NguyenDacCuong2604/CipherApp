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
    String lang;
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
            this.lang = Constants.Language.VIE;
            alphaBet = Constants.Alphabet.VIE_ALPHABET;
        } else {
            this.lang = Constants.Language.ENG;
            alphaBet = Constants.Alphabet.ENG_ALPHABET;
        }
        this.length = alphaBet.length;
    }


    @Override
    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();
        String pharseText = pharse(plainText);
        while (pharseText.length() % sizeMatrix != 0) {
            pharseText = pharseText.concat(alphaBet[alphaBet.length - 1] + "");
        }
        int[] input = new int[sizeMatrix];

        for (int i = 0; i < pharseText.length() / sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                input[j] = indexText(pharseText.charAt(i * sizeMatrix + j), alphaBet);
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
        return cipherText.toString();
    }

    private int indexText(char character, char[] alphaBet) {
        for (int i = 0; i < alphaBet.length; i++) {
            if (character == alphaBet[i]) return i;
        }
        return -1;
    }

    private String pharse(String plainText) {
        plainText = plainText.toLowerCase();
        StringBuilder text = new StringBuilder();
        for (char character : plainText.toCharArray()) {
            if (isContain(character, alphaBet)) text.append(character);
        }
        return text.toString();
    }

    private boolean isContain(char character, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (character == alphabet[i]) return true;
        }
        return false;
    }
    private void printMatrix(int[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    @Override
    public String decrypt(String cipherText) {
        StringBuilder decryptText = new StringBuilder();
        String pharseText = pharse(cipherText);
        while (pharseText.length() % sizeMatrix != 0) {
            pharseText = pharseText.concat(alphaBet[alphaBet.length - 1] + "");
        }
        int[][] inverseMatrix = inverse(keyMatrix);
        printMatrix(inverseMatrix);
        if (inverseMatrix == null) {
            JOptionPane.showMessageDialog(null, "Does not exist in the inverted matrix", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int[] input = new int[sizeMatrix];
        for (int i = 0; i < pharseText.length() / sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                input[j] = indexText(pharseText.charAt(i * sizeMatrix + j), alphaBet);
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
        return decryptText.toString();
    }

    private int[][] inverse(int[][] intMatrix) {
        int n = intMatrix.length;

        // Chuyển ma trận int[][] thành RealMatrix với giá trị nguyên
        double[][] doubleMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                doubleMatrix[i][j] = (double) intMatrix[i][j];
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(doubleMatrix);

        // Kiểm tra tính nghịch đảo của ma trận
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        RealMatrix inverseMatrix = svd.getSolver().getInverse();
        // Create an LU decomposition of the inverseMatrix
        LUDecomposition luDecomposition = new LUDecomposition(matrix);

        // Get the determinant of the inverseMatrix
        double determinant = luDecomposition.getDeterminant();
        int intDeterminant = (int) determinant;
        int k=1;
        while((intDeterminant*k)%length!=1){
            k++;
        }

        int[][] inverseIntMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double entry = inverseMatrix.getEntry(i, j)*determinant;
                while (entry <0){
                    entry+=length;
                }
                int value = (int) (entry%length);
                inverseIntMatrix[i][j] = (value * k)% length;
                System.out.println(inverseIntMatrix[i][j]);
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
    public SecretKey convertKey(String key) {
        convertKeyMatrix(key);
        return null;
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
    public IvParameterSpec convertIv(String ivSpec) {
        return null;
    }
}
