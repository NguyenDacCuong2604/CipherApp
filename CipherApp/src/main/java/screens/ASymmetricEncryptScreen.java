package screens;

import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASymmetricEncryptScreen extends JFrame {
    AbsASymmetricEncryption aSymmetricEncryption;
    JPanel controlPanel;
    JLabel nameCipherLabel;
    JButton createKeyButton, encryptButton, decryptButton, pasteButtonEncrypt, pasteButtonDecrypt, clearButtonEncrypt, clearButtonDecrypt;
    String[] methods;
    JComboBox methodsComboBox;
    JTextField publicKeyText, privateKeyText;
    JTextArea plainTextAreaEncrypt, cipherTextAreaEncrypt, plainTextAreaDecrypt, cipherTextAreaDecrypt;

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
        body.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Encrypt
        renderEncrypt(body);
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
        panelDecrypt.setLayout(new BoxLayout(panelDecrypt, BoxLayout.Y_AXIS));]


        body.add(panelDecrypt);
    }

    private void renderEncrypt(JPanel body) {
        JPanel panelEncrypt = new JPanel();
        panelEncrypt.setLayout(new BoxLayout(panelEncrypt, BoxLayout.Y_AXIS));



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

        JLabel modeLabel = new JLabel("Method: ");
        controlPanel.add(modeLabel);

        methodsComboBox = new JComboBox(this.methods);
        controlPanel.add(methodsComboBox);

        JLabel createKeyLabel = new JLabel("You don't have a key? Click the button to create the key");
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
