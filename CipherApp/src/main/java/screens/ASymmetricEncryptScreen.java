package screens;

import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ASymmetricEncryptScreen extends JFrame {
    AbsASymmetricEncryption aSymmetricEncryption;
    JPanel controlPanel;
    JLabel nameCipherLabel;
    JButton createKeyButton, encryptButton, decryptButton;
    String[] methods;
    JComboBox methodsComboBoxEncrypt, methodsComboBoxDecrypt, typeKeyEncryptComboBox, typeKeyDecryptComboBox;
    JTextArea plainTextAreaEncrypt, cipherTextAreaEncrypt, plainTextAreaDecrypt, cipherTextAreaDecrypt, publicKeyText, privateKeyText;
    Font font;

    public ASymmetricEncryptScreen(AbsASymmetricEncryption aSymmetricEncryption, String[] methods) {
        this.methods = methods;
        this.aSymmetricEncryption = aSymmetricEncryption;
        this.setTitle(aSymmetricEncryption.name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        font = new Font("TimesRoman", Font.PLAIN, 14);
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

        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderDecrypt(JPanel body) {
        JPanel panelDecrypt = new JPanel();
        panelDecrypt.setLayout(new BoxLayout(panelDecrypt, BoxLayout.Y_AXIS));
        panelDecrypt.setPreferredSize(new Dimension(450, 650));

        //CipherText
        cipherTextAreaDecrypt = new JTextArea(10, 35);
        cipherTextAreaDecrypt.setFont(font);
        cipherTextAreaDecrypt.setLineWrap(true);
        cipherTextAreaDecrypt.setWrapStyleWord(true);
        TitledBorder cipherTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.CIPHERTEXT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        cipherTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPaneCipherText = new JScrollPane(cipherTextAreaDecrypt);
        scrollPaneCipherText.setBorder(cipherTextTitledBorder);

        //popup input
        JPopupMenu popupMenuInputEncrypt = new JPopupMenu();
        JMenuItem pasteInputText = new JMenuItem("Paste");
        JMenuItem clearInputText = new JMenuItem("Clear");
        popupMenuInputEncrypt.add(pasteInputText);
        popupMenuInputEncrypt.add(clearInputText);
        //event click
        cipherTextAreaDecrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuInputEncrypt.show(cipherTextAreaDecrypt, e.getX(), e.getY());
                }
            }
        });
        pasteInputText.addActionListener(e -> {
            cipherTextAreaDecrypt.paste();
        });
        clearInputText.addActionListener(e -> {
            cipherTextAreaDecrypt.setText("");
        });

        panelDecrypt.add(scrollPaneCipherText);


        //Type Key
        JPanel panelTypeKey = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLabelKey = new JLabel("Select Type Key:");
        panelTypeKey.add(typeLabelKey);
        typeKeyDecryptComboBox = new JComboBox(Constants.Type.TYPE_KEY);
        typeKeyDecryptComboBox.setFocusable(false);
        typeKeyDecryptComboBox.setSelectedItem(Constants.Description.PRIVATE_KEY);
        panelTypeKey.add(typeKeyDecryptComboBox);
        panelDecrypt.add(panelTypeKey);

        //Key
        privateKeyText = new JTextArea(3, 35);
        privateKeyText.setFont(font);
        privateKeyText.setWrapStyleWord(true);
        privateKeyText.setLineWrap(true);
        TitledBorder privateKeyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        privateKeyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPanePrivateKey = new JScrollPane(privateKeyText);
        scrollPanePrivateKey.setBorder(privateKeyTitledBorder);

        //popup key
        JPopupMenu popupMenuKeyEncrypt = new JPopupMenu();
        JMenuItem pasteKeyText = new JMenuItem("Paste");
        JMenuItem clearKeyText = new JMenuItem("Clear");
        popupMenuKeyEncrypt.add(pasteKeyText);
        popupMenuKeyEncrypt.add(clearKeyText);
        //event click
        privateKeyText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuKeyEncrypt.show(privateKeyText, e.getX(), e.getY());
                }
            }
        });
        pasteKeyText.addActionListener(e -> {
            privateKeyText.paste();
        });
        clearKeyText.addActionListener(e -> {
            privateKeyText.setText("");
        });

        panelDecrypt.add(scrollPanePrivateKey);

        //Type
        JPanel panelType = new JPanel(new FlowLayout());
        JLabel typeLabel = new JLabel("Select Cipher Type");
        panelType.add(typeLabel);
        methodsComboBoxDecrypt = new JComboBox(this.methods);
        methodsComboBoxDecrypt.setFocusable(false);
        panelType.add(methodsComboBoxDecrypt);
        panelDecrypt.add(panelType);

        //Button
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        decryptButton = new JButton("Decrypt");
        decryptButton.setFocusable(false);
        decryptButton.setBackground(Color.GREEN);
        decryptButton.setFont(new Font("Arial", Font.BOLD, 16));
        decryptButton.setPreferredSize(new Dimension(140, 40));
        panelButton.add(decryptButton);
        panelDecrypt.add(panelButton);

        //Encrypt Output
        plainTextAreaDecrypt = new JTextArea(10, 35);
        plainTextAreaDecrypt.setFont(font);
        plainTextAreaDecrypt.setLineWrap(true);
        plainTextAreaDecrypt.setWrapStyleWord(true);
        plainTextAreaDecrypt.setEditable(false);
        TitledBorder plainTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.DECRYPT_OUTPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        plainTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPanePlainText = new JScrollPane(plainTextAreaDecrypt);
        scrollPanePlainText.setBorder(plainTextTitledBorder);

        //popup output
        JPopupMenu popupMenuOutputEncrypt = new JPopupMenu();
        JMenuItem copyOutputText = new JMenuItem("Copy All");
        popupMenuOutputEncrypt.add(copyOutputText);
        //event click
        plainTextAreaDecrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuOutputEncrypt.show(plainTextAreaDecrypt, e.getX(), e.getY());
                }
            }
        });
        copyOutputText.addActionListener(e -> {
            String textToCopy = plainTextAreaDecrypt.getText();

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        panelDecrypt.add(scrollPanePlainText);

        //event
        decryptButton.addActionListener(e -> {
            if (cipherTextAreaDecrypt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Encrypted Text to Decrypt", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (privateKeyText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Private key", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) methodsComboBoxDecrypt.getSelectedItem());
                String decrypt = aSymmetricEncryption.decrypt(cipherTextAreaDecrypt.getText(), (String) typeKeyDecryptComboBox.getSelectedItem(), privateKeyText.getText());
                plainTextAreaDecrypt.setText(decrypt);
            }

        });

        body.add(panelDecrypt);
    }

    private void renderEncrypt(JPanel body) {
        JPanel panelEncrypt = new JPanel();
        panelEncrypt.setPreferredSize(new Dimension(450, 650));
        panelEncrypt.setLayout(new BoxLayout(panelEncrypt, BoxLayout.Y_AXIS));

        //PlainText
        plainTextAreaEncrypt = new JTextArea(10, 35);
        plainTextAreaEncrypt.setFont(font);
        plainTextAreaEncrypt.setLineWrap(true);
        plainTextAreaEncrypt.setWrapStyleWord(true);
        TitledBorder plainTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PLAINTEXT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        plainTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPanePlainText = new JScrollPane(plainTextAreaEncrypt);
        scrollPanePlainText.setBorder(plainTextTitledBorder);
        panelEncrypt.add(scrollPanePlainText);

        //popup input
        JPopupMenu popupMenuInputEncrypt = new JPopupMenu();
        JMenuItem pasteInputText = new JMenuItem("Paste");
        JMenuItem clearInputText = new JMenuItem("Clear");
        popupMenuInputEncrypt.add(pasteInputText);
        popupMenuInputEncrypt.add(clearInputText);
        //event click
        plainTextAreaEncrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuInputEncrypt.show(plainTextAreaEncrypt, e.getX(), e.getY());
                }
            }
        });
        pasteInputText.addActionListener(e -> {
            plainTextAreaEncrypt.paste();
        });
        clearInputText.addActionListener(e -> {
            plainTextAreaEncrypt.setText("");
        });


        //Type Key
        JPanel panelTypeKey = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLabelKey = new JLabel("Select Type Key:");
        panelTypeKey.add(typeLabelKey);
        typeKeyEncryptComboBox = new JComboBox(Constants.Type.TYPE_KEY);
        typeKeyEncryptComboBox.setFocusable(false);
        panelTypeKey.add(typeKeyEncryptComboBox);
        panelEncrypt.add(panelTypeKey);


        //Key
        publicKeyText = new JTextArea(3, 35);
        publicKeyText.setFont(font);
        publicKeyText.setLineWrap(true);
        publicKeyText.setWrapStyleWord(true);
        TitledBorder publicKeyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        publicKeyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPaneKey = new JScrollPane(publicKeyText);
        scrollPaneKey.setBorder(publicKeyTitledBorder);

        //popup key
        JPopupMenu popupMenuKeyEncrypt = new JPopupMenu();
        JMenuItem pasteKeyText = new JMenuItem("Paste");
        JMenuItem clearKeyText = new JMenuItem("Clear");
        popupMenuKeyEncrypt.add(pasteKeyText);
        popupMenuKeyEncrypt.add(clearKeyText);
        //event click
        publicKeyText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuKeyEncrypt.show(publicKeyText, e.getX(), e.getY());
                }
            }
        });
        pasteKeyText.addActionListener(e -> {
            publicKeyText.paste();
        });
        clearKeyText.addActionListener(e -> {
            publicKeyText.setText("");
        });

        panelEncrypt.add(scrollPaneKey);

        //Type
        JPanel panelType = new JPanel(new FlowLayout());
        JLabel typeLabel = new JLabel("Select Cipher Type");
        panelType.add(typeLabel);
        methodsComboBoxEncrypt = new JComboBox(this.methods);
        methodsComboBoxEncrypt.setFocusable(false);
        panelType.add(methodsComboBoxEncrypt);
        panelEncrypt.add(panelType);

        //Button
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        encryptButton = new JButton("Encrypt");
        encryptButton.setFocusable(false);
        encryptButton.setBackground(Color.RED);
        encryptButton.setFont(new Font("Arial", Font.BOLD, 16));
        encryptButton.setPreferredSize(new Dimension(140, 40));
        panelButton.add(encryptButton);
        panelEncrypt.add(panelButton);

        //Encrypt Output
        cipherTextAreaEncrypt = new JTextArea(10, 35);
        cipherTextAreaEncrypt.setFont(font);
        cipherTextAreaEncrypt.setWrapStyleWord(true);
        cipherTextAreaEncrypt.setLineWrap(true);
        cipherTextAreaEncrypt.setEditable(false);
        TitledBorder cipherTextTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.ENCRYPT_OUTPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        cipherTextTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPaneCipherText = new JScrollPane(cipherTextAreaEncrypt);
        scrollPaneCipherText.setBorder(cipherTextTitledBorder);

        //popup output
        JPopupMenu popupMenuOutputEncrypt = new JPopupMenu();
        JMenuItem copyOutputText = new JMenuItem("Copy All");
        popupMenuOutputEncrypt.add(copyOutputText);
        //event click
        cipherTextAreaEncrypt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuOutputEncrypt.show(cipherTextAreaEncrypt, e.getX(), e.getY());
                }
            }
        });
        copyOutputText.addActionListener(e -> {
            String textToCopy = cipherTextAreaEncrypt.getText();

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        panelEncrypt.add(scrollPaneCipherText);

        //event
        encryptButton.addActionListener(e -> {
            if (plainTextAreaEncrypt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Plain Text to Encrypt", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (publicKeyText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Public key", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) methodsComboBoxEncrypt.getSelectedItem());
                String encrypt = aSymmetricEncryption.encrypt(plainTextAreaEncrypt.getText(), (String) typeKeyEncryptComboBox.getSelectedItem(), publicKeyText.getText());
                cipherTextAreaEncrypt.setText(encrypt);
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
        createKeyButton.setFocusable(false);
        controlPanel.add(createKeyButton);
        //event
        createKeyButton.addActionListener(e -> {
            new CreateKeyRSAScreen();

        });


        panel.add(controlPanel);
    }
}
