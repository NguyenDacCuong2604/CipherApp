package GUI;

import GUI.Component.CustomScrollBarUI;
import GUI.Component.ImageButton;
import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;
import model.ASysmmetricEncryption.CreateKeyRSA;
import model.ASysmmetricEncryption.RSA;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class ASymmetricEncryptGUI extends JFrame {
    private Point mouseDownCompCoords = null;
    AbsASymmetricEncryption aSymmetricEncryption;
    String keyEncrypt = Constants.Description.PUBLIC_KEY;
    String keyDecrypt = Constants.Description.PRIVATE_KEY;
    String[] methods;

    public ASymmetricEncryptGUI(AbsASymmetricEncryption aSymmetricEncryption, String[] methods) {
        this.methods = methods;
        this.aSymmetricEncryption = aSymmetricEncryption;
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        renderCustomTitleBar();

        JPanel panelBody = new JPanel();
        panelBody.setBackground(Color.WHITE);
        panelBody.setBorder(new LineBorder(Color.WHITE, 20));
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));

        //create Key
        renderCreateKey(panelBody);

        //cipher
        renderCipher(panelBody);

        this.add(panelBody, BorderLayout.CENTER);

        setFocusable(true);
        requestFocus();

        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderCipher(JPanel panelBody) {
        JPanel cipherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cipherPanel.setBackground(Color.WHITE);
        TitledBorder cipherBorder = BorderFactory.createTitledBorder(null, "Encryption and Decryption ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        cipherBorder.setBorder(new LineBorder(Color.BLACK, 1));
        cipherPanel.setBorder(cipherBorder);

        //render Encryption
        renderEncryption(cipherPanel);

        //render Line
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 550));
        cipherPanel.add(leftPanel);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 550));
        cipherPanel.add(linePanel);

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
        JLabel decryptLabel = new JLabel("RSA decryption");
        decryptLabel.setFont(new Font("Arial", Font.BOLD, 22));
        decryptNamePanel.add(decryptLabel);
        decryptionPanel.add(decryptNamePanel);

        //input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel("Enter Encrypted Text to decrypt");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputLabel);
        decryptionPanel.add(inputNamePanel);
        JTextArea inputTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Encrypted Text to decrypt", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);

        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setPreferredSize(new Dimension(400, 120));
        inputScrollPane.setForeground(Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(inputScrollPane);

        //Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        JLabel keyLabel = new JLabel("Enter Public/Private key");
        keyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(keyLabel);
        decryptionPanel.add(keyNamePanel);

        JTextArea keyTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Public/Private key", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);

        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setPreferredSize(new Dimension(400, 80));
        keyScrollPane.setForeground(Color.WHITE);
        keyScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(keyScrollPane);

        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel("Key Type: ");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typePanel.add(typeLabel);
        JRadioButton publicKeyRadioButton = new JRadioButton("Public Key");
        publicKeyRadioButton.setBackground(Color.WHITE);
        publicKeyRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        publicKeyRadioButton.setFocusable(false);
        typePanel.add(publicKeyRadioButton);
        typeLabel.add(new JLabel("  "));
        JRadioButton privateKeyRaidoButton = new JRadioButton("Private Key");
        privateKeyRaidoButton.setSelected(true);
        privateKeyRaidoButton.setBackground(Color.WHITE);
        privateKeyRaidoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        privateKeyRaidoButton.setFocusable(false);
        typePanel.add(privateKeyRaidoButton);
        decryptionPanel.add(typePanel);

        publicKeyRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    keyDecrypt = Constants.Description.PUBLIC_KEY;
                }
            }
        });
        privateKeyRaidoButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    keyDecrypt = Constants.Description.PRIVATE_KEY;
                }
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(privateKeyRaidoButton);
        buttonGroup.add(publicKeyRadioButton);

        //cipher type
        JPanel typeCipherNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typeCipherNamePanel.setBackground(Color.WHITE);
        JLabel typeCipherLabel = new JLabel("Select Cipher Type");
        typeCipherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeCipherNamePanel.add(typeCipherLabel);
        decryptionPanel.add(typeCipherNamePanel);

        JComboBox cipherTypeComboBox = new JComboBox(methods);
        cipherTypeComboBox.setPreferredSize(new Dimension(400, 34));
        cipherTypeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        cipherTypeComboBox.setBackground(Color.WHITE);
        cipherTypeComboBox.setFocusable(false);
        decryptionPanel.add(cipherTypeComboBox);

        //button decrypt
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setPreferredSize(new Dimension(100, 34));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptButton.setBackground(new Color(35, 128, 251));
        decryptButton.setForeground(Color.WHITE);
        decryptButton.setFocusable(false);
        decryptButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(decryptButton);
        decryptionPanel.add(buttonPanel);

        //output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        JLabel decryptOutputLabel = new JLabel("Decrypted Output");
        decryptOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(decryptOutputLabel);

        outputPanel.add(new JLabel(" "));

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");
        JButton copyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(22, 22));
        copyButton.setToolTipText("Copy Decrypted");
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setFocusable(false);
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        outputPanel.add(copyButton);

        decryptionPanel.add(outputPanel);

        JTextArea outTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Result goes here", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        outTextArea.setEditable(false);
        outTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        outTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        outTextArea.setLineWrap(true);
        outTextArea.setWrapStyleWord(true);

        JScrollPane outScrollPane = new JScrollPane(outTextArea);
        outScrollPane.setPreferredSize(new Dimension(400, 100));
        outScrollPane.setForeground(Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(outScrollPane);

        cipherPanel.add(decryptionPanel);

        //event
        decryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Encrypted Text to decrypt", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Public/Private key", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) cipherTypeComboBox.getSelectedItem());
                String encrypt = aSymmetricEncryption.decrypt(inputTextArea.getText(), keyDecrypt, keyTextArea.getText());
                outTextArea.setText(encrypt);
            }

        });

        //popup input
        JPopupMenu inputPopupMenu = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem("Paste");
        JMenuItem clearInputItem = new JMenuItem("Clear");
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
            inputTextArea.setText("");
            outTextArea.setText("");
        });

        //popup key
        JPopupMenu keyPopupMenu = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem("Paste");
        JMenuItem clearKeyItem = new JMenuItem("Clear");
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
            keyTextArea.setText("");
        });

        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        //event copy
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);

                // Tạo một JDialog cho toast
                JDialog toast = new JDialog();
                toast.setUndecorated(true);
                toast.setBackground(new Color(0, 0, 0, 0)); // Để làm cho nền trong suốt
                toast.setLayout(new BorderLayout());
                JLabel label = new JLabel("Copied Decrypted: " + textToCopy);
                label.setForeground(Color.BLACK);
                toast.add(label, BorderLayout.CENTER);
                toast.pack();
                Point frameLocation = getLocationOnScreen();
                int frameHeight = getHeight();
                int toastWidth = 300; // Độ rộng của toast
                int toastHeight = 25; // Độ cao của toast
                toast.setSize(toastWidth, toastHeight);
                int toastX = (int) (frameLocation.getX() + 30);
                int toastY = (int) (frameLocation.getY() + frameHeight - toastHeight);
                toast.setLocation(toastX, toastY);

                // Tự động ẩn toast sau 2 giây (2000 ms)
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toast.dispose();
                    }
                });

                timer.setRepeats(false);
                timer.start();

                toast.setVisible(true);
            }
        });
    }

    private void renderEncryption(JPanel cipherPanel) {
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setLayout(new BoxLayout(encryptionPanel, BoxLayout.Y_AXIS));


        //encrypt
        JPanel encryptNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 3));
        encryptNamePanel.setBackground(Color.WHITE);
        JLabel encryptLabel = new JLabel("RSA Encryption");
        encryptLabel.setFont(new Font("Arial", Font.BOLD, 22));
        encryptNamePanel.add(encryptLabel);
        encryptionPanel.add(encryptNamePanel);

        //input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel("Enter Plain Text to Encrypt");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputLabel);
        encryptionPanel.add(inputNamePanel);
        JTextArea inputTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Plain Text to Encrypt", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);

        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setPreferredSize(new Dimension(400, 120));
        inputScrollPane.setForeground(Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(inputScrollPane);

        //Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        keyNamePanel.setBackground(Color.WHITE);
        JLabel keyLabel = new JLabel("Enter Public/Private key");
        keyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(keyLabel);
        encryptionPanel.add(keyNamePanel);

        JTextArea keyTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Public/Private key", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        keyTextArea.setLineWrap(true);
        keyTextArea.setWrapStyleWord(true);

        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setPreferredSize(new Dimension(400, 80));
        keyScrollPane.setForeground(Color.WHITE);
        keyScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(keyScrollPane);

        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel("Key Type: ");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typePanel.add(typeLabel);
        JRadioButton publicKeyRadioButton = new JRadioButton("Public Key");
        publicKeyRadioButton.setSelected(true);
        publicKeyRadioButton.setBackground(Color.WHITE);
        publicKeyRadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        publicKeyRadioButton.setFocusable(false);
        typePanel.add(publicKeyRadioButton);
        typeLabel.add(new JLabel("  "));
        JRadioButton privateKeyRaidoButton = new JRadioButton("Private Key");
        privateKeyRaidoButton.setBackground(Color.WHITE);
        privateKeyRaidoButton.setFont(new Font("Arial", Font.PLAIN, 16));
        privateKeyRaidoButton.setFocusable(false);
        typePanel.add(privateKeyRaidoButton);
        encryptionPanel.add(typePanel);


        publicKeyRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    keyEncrypt = Constants.Description.PUBLIC_KEY;
                }
            }
        });
        privateKeyRaidoButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    keyEncrypt = Constants.Description.PRIVATE_KEY;
                }
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(privateKeyRaidoButton);
        buttonGroup.add(publicKeyRadioButton);

        //cipher type
        JPanel typeCipherNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        typeCipherNamePanel.setBackground(Color.WHITE);
        JLabel typeCipherLabel = new JLabel("Select Cipher Type");
        typeCipherLabel.setFont(new Font("Arial", Font.BOLD, 16));
        typeCipherNamePanel.add(typeCipherLabel);
        encryptionPanel.add(typeCipherNamePanel);

        JComboBox cipherTypeComboBox = new JComboBox(methods);
        cipherTypeComboBox.setPreferredSize(new Dimension(400, 34));
        cipherTypeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        cipherTypeComboBox.setBackground(Color.WHITE);
        cipherTypeComboBox.setFocusable(false);
        encryptionPanel.add(cipherTypeComboBox);

        //button encrypt
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setPreferredSize(new Dimension(100, 34));
        encryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptButton.setBackground(new Color(35, 128, 251));
        encryptButton.setForeground(Color.WHITE);
        encryptButton.setFocusable(false);
        encryptButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(encryptButton);
        encryptionPanel.add(buttonPanel);

        //output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        JLabel encryptOutputLabel = new JLabel("Encrypted Output");
        encryptOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(encryptOutputLabel);

        outputPanel.add(new JLabel(" "));

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");
        JButton copyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(22, 22));
        copyButton.setToolTipText("Copy Encrypted");
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setFocusable(false);
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        outputPanel.add(copyButton);

        encryptionPanel.add(outputPanel);

        JTextArea outTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Result goes here", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        outTextArea.setEditable(false);
        outTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        outTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        outTextArea.setLineWrap(true);
        outTextArea.setWrapStyleWord(true);

        JScrollPane outScrollPane = new JScrollPane(outTextArea);
        outScrollPane.setPreferredSize(new Dimension(400, 100));
        outScrollPane.setForeground(Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(outScrollPane);

        cipherPanel.add(encryptionPanel);

        //event
        encryptButton.addActionListener(e -> {
            if (inputTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Plain Text to Encrypt", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (keyTextArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Public/Private key", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                aSymmetricEncryption.instance((String) cipherTypeComboBox.getSelectedItem());
                String encrypt = aSymmetricEncryption.encrypt(inputTextArea.getText(), keyEncrypt, keyTextArea.getText());
                outTextArea.setText(encrypt);
            }

        });

        //popup input
        JPopupMenu inputPopupMenu = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem("Paste");
        JMenuItem clearInputItem = new JMenuItem("Clear");
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
            inputTextArea.setText("");
            outTextArea.setText("");
        });

        //popup key
        JPopupMenu keyPopupMenu = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem("Paste");
        JMenuItem clearKeyItem = new JMenuItem("Clear");
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
            keyTextArea.setText("");
        });

        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        //event copy
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
            if (!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
                // Tạo một JDialog cho toast
                JDialog toast = new JDialog();
                toast.setUndecorated(true);
                toast.setBackground(new Color(0, 0, 0, 0)); // Để làm cho nền trong suốt
                toast.setLayout(new BorderLayout());
                JLabel label = new JLabel("Copied Encrypted: " + textToCopy);
                label.setForeground(Color.BLACK);
                toast.add(label, BorderLayout.CENTER);
                toast.pack();
                Point frameLocation = getLocationOnScreen();
                int frameHeight = getHeight();
                int toastWidth = 300; // Độ rộng của toast
                int toastHeight = 25; // Độ cao của toast
                toast.setSize(toastWidth, toastHeight);
                int toastX = (int) (frameLocation.getX() + 30);
                int toastY = (int) (frameLocation.getY() + frameHeight - toastHeight);
                toast.setLocation(toastX, toastY);

                // Tự động ẩn toast sau 2 giây (2000 ms)
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toast.dispose();
                    }
                });

                timer.setRepeats(false);
                timer.start();

                toast.setVisible(true);
            }
        });
    }

    private void renderCreateKey(JPanel panelBody) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        TitledBorder keyBorder = BorderFactory.createTitledBorder(null, "Generate Keys ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        keyBorder.setBorder(new LineBorder(Color.BLACK, 1));
        inputPanel.setBorder(keyBorder);

        CreateKeyRSA createKeyRSA = new CreateKeyRSA();
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        keyPanel.setBackground(Color.WHITE);

        JTextField publicKeyTextArea = new JTextField();
        publicKeyTextArea.setBackground(Color.WHITE);
        publicKeyTextArea.setBorder(new LineBorder(new Color(122, 138, 153), 1));
        publicKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        publicKeyTextArea.setPreferredSize(new Dimension(290, 38));
        publicKeyTextArea.setEditable(false);

        //popup publicKey
        JPopupMenu publicKeyPopup = new JPopupMenu();
        JMenuItem copyAllMenuItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        JLabel publicKeyLabel = new JLabel("Public Key");
        publicKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyPanel.add(publicKeyLabel);
        keyPanel.add(publicKeyTextArea);

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");

        JButton copyPublicKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        copyPublicKeyButton.setPreferredSize(new Dimension(25, 25));
        copyPublicKeyButton.setToolTipText("Copy PublicKey");
        copyPublicKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyPublicKeyButton.setFocusable(false);
        copyPublicKeyButton.setBorderPainted(false);
        copyPublicKeyButton.setBackground(Color.WHITE);
        keyPanel.add(copyPublicKeyButton);

        //event
        copyPublicKeyButton.addActionListener(e -> {
            String textToCopy = publicKeyTextArea.getText();
if(!textToCopy.isEmpty()) {
    // Copy the text to the clipboard
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection selection = new StringSelection(textToCopy);
    clipboard.setContents(selection, null);
    // Tạo một JDialog cho toast
    JDialog toast = new JDialog();
    toast.setUndecorated(true);
    toast.setBackground(new Color(0, 0, 0, 0)); // Để làm cho nền trong suốt
    toast.setLayout(new BorderLayout());
    JLabel label = new JLabel("Copied PublicKey: " + textToCopy);
    label.setForeground(Color.BLACK);
    toast.add(label, BorderLayout.CENTER);
    toast.pack();
    Point frameLocation = getLocationOnScreen();
    int frameHeight = getHeight();
    int toastWidth = 300; // Độ rộng của toast
    int toastHeight = 25; // Độ cao của toast
    toast.setSize(toastWidth, toastHeight);
    int toastX = (int) (frameLocation.getX() + 30);
    int toastY = (int) (frameLocation.getY() + frameHeight - toastHeight);
    toast.setLocation(toastX, toastY);

    // Tự động ẩn toast sau 2 giây (2000 ms)
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            toast.dispose();
        }
    });

    timer.setRepeats(false);
    timer.start();

    toast.setVisible(true);
}
        });

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 42));
        keyPanel.add(leftPanel);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 42));
        keyPanel.add(linePanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(12, 42));
        keyPanel.add(rightPanel);

        JTextField privateKeyTextArea = new JTextField();
        privateKeyTextArea.setBackground(Color.WHITE);
        privateKeyTextArea.setBorder(new LineBorder(new Color(122, 138, 153), 1));
        privateKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        privateKeyTextArea.setPreferredSize(new Dimension(285, 38));
        privateKeyTextArea.setEditable(false);

        //popup private
        JPopupMenu privateKeyPopup = new JPopupMenu();
        JMenuItem copyAllPrivateMenuItem = new JMenuItem("Copy All");
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
            if(!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
            }
        });

        JLabel privateKeyLabel = new JLabel("Private Key");
        privateKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyPanel.add(privateKeyLabel);
        keyPanel.add(privateKeyTextArea);

        JButton copyPrivateKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        copyPrivateKeyButton.setToolTipText("Copy PrivateKey");
        copyPrivateKeyButton.setPreferredSize(new Dimension(25, 25));
        copyPrivateKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyPrivateKeyButton.setFocusable(false);
        copyPrivateKeyButton.setBorderPainted(false);
        copyPrivateKeyButton.setBackground(Color.WHITE);
        keyPanel.add(copyPrivateKeyButton);
        //event
        copyPrivateKeyButton.addActionListener(e -> {
            String textToCopy = privateKeyTextArea.getText();
            if(!textToCopy.isEmpty()) {
                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
                // Tạo một JDialog cho toast
                JDialog toast = new JDialog();
                toast.setUndecorated(true);
                toast.setBackground(new Color(0, 0, 0, 0)); // Để làm cho nền trong suốt
                toast.setLayout(new BorderLayout());
                JLabel label = new JLabel("Copied Private: " + textToCopy);
                label.setForeground(Color.BLACK);
                toast.add(label, BorderLayout.CENTER);
                toast.pack();
                Point frameLocation = getLocationOnScreen();
                int frameHeight = getHeight();
                int toastWidth = 300; // Độ rộng của toast
                int toastHeight = 25; // Độ cao của toast
                toast.setSize(toastWidth, toastHeight);
                int toastX = (int) (frameLocation.getX() + 30);
                int toastY = (int) (frameLocation.getY() + frameHeight - toastHeight);
                toast.setLocation(toastX, toastY);

                // Tự động ẩn toast sau 2 giây (2000 ms)
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toast.dispose();
                    }
                });

                timer.setRepeats(false);
                timer.start();

                toast.setVisible(true);
            }
        });


        inputPanel.add(keyPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        buttonPanel.setBackground(Color.WHITE);

        JComboBox sizeKeyCombobox = new JComboBox(Constants.List_Size.SIZE_RSA);
        sizeKeyCombobox.setSelectedIndex(1);
        sizeKeyCombobox.setFocusable(false);
        sizeKeyCombobox.setLightWeightPopupEnabled(false);
        sizeKeyCombobox.setFont(new Font("Arial", Font.BOLD, 16));
        sizeKeyCombobox.setBackground(Color.WHITE);
        sizeKeyCombobox.setPreferredSize(new Dimension(100, 34));

        buttonPanel.add(sizeKeyCombobox);

        JButton generateKeysButton = new JButton("Generate Keys");
        generateKeysButton.setPreferredSize(new Dimension(150, 34));
        generateKeysButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateKeysButton.setBackground(new Color(35, 128, 251));
        generateKeysButton.setForeground(Color.WHITE);
        generateKeysButton.setFocusable(false);
        generateKeysButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(generateKeysButton);

        //event
        generateKeysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        inputPanel.add(buttonPanel);

        panelBody.add(inputPanel);
    }

    private void renderCustomTitleBar() {
        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(Color.WHITE);

        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        algorithmPanel.setBackground(Color.WHITE);
//        algorithmPanel.setPreferredSize(new Dimension(600, 50));

        JLabel nameLabel = new JLabel("Algorithm");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        algorithmPanel.add(nameLabel);

        JLabel algorithmLabel = new JLabel(aSymmetricEncryption.name);
        algorithmLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        algorithmPanel.add(algorithmLabel);

        customTitleBar.add(algorithmPanel, BorderLayout.WEST);


        ImageButton minimizeButton = new ImageButton(new ImageIcon("assets/Images/minimize-sign-black.png"), 25, 25);
        minimizeButton.setToolTipText("Minimize");
        ImageButton closeButton = new ImageButton(new ImageIcon("assets/Images/close-black.png"), 25, 25);
        closeButton.setToolTipText("Close");
        //event
        minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED));
        closeButton.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            currentFrame.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(minimizeButton);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(closeButton);

        customTitleBar.add(buttonPanel, BorderLayout.EAST);

        customTitleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        customTitleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        this.add(customTitleBar, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
        AbsASymmetricEncryption rsa = new RSA();
        new ASymmetricEncryptGUI(rsa, Constants.List_Method.METHODS_RSA);
    }
}
