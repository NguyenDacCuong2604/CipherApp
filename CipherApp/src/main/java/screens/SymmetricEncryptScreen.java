package screens;

import constant.Constants;
import model.SysmmetricEncryption.AbsSymmetricEncryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class SymmetricEncryptScreen extends JFrame {
    JRadioButton encryptRadioButton, decryptRadioButton;
    ButtonGroup selectButtonGroup;
    String headerInput = Constants.Description.PLAINTEXT, headerOutput=Constants.Description.ENCRYPT_OUTPUT, temp = Constants.Description.ENCRYPT;
    JTextArea inputTextArea, outputTextArea;
    JButton clickButton, createKeyButton,createIvButton;
    JPanel selectPanel, buttonPanel, controlPanel, ivPanel;
    JLabel nameCipherLabel;
    JTextField keyTextField, ivTextField;
    Color buttonColor = Color.RED;
    AbsSymmetricEncryption symmetricEncryption;
    JComboBox methodsComboBox, sizesKeyComboBox;
    String[] methods;
    Font font;
    JScrollPane scrollPaneInput, scrollPaneOutput;
    public SymmetricEncryptScreen(AbsSymmetricEncryption symmetricEncryption, String[] methods){
        this.methods = methods;
        this.symmetricEncryption = symmetricEncryption;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setTitle(symmetricEncryption.name);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        font = new Font("TimesRoman", Font.PLAIN, 20);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(760, 750));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Control
        renderControl(panel);
        //Select
        renderSelect(panel);
        //input
        renderInput(panel);
        //key
        renderKey(panel);
        //renderInitVector
        renderIv(panel);
        //button
        renderButton(panel);
        //output
        renderOutput(panel);

        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderIv(JPanel panel) {
        ivPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        ivTextField = new JTextField(30);
        ivTextField.setFont(font);
        TitledBorder keyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.IV, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        keyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        ivTextField.setBorder(keyTitledBorder);

        ivTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkLength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkLength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkLength();
            }

            private void checkLength() {
                if (ivTextField.getText().length() > symmetricEncryption.iv) {
                    EventQueue.invokeLater(() -> {
                        String text = ivTextField.getText().substring(0, symmetricEncryption.iv);
                        ivTextField.setText(text);
                    });
                }
            }
        });

        //popup
        JPopupMenu popupMenuIv = new JPopupMenu();
        JMenuItem pasteIvItem = new JMenuItem("Paste");
        JMenuItem createIvItem = new JMenuItem("Create");
        JMenuItem clearIvItem = new JMenuItem("Clear");
        JMenuItem copyIvItem = new JMenuItem("Copy Iv");
        popupMenuIv.add(pasteIvItem);
        popupMenuIv.add(createIvItem);
        popupMenuIv.add(clearIvItem);
        popupMenuIv.add(copyIvItem);

        //event popup
        ivTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuIv.show(ivTextField, e.getX(), e.getY());
                }
            }
        });
        //event paste
        pasteIvItem.addActionListener(e -> {
            ivTextField.paste();
        });
        //event create
        createIvItem.addActionListener(e -> {
            String key = symmetricEncryption.createIv();
            ivTextField.setText(key);
        });
        //event clear
        clearIvItem.addActionListener(e -> {
            ivTextField.setText("");
        });
        //event copy
        copyIvItem.addActionListener(e -> {
            String textToCopy = ivTextField.getText();

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        ivPanel.add(ivTextField);

        renderButtonOfIv(ivPanel);

        panel.add(ivPanel);
        ivPanel.setVisible(false);
    }

    private void renderButtonOfIv(JPanel panel) {
        ImageIcon createIcon = new ImageIcon("assets/Images/create.png");
        createIvButton = new JButton(new ImageIcon(createIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        createIvButton.setFocusable(false);
        createIvButton.setToolTipText("Create InitVector");
        createIvButton.setPreferredSize(new Dimension(35, 35));
        createIvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String key = symmetricEncryption.createIv();
                    ivTextField.setText(key);

            }
        });

        panel.add(createIvButton);
    }

    private void renderSelect(JPanel panel){
        selectPanel = new JPanel();

        selectButtonGroup = new ButtonGroup();

        encryptRadioButton = new JRadioButton(Constants.Description.ENCRYPT);
        encryptRadioButton.setFocusable(false);
        encryptRadioButton.setSelected(true);
        selectPanel.add(encryptRadioButton);
        selectButtonGroup.add(encryptRadioButton);

        decryptRadioButton = new JRadioButton(Constants.Description.DECRYPT);
        decryptRadioButton.setFocusable(false);
        selectPanel.add(decryptRadioButton);
        selectButtonGroup.add(decryptRadioButton);

        addAction(encryptRadioButton);
        addAction(decryptRadioButton);

        panel.add(selectPanel);
    }

    private void renderKey(JPanel panel){
        JPanel panelKey = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if(symmetricEncryption.name.equals(Constants.Cipher.AES)){
            sizesKeyComboBox = new JComboBox(Constants.List_Size.SIZE_AES);
            sizesKeyComboBox.setFocusable(false);
            TitledBorder sizeTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.SIZE, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
            sizeTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
            sizesKeyComboBox.setBorder(sizeTitledBorder);
            panelKey.add(sizesKeyComboBox);
            //event
            sizesKeyComboBox.addActionListener(e -> {
                    JComboBox cb = (JComboBox) e.getSource();
                    String method = (String)cb.getSelectedItem();
                    if(method.contains(Constants.Size.BITS128)){
                        symmetricEncryption.size = 16;
                    }
                    else if (method.contains(Constants.Size.BITS192)){
                        symmetricEncryption.size = 24;
                    }
                    else if (method.contains(Constants.Size.BITS256)){
                        symmetricEncryption.size = 32;
                    }
            });
        }

        keyTextField = new JTextField(30);
        keyTextField.setFont(font);
        TitledBorder keyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        keyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));

        //popup
        JPopupMenu popupMenuKey = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem("Paste");
        JMenuItem createKeyItem = new JMenuItem("Create");
        JMenuItem clearKeyItem = new JMenuItem("Clear");
        JMenuItem copyKeyItem = new JMenuItem("Copy Key");
        popupMenuKey.add(pasteKeyItem);
        popupMenuKey.add(createKeyItem);
        popupMenuKey.add(clearKeyItem);
        popupMenuKey.add(copyKeyItem);

        //event popup
        keyTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuKey.show(keyTextField, e.getX(), e.getY());
                }
            }
        });
        //event paste
        pasteKeyItem.addActionListener(e -> {
            keyTextField.paste();
        });
        //event create
        createKeyItem.addActionListener(e -> {
            if(symmetricEncryption.name.equals(Constants.Cipher.HILL) || symmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                symmetricEncryption.instance((String)methodsComboBox.getSelectedItem());
            }

            String key = symmetricEncryption.createKey();
            keyTextField.setText(key);
        });
        //event clear
        clearKeyItem.addActionListener(e -> {
            keyTextField.setText("");
        });
        //event copy
        copyKeyItem.addActionListener(e -> {
            String textToCopy = keyTextField.getText();

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        keyTextField.setBorder(keyTitledBorder);

        keyTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkLength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkLength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkLength();
            }

            private void checkLength() {
                if (keyTextField.getText().length() > symmetricEncryption.size) {
                    EventQueue.invokeLater(() -> {
                        String text = keyTextField.getText().substring(0, symmetricEncryption.size);
                        keyTextField.setText(text);
                    });
                }
            }
        });

        panelKey.add(keyTextField);

        renderButtonOfKey(panelKey);

        panel.add(panelKey);
    }

    private void renderButtonOfKey(JPanel panel){
        ImageIcon createIcon = new ImageIcon("assets/Images/create.png");
        createKeyButton = new JButton(new ImageIcon(createIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        createKeyButton.setFocusable(false);
        createKeyButton.setToolTipText("Create Key");
        createKeyButton.setPreferredSize(new Dimension(35, 35));
        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(symmetricEncryption.name.equals(Constants.Cipher.HILL) || symmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                    symmetricEncryption.instance((String)methodsComboBox.getSelectedItem());
                }
                String key = symmetricEncryption.createKey();
                keyTextField.setText(key);
            }
        });
        panel.add(createKeyButton);
    }

    private void renderInput(JPanel panel){
        inputTextArea = new JTextArea(8,40);
        inputTextArea.setFont(font);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        scrollPaneInput = new JScrollPane(inputTextArea);
        scrollPaneInput.setBorder(titledBorder);

        //popup
        JPopupMenu popupMenuInput = new JPopupMenu();
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem clearItem = new JMenuItem("Clear");
        popupMenuInput.add(pasteItem);
        popupMenuInput.add(clearItem);

        //popup event
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuInput.show(inputTextArea, e.getX(), e.getY());
                }
            }
        });

        //event paste
        pasteItem.addActionListener(e ->{
            inputTextArea.paste();
        });
        //event clear
        clearItem.addActionListener(e -> {
            inputTextArea.setText("");
            outputTextArea.setText("");
        });



        panel.add(scrollPaneInput);

    }

    private void renderButton(JPanel panel){
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(140,50));

        clickButton = new JButton(temp);
        clickButton.setFocusable(false);
        clickButton.setFont(new Font("TimesRoman", Font.BOLD, 16));
        clickButton.setForeground(Color.BLACK);
        clickButton.setBackground(buttonColor);
        clickButton.setPreferredSize(new Dimension(120,40));
        clickButton.addActionListener(e -> {
                if(inputTextArea.getText().isEmpty()){
                    JOptionPane.showMessageDialog(SymmetricEncryptScreen.this, "Empty Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(keyTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(SymmetricEncryptScreen.this, "Empty Key!!! You can create a key by clicking on the createKey button", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String cipherText;
                    symmetricEncryption.instance((String)methodsComboBox.getSelectedItem());
                    SecretKey key = symmetricEncryption.convertKey(keyTextField.getText());
                    IvParameterSpec iv = symmetricEncryption.convertIv(ivTextField.getText());
                    if (temp.equals(Constants.Description.ENCRYPT)) {
                            cipherText = symmetricEncryption.encrypt(inputTextArea.getText());
                    } else {
                        cipherText = symmetricEncryption.decrypt(inputTextArea.getText());
                    }
                    outputTextArea.setText(cipherText);
                }
        });
        buttonPanel.add(clickButton);

        panel.add(buttonPanel);
    }

    private void renderOutput(JPanel panel){
        outputTextArea = new JTextArea(8,40);
        outputTextArea.setFont(font);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setLineWrap(true);
        outputTextArea.setEditable(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        scrollPaneOutput = new JScrollPane(outputTextArea);
        scrollPaneOutput.setBorder(titledBorder);

        //popup
        JPopupMenu popupMenuOutput = new JPopupMenu();
        JMenuItem copyAll = new JMenuItem("Copy All");
        popupMenuOutput.add(copyAll);
        //event popup
        outputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuOutput.show(outputTextArea, e.getX(), e.getY());
                }
            }
        });
        //event copy all
        copyAll.addActionListener(e -> {
            String textToCopy = outputTextArea.getText();

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        panel.add(scrollPaneOutput);
    }

    private void renderControl(JPanel panel){
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Algorithm :");
        controlPanel.add(nameLabel);

        nameCipherLabel = new JLabel(symmetricEncryption.name);
        Font font = new Font("Serif", Font.BOLD, 20);
        nameCipherLabel.setFont(font);
        controlPanel.add(nameCipherLabel);

        JLabel spaceLabel1 = new JLabel("      ");
        controlPanel.add(spaceLabel1);

        JLabel modeLabel = new JLabel("Method: ");
        controlPanel.add(modeLabel);

        methodsComboBox = new JComboBox(this.methods);
        methodsComboBox.addActionListener(e -> {
                JComboBox cb = (JComboBox) e.getSource();
                String method = (String)cb.getSelectedItem();
                if(method.contains(Constants.Mode.ECB)||method.contains(Constants.Cipher.HILL)||method.contains(Constants.Cipher.VIGENERE)){
                    ivPanel.setVisible(false);
                }
                else ivPanel.setVisible(true);
                repaint();

        });
        methodsComboBox.setFocusable(false);
        controlPanel.add(methodsComboBox);


        panel.add(controlPanel);


    }



    private void addAction(JRadioButton radioButton){
        radioButton.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == encryptRadioButton) {
                        buttonColor = Color.RED;
                        temp = Constants.Description.ENCRYPT;
                        headerInput = Constants.Description.PLAINTEXT;
                        headerOutput = Constants.Description.ENCRYPT_OUTPUT;
                    } else if (e.getSource() == decryptRadioButton) {
                        buttonColor = Color.GREEN;
                        temp = Constants.Description.DECRYPT;
                        headerInput = Constants.Description.CIPHERTEXT;
                        headerOutput = Constants.Description.DECRYPT_OUTPUT;
                    }
                    scrollPaneInput.setBorder(BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    clickButton.setBackground(buttonColor);
                    clickButton.setText(temp);
                    scrollPaneOutput.setBorder(BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    revalidate();
                    repaint();
                }

        });
    }


}
