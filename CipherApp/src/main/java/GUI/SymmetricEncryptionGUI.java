package GUI;

import GUI.Component.*;
import constant.Constants;
import model.SysmmetricEncryption.AbsSymmetricEncryption;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class SymmetricEncryptionGUI extends JFrame {
    AbsSymmetricEncryption absSymmetricEncryptionEncrypt, absSymmetricEncryptionDecrypt;
    String[] methods;

    public SymmetricEncryptionGUI(AbsSymmetricEncryption absSymmetricEncryptionEncrypt, AbsSymmetricEncryption absSymmetricEncryptionDecrypt, String[] methods) {
        this.absSymmetricEncryptionEncrypt = absSymmetricEncryptionEncrypt;
        this.absSymmetricEncryptionDecrypt = absSymmetricEncryptionEncrypt;
        this.methods = methods;
        this.setUndecorated(true);
        Image icon = Toolkit.getDefaultToolkit().getImage(Constants.Image.ICON);
        this.setIconImage(icon);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        //titleBar
        new TitleBar(this, mainPanel, absSymmetricEncryptionEncrypt.name);
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.WHITE, 15));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //body
        JPanel panelBody = new JPanel();
        panelBody.setBackground(Color.WHITE);
        TitledBorder cipherBorder = BorderFactory.createTitledBorder(null, Constants.Description.ENCRYPT_DECRYPT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, Constants.Font_Text.PLAIN_16, Color.BLACK);
        cipherBorder.setBorder(new LineBorder(Color.BLACK, 1));
        panelBody.setBorder(cipherBorder);
        //renderEncryption
        renderEncryption(panelBody, absSymmetricEncryptionEncrypt);
        //left
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 600));
        panelBody.add(leftPanel);
        //line
        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 600));
        panelBody.add(linePanel);
        //right
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(12, 600));
        panelBody.add(rightPanel);
        //renderDecryption
        renderDecryption(panelBody, absSymmetricEncryptionDecrypt);
        panel.add(panelBody);
        mainPanel.add(panel, BorderLayout.CENTER);
        this.add(mainPanel);
        setFocusable(true);
        requestFocus();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderDecryption(JPanel panel, AbsSymmetricEncryption absSymmetricEncryption) {
        JComboBox sizesKeyComboBox = new CustomComboBox(Constants.List_Size.SIZE_AES, new Dimension(85, 34), Constants.Font_Text.BOLD_16, Color.WHITE);;
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JPanel decryptionPanel = new JPanel();
        decryptionPanel.setBackground(Color.WHITE);
        decryptionPanel.setLayout(new BoxLayout(decryptionPanel, BoxLayout.Y_AXIS));
        //title
        JPanel titleNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        titleNamePanel.setBackground(Color.WHITE);
        JLabel titleNameLabel = new JLabel(Constants.Description.DECRYPTION);
        titleNameLabel.setFont(Constants.Font_Text.BOLD_22);
        titleNamePanel.add(titleNameLabel);
        decryptionPanel.add(titleNamePanel);
        //name input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputNameLabel = new JLabel(Constants.Description.ENTER_DECRYPT);
        inputNameLabel.setFont(Constants.Font_Text.BOLD_16);
        inputNamePanel.add(inputNameLabel);
        decryptionPanel.add(inputNamePanel);
        //input
        JTextArea inputTextArea = new CustomTextArea(Constants.Font_Text.PLAIN_16) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_DECRYPT, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane inputScrollPane = new CustomScrollPanel(inputTextArea, new Dimension(450, 160), Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(inputScrollPane);
        //select mode
        JPanel selectModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        selectModePanel.setBackground(Color.WHITE);
        JLabel selectModeLabel = new JLabel(Constants.Description.TYPE_MODE);
        selectModeLabel.setFont(Constants.Font_Text.BOLD_16);
        selectModePanel.add(selectModeLabel);
        decryptionPanel.add(selectModePanel);
        //mode
        JComboBox modeComboBox = new CustomComboBox(methods, new Dimension(450, 34), Constants.Font_Text.BOLD_16, Color.WHITE);
        decryptionPanel.add(modeComboBox);
        //Name Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        //name
        JLabel nameKeyLabel = new JLabel(Constants.Description.ENTER_SECRET);
        nameKeyLabel.setFont(Constants.Font_Text.BOLD_16);
        keyNamePanel.add(nameKeyLabel);
        //button copy
        keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        JButton copyKeyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyKeyButton.setToolTipText(Constants.Description.COPY);
        keyNamePanel.add(copyKeyButton);
        keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        //size
        if (absSymmetricEncryption.name.equals(Constants.Cipher.AES)) {
            JPanel linePanel = new JPanel();
            linePanel.setBackground(Color.BLACK);
            linePanel.setPreferredSize(new Dimension(2, 22));
            keyNamePanel.add(linePanel);
            JLabel sizeLabel = new JLabel(Constants.Description.SIZE);
            sizeLabel.setFont(Constants.Font_Text.BOLD_16);
            keyNamePanel.add(sizeLabel);
            keyNamePanel.add(new JLabel(Constants.Description.BLANK));
            //combobox
            keyNamePanel.add(sizesKeyComboBox);
            keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        }
        //generate Key
        JButton generateKeyButton = new CustomButton(Constants.Description.GENERATE_KEY, new Dimension(150, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        keyNamePanel.add(generateKeyButton);
        decryptionPanel.add(keyNamePanel);
        //key
        JTextField keyTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_SECRET, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(Constants.Font_Text.PLAIN_16);
        keyTextArea.setPreferredSize(new Dimension(450, 34));
        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setForeground(Color.WHITE);
        decryptionPanel.add(keyScrollPane);
        //Name iv
        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        ivPanel.setBackground(Color.WHITE);
        //name iv
        JLabel ivLabel = new JLabel(Constants.Description.ENTER_IV);
        ivLabel.setFont(Constants.Font_Text.BOLD_16);
        ivPanel.add(ivLabel);
        //button copy
        ivPanel.add(new JLabel(Constants.Description.BLANK));
        JButton copyIvButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyIvButton.setToolTipText(Constants.Description.COPY);
        ivPanel.add(copyIvButton);
        ivPanel.add(new JLabel(Constants.Description.BLANK));
        //generate iv
        JButton generateIvButton = new CustomButton(Constants.Description.GENERATE_IV, new Dimension(120, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        ivPanel.add(generateIvButton);
        ivPanel.setVisible(false);
        decryptionPanel.add(ivPanel);
        //iv
        JTextField ivTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_INIT_VECTOR, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        ivTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        ivTextArea.setFont(Constants.Font_Text.PLAIN_16);
        ivTextArea.setPreferredSize(new Dimension(450, 34));
        JScrollPane ivScrollPane = new JScrollPane(ivTextArea);
        ivScrollPane.setForeground(Color.WHITE);
        ivScrollPane.setVisible(false);
        decryptionPanel.add(ivScrollPane);
        //button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton decryptButton = new CustomButton(Constants.Description.DECRYPT, new Dimension(100, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        decryptButton.setPreferredSize(new Dimension(100, 34));
        buttonPanel.add(decryptButton);
        decryptionPanel.add(buttonPanel);
        //name output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        //name
        JLabel nameOutputLabel = new JLabel(Constants.Description.DECRYPT_OUTPUT);
        nameOutputLabel.setFont(Constants.Font_Text.BOLD_16);
        outputPanel.add(nameOutputLabel);
        outputPanel.add(new JLabel(Constants.Description.BLANK));
        //copy button
        JButton copyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyButton.setToolTipText(Constants.Description.COPY);
        outputPanel.add(copyButton);
        decryptionPanel.add(outputPanel);
        //output
        JTextArea outTextArea = new CustomTextArea(Constants.Font_Text.PLAIN_16) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.RESULT_TEXT, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        outTextArea.setEditable(false);
        outTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane outScrollPane = new CustomScrollPanel(outTextArea, new Dimension(450, 160), Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(outScrollPane);
        panel.add(decryptionPanel);
        //event
        //combobox
        modeComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String) cb.getSelectedItem();
            if (method.contains(Constants.Mode.ECB) || method.contains(Constants.Cipher.HILL) || method.contains(Constants.Cipher.VIGENERE)) {
                ivPanel.setVisible(false);
                ivScrollPane.setVisible(false);
            } else {
                ivPanel.setVisible(true);
                ivScrollPane.setVisible(true);
            }
            this.pack();
            repaint();
        });
        //key
        keyTextArea.getDocument().addDocumentListener(new DocumentListener() {
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
                if (keyTextArea.getText().length() > absSymmetricEncryption.size) {
                    EventQueue.invokeLater(() -> {
                        String text = keyTextArea.getText().substring(0, absSymmetricEncryption.size);
                        keyTextArea.setText(text);
                    });
                }
            }
        });
        //combobox size
        sizesKeyComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String) cb.getSelectedItem();
            if (method.contains(Constants.Size.BITS128)) {
                absSymmetricEncryption.size = 16;
            } else if (method.contains(Constants.Size.BITS192)) {
                absSymmetricEncryption.size = 24;
            } else if (method.contains(Constants.Size.BITS256)) {
                absSymmetricEncryption.size = 32;
            }
            String currentText = keyTextArea.getText();
            if (currentText.length() > absSymmetricEncryption.size) {
                EventQueue.invokeLater(() -> {
                    String newText = currentText.substring(0, absSymmetricEncryption.size);
                    keyTextArea.setText(newText);
                });
            }
        });
        //iv
        ivTextArea.getDocument().addDocumentListener(new DocumentListener() {
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
                if (ivTextArea.getText().length() > absSymmetricEncryption.iv) {
                    EventQueue.invokeLater(() -> {
                        String text = ivTextArea.getText().substring(0, absSymmetricEncryption.iv);
                        ivTextArea.setText(text);
                    });
                }
            }
        });
        //create key
        generateKeyButton.addActionListener(e -> {
            if (absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)) {
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        //create iv
        generateIvButton.addActionListener(e -> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        //decrypt
        decryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, Constants.Description.ENTER_DECRYPT, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, Constants.Description.EMPTY_KEY, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                String cipherText;
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
                absSymmetricEncryption.convertKey(keyTextArea.getText());
                absSymmetricEncryption.convertIv(ivTextArea.getText());
                cipherText = absSymmetricEncryption.decrypt(inputTextArea.getText());
                outTextArea.setText(cipherText);
            }
        });
        //copy Key
        copyKeyButton.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //copy Iv
        copyIvButton.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //copy output
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //popup
        //popup input
        JPopupMenu inputPopup = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearInputItem = new JMenuItem(Constants.Description.CLEAR);
        inputPopup.add(pasteInputItem);
        inputPopup.add(clearInputItem);
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    inputPopup.show(inputTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteInputItem.addActionListener(e -> {
            inputTextArea.paste();
        });
        clearInputItem.addActionListener(e -> {
            inputTextArea.setText(Constants.Description.EMPTY);
            outTextArea.setText(Constants.Description.EMPTY);
        });
        //popup key
        JPopupMenu keyPopup = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearKeyItem = new JMenuItem(Constants.Description.CLEAR);
        JMenuItem generateKeyItem = new JMenuItem(Constants.Description.GENERATE_KEY);
        JMenuItem copyAllKeyItem = new JMenuItem(Constants.Description.COPY_ALL);
        keyPopup.add(pasteKeyItem);
        keyPopup.add(clearKeyItem);
        keyPopup.add(generateKeyItem);
        keyPopup.add(copyAllKeyItem);
        keyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    keyPopup.show(keyTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteKeyItem.addActionListener(e -> {
            keyTextArea.paste();
        });
        clearKeyItem.addActionListener(e -> {
            keyTextArea.setText(Constants.Description.EMPTY);
        });
        generateKeyItem.addActionListener(e -> {
            if (absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)) {
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        copyAllKeyItem.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //popup iv
        //popup key
        JPopupMenu ivPopup = new JPopupMenu();
        JMenuItem pasteIvItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearIvItem = new JMenuItem(Constants.Description.CLEAR);
        JMenuItem generateIvItem = new JMenuItem(Constants.Description.GENERATE_IV);
        JMenuItem copyAllIvItem = new JMenuItem(Constants.Description.COPY_ALL);
        ivPopup.add(pasteIvItem);
        ivPopup.add(clearIvItem);
        ivPopup.add(generateIvItem);
        ivPopup.add(copyAllIvItem);
        ivTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ivPopup.show(ivTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteIvItem.addActionListener(e -> {
            ivTextArea.paste();
        });
        clearIvItem.addActionListener(e -> {
            ivTextArea.setText(Constants.Description.EMPTY);
        });
        generateIvItem.addActionListener(e -> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        copyAllIvItem.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllOutputItem = new JMenuItem(Constants.Description.COPY_ALL);
        outputPopupMenu.add(copyAllOutputItem);
        outTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    outputPopupMenu.show(outTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllOutputItem.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
    }

    private void renderEncryption(JPanel panel, AbsSymmetricEncryption absSymmetricEncryption) {
        JComboBox sizesKeyComboBox = new CustomComboBox(Constants.List_Size.SIZE_AES, new Dimension(85, 34), Constants.Font_Text.BOLD_16, Color.WHITE);
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setBackground(Color.WHITE);
        encryptionPanel.setLayout(new BoxLayout(encryptionPanel, BoxLayout.Y_AXIS));
        //title
        JPanel titleNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        titleNamePanel.setBackground(Color.WHITE);
        JLabel titleNameLabel = new JLabel(Constants.Description.ENCRYPTION);
        titleNameLabel.setFont(Constants.Font_Text.BOLD_22);
        titleNamePanel.add(titleNameLabel);
        encryptionPanel.add(titleNamePanel);
        //name input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputNameLabel = new JLabel(Constants.Description.ENTER_ENCRYPT);
        inputNameLabel.setFont(Constants.Font_Text.BOLD_16);
        inputNamePanel.add(inputNameLabel);
        encryptionPanel.add(inputNamePanel);
        //input
        JTextArea inputTextArea = new CustomTextArea(Constants.Font_Text.PLAIN_16) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_ENCRYPT, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane inputScrollPane = new CustomScrollPanel(inputTextArea, new Dimension(450, 160), Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(inputScrollPane);
        //select mode
        JPanel selectModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        selectModePanel.setBackground(Color.WHITE);
        JLabel selectModeLabel = new JLabel(Constants.Description.TYPE_MODE);
        selectModeLabel.setFont(Constants.Font_Text.BOLD_16);
        selectModePanel.add(selectModeLabel);
        encryptionPanel.add(selectModePanel);
        //mode
        JComboBox modeComboBox = new CustomComboBox(methods, new Dimension(450, 34), Constants.Font_Text.BOLD_16, Color.WHITE);
        encryptionPanel.add(modeComboBox);
        //Name Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        //name
        JLabel nameKeyLabel = new JLabel(Constants.Description.ENTER_SECRET);
        nameKeyLabel.setFont(Constants.Font_Text.BOLD_16);
        keyNamePanel.add(nameKeyLabel);
        //button copy
        keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        JButton copyKeyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyKeyButton.setToolTipText(Constants.Description.COPY);
        keyNamePanel.add(copyKeyButton);
        keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        //size
        if (absSymmetricEncryption.name.equals(Constants.Cipher.AES)) {
            JPanel linePanel = new JPanel();
            linePanel.setBackground(Color.BLACK);
            linePanel.setPreferredSize(new Dimension(2, 22));
            keyNamePanel.add(linePanel);
            JLabel sizeLabel = new JLabel(Constants.Description.SIZE);
            sizeLabel.setFont(Constants.Font_Text.BOLD_16);
            keyNamePanel.add(sizeLabel);
            keyNamePanel.add(new JLabel(Constants.Description.BLANK));
            //combobox
            keyNamePanel.add(sizesKeyComboBox);
            keyNamePanel.add(new JLabel(Constants.Description.BLANK));
        }
        //generate Key
        JButton generateKeyButton = new CustomButton(Constants.Description.GENERATE_KEY, new Dimension(150, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        keyNamePanel.add(generateKeyButton);
        encryptionPanel.add(keyNamePanel);
        //key
        JTextField keyTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_SECRET, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(Constants.Font_Text.PLAIN_16);
        keyTextArea.setPreferredSize(new Dimension(450, 34));
        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setForeground(Color.WHITE);
        encryptionPanel.add(keyScrollPane);
        //Name iv
        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        ivPanel.setBackground(Color.WHITE);
        //name iv
        JLabel ivLabel = new JLabel(Constants.Description.ENTER_IV);
        ivLabel.setFont(Constants.Font_Text.BOLD_16);
        ivPanel.add(ivLabel);
        //button copy
        ivPanel.add(new JLabel(Constants.Description.BLANK));
        JButton copyIvButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyIvButton.setToolTipText(Constants.Description.COPY);
        ivPanel.add(copyIvButton);
        ivPanel.add(new JLabel(Constants.Description.BLANK));
        //generate iv
        JButton generateIvButton = new CustomButton(Constants.Description.GENERATE_IV, new Dimension(120, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        ivPanel.add(generateIvButton);
        ivPanel.setVisible(false);
        encryptionPanel.add(ivPanel);
        //iv
        JTextField ivTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_INIT_VECTOR, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        ivTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        ivTextArea.setFont(Constants.Font_Text.PLAIN_16);
        ivTextArea.setPreferredSize(new Dimension(450, 34));
        JScrollPane ivScrollPane = new JScrollPane(ivTextArea);
        ivScrollPane.setForeground(Color.WHITE);
        ivScrollPane.setVisible(false);
        encryptionPanel.add(ivScrollPane);
        //button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton encryptButton = new CustomButton(Constants.Description.ENCRYPT, new Dimension(100, 34), Constants.Font_Text.BOLD_14, Constants.ColorUI.BUTTON_CLICK, Color.WHITE);
        encryptButton.setPreferredSize(new Dimension(100, 34));
        buttonPanel.add(encryptButton);
        encryptionPanel.add(buttonPanel);
        //name output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        //name
        JLabel nameOutputLabel = new JLabel(Constants.Description.ENCRYPT_OUTPUT);
        nameOutputLabel.setFont(Constants.Font_Text.BOLD_16);
        outputPanel.add(nameOutputLabel);
        outputPanel.add(new JLabel(Constants.Description.BLANK));
        //copy button
        JButton copyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyButton.setToolTipText(Constants.Description.COPY);
        outputPanel.add(copyButton);
        encryptionPanel.add(outputPanel);
        //output
        JTextArea outTextArea = new CustomTextArea(Constants.Font_Text.PLAIN_16) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.RESULT_TEXT, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        outTextArea.setEditable(false);
        outTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane outScrollPane = new CustomScrollPanel(outTextArea, new Dimension(450, 160), Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(outScrollPane);
        panel.add(encryptionPanel);
        //event
        //combobox
        modeComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String) cb.getSelectedItem();
            if (method.contains(Constants.Mode.ECB) || method.contains(Constants.Cipher.HILL) || method.contains(Constants.Cipher.VIGENERE)) {
                ivPanel.setVisible(false);
                ivScrollPane.setVisible(false);
            } else {
                ivPanel.setVisible(true);
                ivScrollPane.setVisible(true);
            }
            this.pack();
            repaint();
        });
        //key
        keyTextArea.getDocument().addDocumentListener(new DocumentListener() {
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
                if (keyTextArea.getText().length() > absSymmetricEncryption.size) {
                    EventQueue.invokeLater(() -> {
                        String text = keyTextArea.getText().substring(0, absSymmetricEncryption.size);
                        keyTextArea.setText(text);
                    });
                }
            }
        });
        //combobox size
        sizesKeyComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String) cb.getSelectedItem();
            if (method.contains(Constants.Size.BITS128)) {
                absSymmetricEncryption.size = 16;
            } else if (method.contains(Constants.Size.BITS192)) {
                absSymmetricEncryption.size = 24;
            } else if (method.contains(Constants.Size.BITS256)) {
                absSymmetricEncryption.size = 32;
            }
            String currentText = keyTextArea.getText();
            if (currentText.length() > absSymmetricEncryption.size) {
                EventQueue.invokeLater(() -> {
                    String newText = currentText.substring(0, absSymmetricEncryption.size);
                    keyTextArea.setText(newText);
                });
            }
        });
        //iv
        ivTextArea.getDocument().addDocumentListener(new DocumentListener() {
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
                if (ivTextArea.getText().length() > absSymmetricEncryption.iv) {
                    EventQueue.invokeLater(() -> {
                        String text = ivTextArea.getText().substring(0, absSymmetricEncryption.iv);
                        ivTextArea.setText(text);
                    });
                }
            }
        });
        //create key
        generateKeyButton.addActionListener(e -> {
            if (absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)) {
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        //create iv
        generateIvButton.addActionListener(e -> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        //encrypt
        encryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, Constants.Description.ENTER_ENCRYPT, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, Constants.Description.EMPTY_KEY, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                String cipherText;
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
                absSymmetricEncryption.convertKey(keyTextArea.getText());
                absSymmetricEncryption.convertIv(ivTextArea.getText());
                cipherText = absSymmetricEncryption.encrypt(inputTextArea.getText());
                outTextArea.setText(cipherText);
            }
        });
        //copy Key
        copyKeyButton.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //copy Iv
        copyIvButton.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //copy output
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //popup
        //popup input
        JPopupMenu inputPopup = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearInputItem = new JMenuItem(Constants.Description.CLEAR);
        inputPopup.add(pasteInputItem);
        inputPopup.add(clearInputItem);
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    inputPopup.show(inputTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteInputItem.addActionListener(e -> {
            inputTextArea.paste();
        });
        clearInputItem.addActionListener(e -> {
            inputTextArea.setText(Constants.Description.EMPTY);
            outTextArea.setText(Constants.Description.EMPTY);
        });
        //popup key
        JPopupMenu keyPopup = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearKeyItem = new JMenuItem(Constants.Description.CLEAR);
        JMenuItem generateKeyItem = new JMenuItem(Constants.Description.GENERATE_KEY);
        JMenuItem copyAllKeyItem = new JMenuItem(Constants.Description.COPY_ALL);
        keyPopup.add(pasteKeyItem);
        keyPopup.add(clearKeyItem);
        keyPopup.add(generateKeyItem);
        keyPopup.add(copyAllKeyItem);
        keyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    keyPopup.show(keyTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteKeyItem.addActionListener(e -> {
            keyTextArea.paste();
        });
        clearKeyItem.addActionListener(e -> {
            keyTextArea.setText(Constants.Description.EMPTY);
        });
        generateKeyItem.addActionListener(e -> {
            if (absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)) {
                absSymmetricEncryption.instance((String) modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        copyAllKeyItem.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //popup iv
        //popup key
        JPopupMenu ivPopup = new JPopupMenu();
        JMenuItem pasteIvItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearIvItem = new JMenuItem(Constants.Description.CLEAR);
        JMenuItem generateIvItem = new JMenuItem(Constants.Description.GENERATE_IV);
        JMenuItem copyAllIvItem = new JMenuItem(Constants.Description.COPY_ALL);
        ivPopup.add(pasteIvItem);
        ivPopup.add(clearIvItem);
        ivPopup.add(generateIvItem);
        ivPopup.add(copyAllIvItem);
        ivTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    ivPopup.show(ivTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteIvItem.addActionListener(e -> {
            ivTextArea.paste();
        });
        clearIvItem.addActionListener(e -> {
            ivTextArea.setText(Constants.Description.EMPTY);
        });
        generateIvItem.addActionListener(e -> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        copyAllIvItem.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllOutputItem = new JMenuItem(Constants.Description.COPY_ALL);
        outputPopupMenu.add(copyAllOutputItem);
        outTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    outputPopupMenu.show(outTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllOutputItem.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
    }
}
