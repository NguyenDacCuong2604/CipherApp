package GUI;

import GUI.Component.*;
import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;
import model.ASysmmetricEncryption.CreateKeyRSA;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class ASymmetricEncryptGUI extends JFrame {
    AbsASymmetricEncryption aSymmetricEncryption;
    String keyEncrypt = Constants.Description.PUBLIC_KEY;
    String keyDecrypt = Constants.Description.PRIVATE_KEY;
    String[] methods;

    public ASymmetricEncryptGUI(AbsASymmetricEncryption aSymmetricEncryption, String[] methods) {
        this.methods = methods;
        this.aSymmetricEncryption = aSymmetricEncryption;
        this.setUndecorated(true);
        Image icon = Toolkit.getDefaultToolkit().getImage(Constants.Image.ICON);
        this.setIconImage(icon);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new LineBorder(Color.BLACK, 1));
        //titleBar
        new TitleBar(this, mainPanel, aSymmetricEncryption.name);
        //body
        JPanel panelBody = new JPanel();
        panelBody.setBackground(Color.WHITE);
        panelBody.setBorder(new LineBorder(Color.WHITE, 20));
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));
        //create Key
        renderCreateKey(panelBody);
        //cipher
        renderCipher(panelBody);
        mainPanel.add(panelBody, BorderLayout.CENTER);
        this.add(mainPanel);
        setFocusable(true);
        requestFocus();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderCipher(JPanel panelBody) {
        JPanel cipherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cipherPanel.setBackground(Color.WHITE);
        TitledBorder cipherBorder = BorderFactory.createTitledBorder(null, Constants.Description.ENCRYPT_DECRYPT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        cipherBorder.setBorder(new LineBorder(Color.BLACK, 1));
        cipherPanel.setBorder(cipherBorder);
        //render Encryption
        renderEncryption(cipherPanel);
        //left
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 550));
        cipherPanel.add(leftPanel);
        //line
        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 550));
        cipherPanel.add(linePanel);
        //right
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(12, 550));
        cipherPanel.add(rightPanel);
        //renderDecryption
        renderDecryption(cipherPanel);
        panelBody.add(cipherPanel);
    }

    private void renderDecryption(JPanel cipherPanel) {
        JPanel decryptionPanel = new JPanel();
        decryptionPanel.setLayout(new BoxLayout(decryptionPanel, BoxLayout.Y_AXIS));
        //decrypt
        JPanel decryptNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        decryptNamePanel.setBackground(Color.WHITE);
        JLabel decryptLabel = new JLabel(Constants.Description.RSA_DE);
        decryptLabel.setFont(new Font("Arial", Font.BOLD, 22));
        decryptNamePanel.add(decryptLabel);
        decryptionPanel.add(decryptNamePanel);
        //input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel(Constants.Description.ENTER_DECRYPT);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputLabel);
        decryptionPanel.add(inputNamePanel);
        JTextArea inputTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
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
        JScrollPane inputScrollPane = new CustomScrollPanel(inputTextArea, new Dimension(400, 120), Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(inputScrollPane);
        //Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        JLabel keyLabel = new JLabel(Constants.Description.ENTER_PUBLIC_PRIVATE);
        keyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(keyLabel);
        decryptionPanel.add(keyNamePanel);
        JTextArea keyTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_PUBLIC_PRIVATE, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane keyScrollPane = new CustomScrollPanel(keyTextArea, new Dimension(400, 80), Color.WHITE);
        keyScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(keyScrollPane);
        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel(Constants.Description.KEY_TYPE);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typePanel.add(typeLabel);
        JRadioButton publicKeyRadioButton = new CustomRadioButton(Constants.Description.PUBLIC_KEY, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        typePanel.add(publicKeyRadioButton);
        typeLabel.add(new JLabel(Constants.Description.BLANK));
        JRadioButton privateKeyRadioButton = new CustomRadioButton(Constants.Description.PRIVATE_KEY, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        privateKeyRadioButton.setSelected(true);
        typePanel.add(privateKeyRadioButton);
        decryptionPanel.add(typePanel);
        publicKeyRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                keyDecrypt = Constants.Description.PUBLIC_KEY;
            }
        });
        privateKeyRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                keyDecrypt = Constants.Description.PRIVATE_KEY;
            }
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(privateKeyRadioButton);
        buttonGroup.add(publicKeyRadioButton);
        //cipher type
        JPanel typeCipherNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typeCipherNamePanel.setBackground(Color.WHITE);
        JLabel typeCipherLabel = new JLabel(Constants.Description.TYPE_CIPHER);
        typeCipherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeCipherNamePanel.add(typeCipherLabel);
        decryptionPanel.add(typeCipherNamePanel);
        //combobox
        JComboBox cipherTypeComboBox = new CustomComboBox(methods, new Dimension(400, 34), new Font("Arial", Font.BOLD, 16), Color.WHITE);
        decryptionPanel.add(cipherTypeComboBox);
        //button decrypt
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton decryptButton = new CustomButton(Constants.Description.DECRYPT, new Dimension(100, 34), new Font("Arial", Font.BOLD, 14), new Color(35, 128, 251), Color.WHITE);
        buttonPanel.add(decryptButton);
        decryptionPanel.add(buttonPanel);
        //output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        JLabel decryptOutputLabel = new JLabel(Constants.Description.DECRYPT_OUTPUT);
        decryptOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(decryptOutputLabel);
        outputPanel.add(new JLabel(Constants.Description.BLANK));
        //copy
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JButton copyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyButton.setToolTipText(Constants.Description.COPY);
        outputPanel.add(copyButton);
        decryptionPanel.add(outputPanel);
        JTextArea outTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
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
        JScrollPane outScrollPane = new CustomScrollPanel(outTextArea, new Dimension(400, 100), Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(outScrollPane);
        cipherPanel.add(decryptionPanel);
        //event
        decryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Constants.Description.ENTER_DECRYPT, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Constants.Description.ENTER_PUBLIC_PRIVATE, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) cipherTypeComboBox.getSelectedItem());
                String encrypt = aSymmetricEncryption.decrypt(inputTextArea.getText(), keyDecrypt, keyTextArea.getText());
                outTextArea.setText(encrypt);
            }
        });
        //popup input
        JPopupMenu inputPopupMenu = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearInputItem = new JMenuItem(Constants.Description.CLEAR);
        inputPopupMenu.add(pasteInputItem);
        inputPopupMenu.add(clearInputItem);
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    inputPopupMenu.show(inputTextArea, e.getX(), e.getY());
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
        JPopupMenu keyPopupMenu = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearKeyItem = new JMenuItem(Constants.Description.CLEAR);
        keyPopupMenu.add(pasteKeyItem);
        keyPopupMenu.add(clearKeyItem);
        keyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    keyPopupMenu.show(keyTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteKeyItem.addActionListener(e -> {
            keyTextArea.paste();
        });
        clearKeyItem.addActionListener(e -> {
            keyTextArea.setText(Constants.Description.EMPTY);
        });
        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem(Constants.Description.COPY_ALL);
        outputPopupMenu.add(copyAllItem);
        outTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    outputPopupMenu.show(outTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllItem.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //event copy
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
    }

    private void renderEncryption(JPanel cipherPanel) {
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setLayout(new BoxLayout(encryptionPanel, BoxLayout.Y_AXIS));
        //encrypt
        JPanel encryptNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        encryptNamePanel.setBackground(Color.WHITE);
        JLabel encryptLabel = new JLabel(Constants.Description.RSA_EN);
        encryptLabel.setFont(new Font("Arial", Font.BOLD, 22));
        encryptNamePanel.add(encryptLabel);
        encryptionPanel.add(encryptNamePanel);
        //input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel(Constants.Description.ENTER_ENCRYPT);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputLabel);
        encryptionPanel.add(inputNamePanel);
        JTextArea inputTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
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
        JScrollPane inputScrollPane = new CustomScrollPanel(inputTextArea, new Dimension(400, 120), Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(inputScrollPane);
        //Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        JLabel keyLabel = new JLabel(Constants.Description.ENTER_PUBLIC_PRIVATE);
        keyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(keyLabel);
        encryptionPanel.add(keyNamePanel);
        JTextArea keyTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_PUBLIC_PRIVATE, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        JScrollPane keyScrollPane = new CustomScrollPanel(keyTextArea, new Dimension(400, 80), Color.WHITE);
        keyScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(keyScrollPane);
        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel(Constants.Description.KEY_TYPE);
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typePanel.add(typeLabel);
        JRadioButton publicKeyRadioButton = new CustomRadioButton(Constants.Description.PUBLIC_KEY, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        publicKeyRadioButton.setSelected(true);
        typePanel.add(publicKeyRadioButton);
        typeLabel.add(new JLabel(Constants.Description.BLANK));
        JRadioButton privateKeyRadioButton = new CustomRadioButton(Constants.Description.PRIVATE_KEY, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        typePanel.add(privateKeyRadioButton);
        encryptionPanel.add(typePanel);
        publicKeyRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                keyEncrypt = Constants.Description.PUBLIC_KEY;
            }
        });
        privateKeyRadioButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                keyEncrypt = Constants.Description.PRIVATE_KEY;
            }
        });
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(privateKeyRadioButton);
        buttonGroup.add(publicKeyRadioButton);
        //cipher type
        JPanel typeCipherNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typeCipherNamePanel.setBackground(Color.WHITE);
        JLabel typeCipherLabel = new JLabel(Constants.Description.TYPE_CIPHER);
        typeCipherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeCipherNamePanel.add(typeCipherLabel);
        encryptionPanel.add(typeCipherNamePanel);
        //combobox
        JComboBox cipherTypeComboBox = new CustomComboBox(methods, new Dimension(400, 34), new Font("Arial", Font.BOLD, 16), Color.WHITE);
        encryptionPanel.add(cipherTypeComboBox);
        //button encrypt
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton encryptButton = new CustomButton(Constants.Description.ENCRYPT, new Dimension(100, 34), new Font("Arial", Font.BOLD, 14), new Color(35, 128, 251), Color.WHITE);
        buttonPanel.add(encryptButton);
        encryptionPanel.add(buttonPanel);
        //output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        JLabel encryptOutputLabel = new JLabel(Constants.Description.ENCRYPT_OUTPUT);
        encryptOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(encryptOutputLabel);
        outputPanel.add(new JLabel(Constants.Description.BLANK));
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JButton copyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)), new Dimension(22, 22), Color.WHITE);
        copyButton.setToolTipText(Constants.Description.COPY);
        outputPanel.add(copyButton);
        encryptionPanel.add(outputPanel);
        JTextArea outTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 16)) {
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
        JScrollPane outScrollPane = new CustomScrollPanel(outTextArea, new Dimension(400, 100), Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(outScrollPane);
        cipherPanel.add(encryptionPanel);
        //event
        encryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Constants.Description.ENTER_ENCRYPT, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, Constants.Description.ENTER_PUBLIC_PRIVATE, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) cipherTypeComboBox.getSelectedItem());
                String encrypt = aSymmetricEncryption.encrypt(inputTextArea.getText(), keyEncrypt, keyTextArea.getText());
                outTextArea.setText(encrypt);
            }
        });
        //popup input
        JPopupMenu inputPopupMenu = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearInputItem = new JMenuItem(Constants.Description.CLEAR);
        inputPopupMenu.add(pasteInputItem);
        inputPopupMenu.add(clearInputItem);
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    inputPopupMenu.show(inputTextArea, e.getX(), e.getY());
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
        JPopupMenu keyPopupMenu = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearKeyItem = new JMenuItem(Constants.Description.CLEAR);
        keyPopupMenu.add(pasteKeyItem);
        keyPopupMenu.add(clearKeyItem);
        keyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    keyPopupMenu.show(keyTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteKeyItem.addActionListener(e -> {
            keyTextArea.paste();
        });
        clearKeyItem.addActionListener(e -> {
            keyTextArea.setText(Constants.Description.EMPTY);
        });
        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem(Constants.Description.COPY_ALL);
        outputPopupMenu.add(copyAllItem);
        outTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    outputPopupMenu.show(outTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllItem.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        //event copy
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
    }

    private void renderCreateKey(JPanel panelBody) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        TitledBorder keyBorder = BorderFactory.createTitledBorder(null, Constants.Description.GENERATE_KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        keyBorder.setBorder(new LineBorder(Color.BLACK, 1));
        inputPanel.setBorder(keyBorder);
        //create KEY
        CreateKeyRSA createKeyRSA = new CreateKeyRSA();
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        keyPanel.setBackground(Color.WHITE);
        //text field
        JTextField publicKeyTextArea = new JTextField();
        publicKeyTextArea.setBackground(Color.WHITE);
        publicKeyTextArea.setBorder(new LineBorder(new Color(122, 138, 153), 1));
        publicKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        publicKeyTextArea.setPreferredSize(new Dimension(290, 38));
        publicKeyTextArea.setEditable(false);
        //popup publicKey
        JPopupMenu publicKeyPopup = new JPopupMenu();
        JMenuItem copyAllMenuItem = new JMenuItem(Constants.Description.COPY_ALL);
        publicKeyPopup.add(copyAllMenuItem);
        publicKeyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    publicKeyPopup.show(publicKeyTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllMenuItem.addActionListener(e -> {
            String textToCopy = publicKeyTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        JLabel publicKeyLabel = new JLabel(Constants.Description.PUBLIC_KEY);
        publicKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyPanel.add(publicKeyLabel);
        keyPanel.add(publicKeyTextArea);
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JButton copyPublicKeyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)), new Dimension(25, 25), Color.WHITE);
        copyPublicKeyButton.setToolTipText(Constants.Description.COPY);
        keyPanel.add(copyPublicKeyButton);
        //event
        copyPublicKeyButton.addActionListener(e -> {
            String textToCopy = publicKeyTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        //left
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 42));
        keyPanel.add(leftPanel);
        //line
        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 42));
        keyPanel.add(linePanel);
        //right
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(12, 42));
        keyPanel.add(rightPanel);
        //private
        JTextField privateKeyTextArea = new JTextField();
        privateKeyTextArea.setBackground(Color.WHITE);
        privateKeyTextArea.setBorder(new LineBorder(new Color(122, 138, 153), 1));
        privateKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        privateKeyTextArea.setPreferredSize(new Dimension(285, 38));
        privateKeyTextArea.setEditable(false);
        //popup private
        JPopupMenu privateKeyPopup = new JPopupMenu();
        JMenuItem copyAllPrivateMenuItem = new JMenuItem(Constants.Description.COPY_ALL);
        privateKeyPopup.add(copyAllPrivateMenuItem);
        privateKeyTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    privateKeyPopup.show(privateKeyTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllPrivateMenuItem.addActionListener(e -> {
            String textToCopy = privateKeyTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
            }
        });
        JLabel privateKeyLabel = new JLabel(Constants.Description.PRIVATE_KEY);
        privateKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyPanel.add(privateKeyLabel);
        keyPanel.add(privateKeyTextArea);
        //button
        JButton copyPrivateKeyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)), new Dimension(25, 25), Color.WHITE);
        copyPrivateKeyButton.setToolTipText("Copy PrivateKey");
        keyPanel.add(copyPrivateKeyButton);
        //event
        copyPrivateKeyButton.addActionListener(e -> {
            String textToCopy = privateKeyTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                model.Toolkit.copy(textToCopy);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + textToCopy);
                showToast.showToast(1000);
            }
        });
        inputPanel.add(keyPanel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        buttonPanel.setBackground(Color.WHITE);
        //combobox
        JComboBox sizeKeyCombobox = new CustomComboBox(Constants.List_Size.SIZE_RSA, new Dimension(100, 34), new Font("Arial", Font.BOLD, 16), Color.WHITE);
        sizeKeyCombobox.setSelectedIndex(1);
        buttonPanel.add(sizeKeyCombobox);
        JButton generateKeysButton = new CustomButton(Constants.Description.GENERATE_KEY, new Dimension(150, 34), new Font("Arial", Font.BOLD, 14), new Color(35, 128, 251), Color.WHITE);
        buttonPanel.add(generateKeysButton);
        //event
        generateKeysButton.addActionListener(e -> {
            if ((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT515)) {
                createKeyRSA.createKey(515);
            } else if ((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT1024)) {
                createKeyRSA.createKey(1024);
            } else if ((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT2048)) {
                createKeyRSA.createKey(2048);
            } else if ((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT4096)) {
                createKeyRSA.createKey(4096);
            } else if ((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT3072)) {
                createKeyRSA.createKey(3072);
            }
            publicKeyTextArea.setText(createKeyRSA.getPublicKey());
            privateKeyTextArea.setText(createKeyRSA.getPrivateKey());
            repaint();
        });
        inputPanel.add(buttonPanel);
        panelBody.add(inputPanel);
    }
}
