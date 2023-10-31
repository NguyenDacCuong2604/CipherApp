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
    String headerInput = Constants.Description.PLAINTEXT, headerOutput=Constants.Description.CIPHERTEXT, temp = Constants.Description.ENCRYPT;
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
    public SymmetricEncryptScreen(AbsSymmetricEncryption symmetricEncryption, String[] methods){
        this.methods = methods;
        this.symmetricEncryption = symmetricEncryption;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setTitle(symmetricEncryption.name);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        font = new Font("TimesRoman", Font.PLAIN, 20);

        JPanel panel = new JPanel();
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

        ivTextField = new JTextField(24);
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

        ivPanel.add(ivTextField);

        renderButtonOfIv(ivPanel);

        panel.add(ivPanel);
        ivPanel.setVisible(false);
    }

    private void renderButtonOfIv(JPanel panel) {
        ImageIcon createIcon = new ImageIcon("assets/Images/create.png");
        createIvButton = new JButton(new ImageIcon(createIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
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
        encryptRadioButton.setSelected(true);
        selectPanel.add(encryptRadioButton);
        selectButtonGroup.add(encryptRadioButton);

        decryptRadioButton = new JRadioButton(Constants.Description.DECRYPT);
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
            TitledBorder sizeTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.SIZE, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
            sizeTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
            sizesKeyComboBox.setBorder(sizeTitledBorder);
            panelKey.add(sizesKeyComboBox);
            //event
            sizesKeyComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
                }
            });
        }

        keyTextField = new JTextField(24);
        keyTextField.setFont(font);
        TitledBorder keyTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        keyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
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
        createKeyButton.setToolTipText("Create Key");
        createKeyButton.setPreferredSize(new Dimension(35, 35));
        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = symmetricEncryption.createKey();
                keyTextField.setText(key);
            }
        });
        panel.add(createKeyButton);
    }

    private void renderInput(JPanel panel){
        inputTextArea = new JTextArea(10,50);
        inputTextArea.setFont(font);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPaneInput = new JScrollPane(inputTextArea);
        scrollPaneInput.setBorder(titledBorder);
        panel.add(scrollPaneInput);

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
            }
        });
        buttonPanel.add(clickButton);

        panel.add(buttonPanel);
    }

    private void renderOutput(JPanel panel){
        outputTextArea = new JTextArea(10,50);
        outputTextArea.setFont(font);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setLineWrap(true);
        outputTextArea.setEditable(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        JScrollPane scrollPaneOutput = new JScrollPane(outputTextArea);
        scrollPaneOutput.setBorder(titledBorder);
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
        methodsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String method = (String)cb.getSelectedItem();
                if(method.contains(Constants.Mode.ECB)){
                    ivPanel.setVisible(false);
                }
                else ivPanel.setVisible(true);
                repaint();
            }
        });
        controlPanel.add(methodsComboBox);


        panel.add(controlPanel);


    }



    private void addAction(JRadioButton radioButton){
        radioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == encryptRadioButton) {
                        buttonColor = Color.RED;
                        temp = Constants.Description.ENCRYPT;
                        headerInput = Constants.Description.PLAINTEXT;
                        headerOutput = Constants.Description.CIPHERTEXT;
                    } else if (e.getSource() == decryptRadioButton) {
                        buttonColor = Color.GREEN;
                        temp = Constants.Description.DECRYPT;
                        headerInput = Constants.Description.CIPHERTEXT;
                        headerOutput = Constants.Description.PLAINTEXT;
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
