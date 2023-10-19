package screens;

import model.ASymmetricEncryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CipherScreen extends JFrame {
    JRadioButton encryptRadioButton, decryptRadioButton;
    ButtonGroup selectButtonGroup;
    String headerInput = "PlantText", headerOutput="CipherText", temp = "Encrypt";
    JTextArea inputTextArea, outputTextArea;
    JButton clickButton, pasteButton, clearButton, copyButton, pasteKeyButton, createKeyButton, clearKeyButton, copyKeyButton;
    Dimension textDimension;
    JPanel inputPanel, selectPanel, buttonPanel, outputPanel;
    JTextField keyTextField;
    Color buttonColor = Color.RED;
    ASymmetricEncryption symmetricEncryption;

    public CipherScreen(ASymmetricEncryption symmetricEncryption, String cipherType){
        this.symmetricEncryption = symmetricEncryption;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(cipherType);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        textDimension = new Dimension(600,200);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Select
        renderSelect(panel);
        //input
        renderInput(panel);
        //key
        renderKey(panel);
        //button
        renderButton(panel);
        //output
        renderOutput(panel);


        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderSelect(JPanel panel){
        selectPanel = new JPanel();

        selectButtonGroup = new ButtonGroup();

        encryptRadioButton = new JRadioButton("Encrypt");
        encryptRadioButton.setSelected(true);
        selectPanel.add(encryptRadioButton);
        selectButtonGroup.add(encryptRadioButton);

        decryptRadioButton = new JRadioButton("Decrypt");
        selectPanel.add(decryptRadioButton);
        selectButtonGroup.add(decryptRadioButton);

        addAction(encryptRadioButton);
        addAction(decryptRadioButton);

        panel.add(selectPanel);
    }

    private void renderKey(JPanel panel){
        JPanel panelKey = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        keyTextField = new JTextField();
        keyTextField.setPreferredSize(new Dimension(500, 45));
        TitledBorder keyTitledBorder = BorderFactory.createTitledBorder(null, "Key", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        keyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        keyTextField.setBorder(keyTitledBorder);

        panelKey.add(keyTextField);

        renderButtonOfKey(panelKey);

        panel.add(panelKey);
    }

    private void renderButtonOfKey(JPanel panel){
        ImageIcon pasteIcon = new ImageIcon("assets/Images/paste.png");
        pasteKeyButton = new JButton(new ImageIcon(pasteIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        pasteKeyButton.setToolTipText("Paste Key");
        pasteKeyButton.setPreferredSize(new Dimension(35, 35));
        //action paste key
        pasteKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyTextField.paste();
            }
        });
        panel.add(pasteKeyButton);

        ImageIcon clearIcon = new ImageIcon("assets/Images/clear.png");
        clearKeyButton = new JButton(new ImageIcon(clearIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        clearKeyButton.setToolTipText("Clear Key");
        clearKeyButton.setPreferredSize(new Dimension(35, 35));
        //action clear key
        clearKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyTextField.setText("");
            }
        });
        panel.add(clearKeyButton);

        ImageIcon createIcon = new ImageIcon("assets/Images/create.png");
        createKeyButton = new JButton(new ImageIcon(createIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        createKeyButton.setToolTipText("Create Key");
        createKeyButton.setPreferredSize(new Dimension(35, 35));
        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String key = symmetricEncryption.createKey();
                    keyTextField.setText(key);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(createKeyButton);

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy.png");
        copyKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        copyKeyButton.setToolTipText("Copy Key");
        copyKeyButton.setPreferredSize(new Dimension(35, 35));
        //action copy key
        copyKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = keyTextField.getText();

                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
            }
        });
        panel.add(copyKeyButton);
    }

    private void renderInput(JPanel panel){
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelOption = new JPanel();
        panelOption.setPreferredSize(new Dimension(96, 200));
        panelOption.setLayout(new BoxLayout(panelOption, BoxLayout.Y_AXIS));


        renderButtonOfInput(panelOption);
        inputPanel.add(panelOption);

        inputTextArea = new JTextArea();
        inputTextArea.setPreferredSize(textDimension);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        inputTextArea.setBorder(titledBorder);
        inputPanel.add(inputTextArea);


        panel.add(inputPanel);

    }

    private void renderButtonOfInput(JPanel panel){
        JLabel spaceLabel1 = new JLabel(" ");
        panel.add(spaceLabel1);

        //Button Clear
        ImageIcon clearIcon = new ImageIcon("assets/Images/clear.png");
        clearButton = new JButton("Clear", new ImageIcon(clearIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        clearButton.setPreferredSize(new Dimension(90, 30));
        //action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputTextArea.setText("");
            }
        });
        panel.add(clearButton);

        JLabel spaceLabel2 = new JLabel(" ");
        panel.add(spaceLabel2);

        //Button Paste
        ImageIcon pasteIcon = new ImageIcon("assets/Images/paste.png");
        pasteButton = new JButton("Paste", new ImageIcon(pasteIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        pasteButton.setPreferredSize(new Dimension(90, 30));
        //action
        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputTextArea.paste();
            }
        });
        panel.add(pasteButton);
    }

    private void renderButton(JPanel panel){
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(140,50));

        clickButton = new JButton(temp);
        clickButton.setFont(new Font("TimesRoman", Font.BOLD, 16));
        clickButton.setForeground(Color.BLACK);
        clickButton.setBackground(buttonColor);
        clickButton.setPreferredSize(new Dimension(120,40));
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(inputTextArea.getText().isEmpty()){
                    JOptionPane.showMessageDialog(CipherScreen.this, "Empty Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(keyTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(CipherScreen.this, "Empty Key!!! You can create a key by clicking on the createKey button", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String cipherText;
                    if (temp.equals("Encrypt")) {
                        SecretKey key = symmetricEncryption.convertKey(keyTextField.getText());
                        try {
                            cipherText = symmetricEncryption.encrypt(inputTextArea.getText());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                                 UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        SecretKey key = symmetricEncryption.convertKey(keyTextField.getText());
                        try {
                            cipherText = symmetricEncryption.decrypt(inputTextArea.getText());
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                                 IllegalBlockSizeException | BadPaddingException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    outputTextArea.setText(cipherText);
                }
            }
        });
        buttonPanel.add(clickButton);

        panel.add(buttonPanel);
    }

    private void renderOutput(JPanel panel){
        outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panelOption = new JPanel();
        panelOption.setPreferredSize(new Dimension(96, 200));
        panelOption.setLayout(new BoxLayout(panelOption, BoxLayout.Y_AXIS));


        renderButtonOfOutput(panelOption);
        outputPanel.add(panelOption);

        outputTextArea = new JTextArea();
        outputTextArea.setPreferredSize(textDimension);
        outputTextArea.setEditable(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        outputTextArea.setBorder(titledBorder);
        outputPanel.add(outputTextArea);

        panel.add(outputPanel);
    }

    private void renderButtonOfOutput(JPanel panel){
        JLabel spaceLabel = new JLabel(" ");
        panel.add(spaceLabel);

        //Button Copy
        ImageIcon copyIcon = new ImageIcon("assets/Images/copy.png");
        copyButton = new JButton("Copy", new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(90, 30));
        //action
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = outputTextArea.getText();

                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
            }
        });
        panel.add(copyButton);


    }



    private void addAction(JRadioButton radioButton){
        radioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == encryptRadioButton) {
                        buttonColor = Color.RED;
                        temp = "Encrypt";
                        headerInput = "PlantText";
                        headerOutput = "CipherText";
                    } else if (e.getSource() == decryptRadioButton) {
                        buttonColor = Color.GREEN;
                        temp = "Decrypt";
                        headerInput = "CipherText";
                        headerOutput = "PlantText";
                    }
                    inputTextArea.setBorder(BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    clickButton.setBackground(buttonColor);
                    clickButton.setText(temp);
                    outputTextArea.setBorder(BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    revalidate();
                    repaint();
                }
            }
        });
    }


}
