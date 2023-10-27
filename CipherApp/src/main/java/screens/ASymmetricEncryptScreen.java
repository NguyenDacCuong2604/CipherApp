package screens;

import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASymmetricEncryptScreen extends JFrame {
    AbsASymmetricEncryption aSymmetricEncryption;
    JPanel controlPanel;
    JLabel nameCipherLabel;
    JButton createKeyButton, encryptButton, decryptButton, pasteButtonEncrypt, pasteButtonDecrypt, clearButtonEncrypt, clearButtonDecrypt;
    String[] methods;
    JComboBox methodsComboBoxEncrypt, methodsComboBoxDecrypt;
    JTextArea plainTextAreaEncrypt, cipherTextAreaEncrypt, plainTextAreaDecrypt, cipherTextAreaDecrypt, publicKeyText, privateKeyText;

    public ASymmetricEncryptScreen(AbsASymmetricEncryption aSymmetricEncryption, String[] methods){
        this.methods = methods;
        this.aSymmetricEncryption = aSymmetricEncryption;
        this.setTitle(aSymmetricEncryption.name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Control
        renderControl(panel);

        //body
        JPanel body = new JPanel();
        body.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //Encrypt
        renderEncrypt(body);

        //line
        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(2, 600));
        linePanel.setBackground(Color.BLACK);
        body.add(linePanel);

        //Decrypt
        renderDecrypt(body);

        panel.add(body);

        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderDecrypt(JPanel body) {
        JPanel panelDecrypt = new JPanel();
        panelDecrypt.setLayout(new BoxLayout(panelDecrypt, BoxLayout.Y_AXIS));

        //CipherText
        cipherTextAreaDecrypt = new JTextArea();
        cipherTextAreaDecrypt.setPreferredSize(new Dimension(400,200));
        TitledBorder cipherTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.CIPHERTEXT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        cipherTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        cipherTextAreaDecrypt.setBorder(cipherTextTitledBorder);
        panelDecrypt.add(cipherTextAreaDecrypt);

        //PrivateKey
        privateKeyText = new JTextArea();
        privateKeyText.setPreferredSize(new Dimension(400, 100));
        TitledBorder privateKeyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PRIVATE_KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        privateKeyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        privateKeyText.setBorder(privateKeyTitledBorder);
        panelDecrypt.add(privateKeyText);

        //Type
        JPanel panelType = new JPanel(new FlowLayout());
        JLabel typeLabel = new JLabel("Select Cipher Type");
        panelType.add(typeLabel);
        methodsComboBoxDecrypt = new JComboBox(this.methods);
        panelType.add(methodsComboBoxDecrypt);
        panelDecrypt.add(panelType);

        //Button
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        decryptButton = new JButton("Decrypt");
        decryptButton.setBackground(Color.GREEN);
        decryptButton.setFont(new Font("Arial", Font.BOLD, 16));
        decryptButton.setPreferredSize(new Dimension(120, 40));
        panelButton.add(decryptButton);
        panelDecrypt.add(panelButton);

        //Encrypt Output
        plainTextAreaDecrypt = new JTextArea();
        plainTextAreaDecrypt.setPreferredSize(new Dimension(400,200));
        TitledBorder plainTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.DECRYPT_OUTPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        plainTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        plainTextAreaDecrypt.setBorder(plainTextTitledBorder);
        panelDecrypt.add(plainTextAreaDecrypt);

        //event
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cipherTextAreaDecrypt.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter Encrypted Text to Decrypt", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(privateKeyText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter Private key", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    aSymmetricEncryption.instance((String)methodsComboBoxDecrypt.getSelectedItem());
                    aSymmetricEncryption.convertPrivateKey(privateKeyText.getText());
                    String decrypt = aSymmetricEncryption.decrypt(cipherTextAreaDecrypt.getText());
                    plainTextAreaDecrypt.setText(decrypt);
                }
            }
        });

        body.add(panelDecrypt);
    }

    private void renderEncrypt(JPanel body) {
        JPanel panelEncrypt = new JPanel();
        panelEncrypt.setLayout(new BoxLayout(panelEncrypt, BoxLayout.Y_AXIS));

        //PlainText
        plainTextAreaEncrypt = new JTextArea();
        plainTextAreaEncrypt.setPreferredSize(new Dimension(400,200));
        TitledBorder plainTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PLAINTEXT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        plainTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        plainTextAreaEncrypt.setBorder(plainTextTitledBorder);
        panelEncrypt.add(plainTextAreaEncrypt);

        //PublicKey
        publicKeyText = new JTextArea();
        publicKeyText.setPreferredSize(new Dimension(400, 100));
        TitledBorder publicKeyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PUBLIC_KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        publicKeyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        publicKeyText.setBorder(publicKeyTitledBorder);
        panelEncrypt.add(publicKeyText);

        //Type
        JPanel panelType = new JPanel(new FlowLayout());
        JLabel typeLabel = new JLabel("Select Cipher Type");
        panelType.add(typeLabel);
        methodsComboBoxEncrypt = new JComboBox(this.methods);
        panelType.add(methodsComboBoxEncrypt);
        panelEncrypt.add(panelType);

        //Button
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptButton = new JButton("Encrypt");
        encryptButton.setBackground(Color.RED);
        encryptButton.setFont(new Font("Arial", Font.BOLD, 16));
        encryptButton.setPreferredSize(new Dimension(120, 40));
        panelButton.add(encryptButton);
        panelEncrypt.add(panelButton);

        //Encrypt Output
        cipherTextAreaEncrypt = new JTextArea();
        cipherTextAreaEncrypt.setPreferredSize(new Dimension(400,200));
        TitledBorder cipherTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.ENCRYPT_OUTPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        cipherTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        cipherTextAreaEncrypt.setBorder(cipherTextTitledBorder);
        panelEncrypt.add(cipherTextAreaEncrypt);

        //event
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(plainTextAreaEncrypt.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter Plain Text to Encrypt", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(publicKeyText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Enter Public key", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    aSymmetricEncryption.instance((String)methodsComboBoxEncrypt.getSelectedItem());
                    aSymmetricEncryption.convertPublicKey(publicKeyText.getText());
                    String encrypt = aSymmetricEncryption.encrypt(plainTextAreaEncrypt.getText());
                    cipherTextAreaEncrypt.setText(encrypt);
                }
            }
        });

        body.add(panelEncrypt);
    }

    private void renderControl(JPanel panel) {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Algorithm :");
        controlPanel.add(nameLabel);

        nameCipherLabel = new JLabel(aSymmetricEncryption.name);
        Font font = new Font("Serif", Font.BOLD, 20);
        nameCipherLabel.setFont(font);
        controlPanel.add(nameCipherLabel);

        //space
        JLabel spaceLabel1 = new JLabel("          ");
        controlPanel.add(spaceLabel1);


        JLabel createKeyLabel = new JLabel("You don't have a key? Click the button");
        controlPanel.add(createKeyLabel);

        createKeyButton = new JButton("Create Key");
        controlPanel.add(createKeyButton);
        //event
        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateKeyRSAScreen();
            }
        });


        panel.add(controlPanel);
    }
}
