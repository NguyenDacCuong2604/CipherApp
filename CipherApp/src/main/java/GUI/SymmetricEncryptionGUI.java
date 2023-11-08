package GUI;

import GUI.Component.CustomScrollBarUI;
import GUI.Component.ImageButton;
import constant.Constants;
import model.SysmmetricEncryption.AES;
import model.SysmmetricEncryption.AbsSymmetricEncryption;
import model.SysmmetricEncryption.DES;
import screens.SymmetricEncryptScreen;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

public class SymmetricEncryptionGUI extends JFrame {
    private Point mouseDownCompCoords = null;
    AbsSymmetricEncryption absSymmetricEncryptionEncrypt, absSymmetricEncryptionDecrypt;
    String[] methods;
    public SymmetricEncryptionGUI(AbsSymmetricEncryption absSymmetricEncryptionEncrypt, AbsSymmetricEncryption absSymmetricEncryptionDecrypt, String[] methods){
        this.absSymmetricEncryptionEncrypt = absSymmetricEncryptionEncrypt;
        this.absSymmetricEncryptionDecrypt = absSymmetricEncryptionEncrypt;
        this.methods = methods;
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        //titleBar
        renderCustomTitleBar();

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.WHITE, 15));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel panelBody = new JPanel();
        panelBody.setBackground(Color.WHITE);
        TitledBorder cipherBorder = BorderFactory.createTitledBorder(null, "Encryption and Decryption ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        cipherBorder.setBorder(new LineBorder(Color.BLACK, 1));
        panelBody.setBorder(cipherBorder);

        //renderEncryption
        renderEncryption(panelBody, absSymmetricEncryptionEncrypt);

        //renderLine
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(12, 600));
        panelBody.add(leftPanel);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 600));
        panelBody.add(linePanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(12, 600));
        panelBody.add(rightPanel);

        //renderDecryption
        renderDecryption(panelBody, absSymmetricEncryptionDecrypt);

        panel.add(panelBody);

        this.add(panel, BorderLayout.CENTER);

        setFocusable(true);
        requestFocus();

        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderDecryption(JPanel panel, AbsSymmetricEncryption absSymmetricEncryption) {
        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");
        JPanel decryptionPanel = new JPanel();
        decryptionPanel.setBackground(Color.WHITE);
        decryptionPanel.setLayout(new BoxLayout(decryptionPanel, BoxLayout.Y_AXIS));

        //title
        JPanel titleNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0 ,3));
        titleNamePanel.setBackground(Color.WHITE);

        JLabel titleNameLabel = new JLabel("Decryption");
        titleNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleNamePanel.add(titleNameLabel);
        decryptionPanel.add(titleNamePanel);
        //name input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);

        JLabel inputNameLabel = new JLabel("Enter Text to Decrypted");
        inputNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputNameLabel);
        decryptionPanel.add(inputNamePanel);
        //input
        JTextArea inputTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Text to Decrypt", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);

        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputScrollPane.setPreferredSize(new Dimension(450, 160));
        inputScrollPane.setForeground(Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(inputScrollPane);
        //select mode
        JPanel selectModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        selectModePanel.setBackground(Color.WHITE);

        JLabel selectModeLabel = new JLabel("Select Mode");
        selectModeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectModePanel.add(selectModeLabel);
        decryptionPanel.add(selectModePanel);
        //mode
        JComboBox modeComboBox = new JComboBox(methods);
        modeComboBox.setPreferredSize(new Dimension(450, 34));
        modeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        modeComboBox.setBackground(Color.WHITE);
        modeComboBox.setFocusable(false);
        decryptionPanel.add(modeComboBox);
        //Name Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0 ,5));
        keyNamePanel.setBackground(Color.WHITE);
        //name
        JLabel nameKeyLabel = new JLabel("Enter Secret Key");
        nameKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(nameKeyLabel);
        //button copy
        keyNamePanel.add(new JLabel(" "));
        JButton copyKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyKeyButton.setPreferredSize(new Dimension(22, 22));
        copyKeyButton.setToolTipText("Copy Key");
        copyKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyKeyButton.setFocusable(false);
        copyKeyButton.setBorderPainted(false);
        copyKeyButton.setBackground(Color.WHITE);
        keyNamePanel.add(copyKeyButton);
        keyNamePanel.add(new JLabel("  "));
        //size
        if(absSymmetricEncryption.name.equals(Constants.Cipher.AES)){
            JPanel linePanel = new JPanel();
            linePanel.setBackground(Color.BLACK);
            linePanel.setPreferredSize(new Dimension(2, 22));
            keyNamePanel.add(linePanel);

            JLabel sizeLabel = new JLabel(" Size ");
            sizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            keyNamePanel.add(sizeLabel);

            JComboBox sizesKeyComboBox = new JComboBox(Constants.List_Size.SIZE_AES);
            sizesKeyComboBox.setPreferredSize(new Dimension(85, 34));
            sizesKeyComboBox.setBackground(Color.WHITE);
            sizesKeyComboBox.setFont(new Font("Arial", Font.BOLD, 16));
            sizesKeyComboBox.setFocusable(false);
            keyNamePanel.add(sizesKeyComboBox);
            keyNamePanel.add(new JLabel("  "));
            //event
            sizesKeyComboBox.addActionListener(e -> {
                JComboBox cb = (JComboBox) e.getSource();
                String method = (String)cb.getSelectedItem();
                if(method.contains(Constants.Size.BITS128)){
                    absSymmetricEncryption.size = 16;
                }
                else if (method.contains(Constants.Size.BITS192)){
                    absSymmetricEncryption.size = 24;
                }
                else if (method.contains(Constants.Size.BITS256)){
                    absSymmetricEncryption.size = 32;
                }
                revalidate();
            });
        }

        //generate Key
        JButton generateKeyButton = new JButton("Generate Key");
        generateKeyButton.setPreferredSize(new Dimension(140, 34));
        generateKeyButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateKeyButton.setBackground(new Color(35, 128, 251));
        generateKeyButton.setForeground(Color.WHITE);
        generateKeyButton.setFocusable(false);
        generateKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        keyNamePanel.add(generateKeyButton);

        decryptionPanel.add(keyNamePanel);
        //key
        JTextField keyTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Secret Key", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        keyTextArea.setPreferredSize(new Dimension(450, 34));

        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setForeground(Color.WHITE);
        decryptionPanel.add(keyScrollPane);

        //Name iv
        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        ivPanel.setBackground(Color.WHITE);
        //name iv
        JLabel ivLabel = new JLabel("Enter IV (Optional)");
        ivLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ivPanel.add(ivLabel);

        //button copy
        ivPanel.add(new JLabel(" "));
        JButton copyIvButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyIvButton.setPreferredSize(new Dimension(22, 22));
        copyIvButton.setToolTipText("Copy IV");
        copyIvButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyIvButton.setFocusable(false);
        copyIvButton.setBorderPainted(false);
        copyIvButton.setBackground(Color.WHITE);
        ivPanel.add(copyIvButton);
        ivPanel.add(new JLabel("   "));

        //generate iv
        JButton generateIvButton = new JButton("Generate IV");
        generateIvButton.setPreferredSize(new Dimension(120, 34));
        generateIvButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateIvButton.setBackground(new Color(35, 128, 251));
        generateIvButton.setForeground(Color.WHITE);
        generateIvButton.setFocusable(false);
        generateIvButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                    g.drawString("Enter Initialization Vector", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        ivTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        ivTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        ivTextArea.setPreferredSize(new Dimension(450, 34));

        JScrollPane ivScrollPane = new JScrollPane(ivTextArea);
        ivScrollPane.setForeground(Color.WHITE);
        ivScrollPane.setVisible(false);

        decryptionPanel.add(ivScrollPane);

        //button
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

        //name output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
        //name
        JLabel nameOutputLabel = new JLabel("Encrypted Output ");
        nameOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(nameOutputLabel);

        outputPanel.add(new JLabel(" "));


        JButton copyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(22, 22));
        copyButton.setToolTipText("Copy Decrypted");
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setFocusable(false);
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        outputPanel.add(copyButton);

        decryptionPanel.add(outputPanel);

        //output
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
        outScrollPane.setPreferredSize(new Dimension(450, 160));
        outScrollPane.setForeground(Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        decryptionPanel.add(outScrollPane);

        panel.add(decryptionPanel);

        //event

        //combobox
        modeComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String)cb.getSelectedItem();
            if(method.contains(Constants.Mode.ECB)||method.contains(Constants.Cipher.HILL)||method.contains(Constants.Cipher.VIGENERE)){
                ivPanel.setVisible(false);
                ivScrollPane.setVisible(false);
            }
            else {
                ivPanel.setVisible(true);
                ivScrollPane.setVisible(true);
            }
            this.pack();
            this.setLocationRelativeTo(null);
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
        generateKeyButton.addActionListener(e->{
            if(absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);

        });

        //create iv
        generateIvButton.addActionListener(e ->{

            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);


        });

        //encrypt
        decryptButton.addActionListener(e -> {
            if(inputTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, "Empty Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(keyTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, "Empty Key!!! You can create a key by clicking on the createKey button", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String cipherText;
                absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
                SecretKey key = absSymmetricEncryption.convertKey(keyTextArea.getText());
                IvParameterSpec iv = absSymmetricEncryption.convertIv(ivTextArea.getText());
                cipherText = absSymmetricEncryption.decrypt(inputTextArea.getText());
                outTextArea.setText(cipherText);
            }
        });


        //copy Key
        copyKeyButton.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
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
                JLabel label = new JLabel("Copied Key: " + textToCopy);
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

        //copy Iv
        copyIvButton.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
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
                JLabel label = new JLabel("Copied IV: " + textToCopy);
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

        //copy output
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
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
                JLabel label = new JLabel("Copied Output: " + textToCopy);
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

        //popup
        //popup input
        JPopupMenu inputPopup = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem("Paste");
        JMenuItem clearInputItem = new JMenuItem("Clear");
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
        clearInputItem.addActionListener(e ->{
            inputTextArea.setText("");
            outTextArea.setText("");
        });
        //popup key
        JPopupMenu keyPopup = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem("Paste");
        JMenuItem clearKeyItem = new JMenuItem("Clear");
        JMenuItem generateKeyItem = new JMenuItem("GenerateKey");
        JMenuItem copyAllKeyItem = new JMenuItem("Copy All");
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
            keyTextArea.setText("");
        });
        generateKeyItem.addActionListener(e-> {
            if(absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        copyAllKeyItem.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });
        //popup iv
        //popup key
        JPopupMenu ivPopup = new JPopupMenu();
        JMenuItem pasteIvItem = new JMenuItem("Paste");
        JMenuItem clearIvItem = new JMenuItem("Clear");
        JMenuItem generateIvItem = new JMenuItem("GenerateKey");
        JMenuItem copyAllIvItem = new JMenuItem("Copy All");
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
            ivTextArea.setText("");
        });
        generateIvItem.addActionListener(e-> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        copyAllIvItem.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllOutputItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });
    }

    private void renderEncryption(JPanel panel, AbsSymmetricEncryption absSymmetricEncryption) {
        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");
        JPanel encryptionPanel = new JPanel();
        encryptionPanel.setBackground(Color.WHITE);
        encryptionPanel.setLayout(new BoxLayout(encryptionPanel, BoxLayout.Y_AXIS));

        //title
        JPanel titleNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0 ,3));
        titleNamePanel.setBackground(Color.WHITE);

        JLabel titleNameLabel = new JLabel("Encryption");
        titleNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleNamePanel.add(titleNameLabel);
        encryptionPanel.add(titleNamePanel);
        //name input
        JPanel inputNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        inputNamePanel.setBackground(Color.WHITE);

        JLabel inputNameLabel = new JLabel("Enter text to be Encrypted");
        inputNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputNamePanel.add(inputNameLabel);
        encryptionPanel.add(inputNamePanel);
        //input
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
        inputScrollPane.setPreferredSize(new Dimension(450, 160));
        inputScrollPane.setForeground(Color.WHITE);
        inputScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(inputScrollPane);
        //select mode
        JPanel selectModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        selectModePanel.setBackground(Color.WHITE);

        JLabel selectModeLabel = new JLabel("Select Mode");
        selectModeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectModePanel.add(selectModeLabel);
        encryptionPanel.add(selectModePanel);
        //mode
        JComboBox modeComboBox = new JComboBox(methods);
        modeComboBox.setPreferredSize(new Dimension(450, 34));
        modeComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        modeComboBox.setBackground(Color.WHITE);
        modeComboBox.setFocusable(false);
        encryptionPanel.add(modeComboBox);
        //Name Key
        JPanel keyNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0 ,5));
        keyNamePanel.setBackground(Color.WHITE);
            //name
        JLabel nameKeyLabel = new JLabel("Enter Secret Key");
        nameKeyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        keyNamePanel.add(nameKeyLabel);
            //button copy
        keyNamePanel.add(new JLabel(" "));
        JButton copyKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyKeyButton.setPreferredSize(new Dimension(22, 22));
        copyKeyButton.setToolTipText("Copy Key");
        copyKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyKeyButton.setFocusable(false);
        copyKeyButton.setBorderPainted(false);
        copyKeyButton.setBackground(Color.WHITE);
        keyNamePanel.add(copyKeyButton);
        keyNamePanel.add(new JLabel("  "));
            //size
        if(absSymmetricEncryption.name.equals(Constants.Cipher.AES)){
            JPanel linePanel = new JPanel();
            linePanel.setBackground(Color.BLACK);
            linePanel.setPreferredSize(new Dimension(2, 22));
            keyNamePanel.add(linePanel);

            JLabel sizeLabel = new JLabel(" Size ");
            sizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            keyNamePanel.add(sizeLabel);

            JComboBox sizesKeyComboBox = new JComboBox(Constants.List_Size.SIZE_AES);
            sizesKeyComboBox.setPreferredSize(new Dimension(85, 34));
            sizesKeyComboBox.setBackground(Color.WHITE);
            sizesKeyComboBox.setFont(new Font("Arial", Font.BOLD, 16));
            sizesKeyComboBox.setFocusable(false);
            keyNamePanel.add(sizesKeyComboBox);
            keyNamePanel.add(new JLabel("  "));
            //event
            sizesKeyComboBox.addActionListener(e -> {
                JComboBox cb = (JComboBox) e.getSource();
                String method = (String)cb.getSelectedItem();
                if(method.contains(Constants.Size.BITS128)){
                    absSymmetricEncryption.size = 16;
                }
                else if (method.contains(Constants.Size.BITS192)){
                    absSymmetricEncryption.size = 24;
                }
                else if (method.contains(Constants.Size.BITS256)){
                    absSymmetricEncryption.size = 32;
                }
                revalidate();
            });
        }

            //generate Key
        JButton generateKeyButton = new JButton("Generate Key");
        generateKeyButton.setPreferredSize(new Dimension(140, 34));
        generateKeyButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateKeyButton.setBackground(new Color(35, 128, 251));
        generateKeyButton.setForeground(Color.WHITE);
        generateKeyButton.setFocusable(false);
        generateKeyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        keyNamePanel.add(generateKeyButton);

        encryptionPanel.add(keyNamePanel);
        //key
        JTextField keyTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Secret Key", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        keyTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        keyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        keyTextArea.setPreferredSize(new Dimension(450, 34));

        JScrollPane keyScrollPane = new JScrollPane(keyTextArea);
        keyScrollPane.setForeground(Color.WHITE);
        encryptionPanel.add(keyScrollPane);

        //Name iv
        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        ivPanel.setBackground(Color.WHITE);
            //name iv
        JLabel ivLabel = new JLabel("Enter IV (Optional)");
        ivLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ivPanel.add(ivLabel);

            //button copy
        ivPanel.add(new JLabel(" "));
        JButton copyIvButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyIvButton.setPreferredSize(new Dimension(22, 22));
        copyIvButton.setToolTipText("Copy IV");
        copyIvButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyIvButton.setFocusable(false);
        copyIvButton.setBorderPainted(false);
        copyIvButton.setBackground(Color.WHITE);
        ivPanel.add(copyIvButton);
        ivPanel.add(new JLabel("   "));

            //generate iv
        JButton generateIvButton = new JButton("Generate IV");
        generateIvButton.setPreferredSize(new Dimension(120, 34));
        generateIvButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateIvButton.setBackground(new Color(35, 128, 251));
        generateIvButton.setForeground(Color.WHITE);
        generateIvButton.setFocusable(false);
        generateIvButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
                    g.drawString("Enter Initialization Vector", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        ivTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        ivTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        ivTextArea.setPreferredSize(new Dimension(450, 34));

        JScrollPane ivScrollPane = new JScrollPane(ivTextArea);
        ivScrollPane.setForeground(Color.WHITE);
        ivScrollPane.setVisible(false);

        encryptionPanel.add(ivScrollPane);

        //button
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

        //name output
        JPanel outputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        outputPanel.setBackground(Color.WHITE);
            //name
        JLabel nameOutputLabel = new JLabel("Encrypted Output ");
        nameOutputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        outputPanel.add(nameOutputLabel);

        outputPanel.add(new JLabel(" "));


        JButton copyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(22, 22));
        copyButton.setToolTipText("Copy Encrypted");
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setFocusable(false);
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        outputPanel.add(copyButton);

        encryptionPanel.add(outputPanel);

        //output
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
        outScrollPane.setPreferredSize(new Dimension(450, 160));
        outScrollPane.setForeground(Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        encryptionPanel.add(outScrollPane);

        panel.add(encryptionPanel);

        //event

        //combobox
        modeComboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String method = (String)cb.getSelectedItem();
            if(method.contains(Constants.Mode.ECB)||method.contains(Constants.Cipher.HILL)||method.contains(Constants.Cipher.VIGENERE)){
                ivPanel.setVisible(false);
                ivScrollPane.setVisible(false);
            }
            else {
                ivPanel.setVisible(true);
                ivScrollPane.setVisible(true);
            }
            this.pack();
            this.setLocationRelativeTo(null);
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
        generateKeyButton.addActionListener(e->{
                if(absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                    absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
                }
                String key = absSymmetricEncryption.createKey();
                keyTextArea.setText(key);

        });

        //create iv
        generateIvButton.addActionListener(e ->{

                String key = absSymmetricEncryption.createIv();
                ivTextArea.setText(key);


        });

        //encrypt
        encryptButton.addActionListener(e -> {
            if(inputTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, "Empty Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if(keyTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(SymmetricEncryptionGUI.this, "Empty Key!!! You can create a key by clicking on the createKey button", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String cipherText;
                absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
                SecretKey key = absSymmetricEncryption.convertKey(keyTextArea.getText());
                IvParameterSpec iv = absSymmetricEncryption.convertIv(ivTextArea.getText());
                cipherText = absSymmetricEncryption.encrypt(inputTextArea.getText());
                outTextArea.setText(cipherText);
            }
        });


        //copy Key
        copyKeyButton.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
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
                JLabel label = new JLabel("Copied Key: " + textToCopy);
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

        //copy Iv
        copyIvButton.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
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
                JLabel label = new JLabel("Copied IV: " + textToCopy);
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

        //copy output
        copyButton.addActionListener(e -> {
            String textToCopy = outTextArea.getText();
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
                JLabel label = new JLabel("Copied Output: " + textToCopy);
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

        //popup
            //popup input
        JPopupMenu inputPopup = new JPopupMenu();
        JMenuItem pasteInputItem = new JMenuItem("Paste");
        JMenuItem clearInputItem = new JMenuItem("Clear");
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
        clearInputItem.addActionListener(e ->{
            inputTextArea.setText("");
            outTextArea.setText("");
        });
            //popup key
        JPopupMenu keyPopup = new JPopupMenu();
        JMenuItem pasteKeyItem = new JMenuItem("Paste");
        JMenuItem clearKeyItem = new JMenuItem("Clear");
        JMenuItem generateKeyItem = new JMenuItem("GenerateKey");
        JMenuItem copyAllKeyItem = new JMenuItem("Copy All");
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
            keyTextArea.setText("");
        });
        generateKeyItem.addActionListener(e-> {
            if(absSymmetricEncryption.name.equals(Constants.Cipher.HILL) || absSymmetricEncryption.name.equals(Constants.Cipher.VIGENERE)){
                absSymmetricEncryption.instance((String)modeComboBox.getSelectedItem());
            }
            String key = absSymmetricEncryption.createKey();
            keyTextArea.setText(key);
        });
        copyAllKeyItem.addActionListener(e -> {
            String textToCopy = keyTextArea.getText();
                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
        });
            //popup iv
        //popup key
        JPopupMenu ivPopup = new JPopupMenu();
        JMenuItem pasteIvItem = new JMenuItem("Paste");
        JMenuItem clearIvItem = new JMenuItem("Clear");
        JMenuItem generateIvItem = new JMenuItem("GenerateKey");
        JMenuItem copyAllIvItem = new JMenuItem("Copy All");
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
            ivTextArea.setText("");
        });
        generateIvItem.addActionListener(e-> {
            String key = absSymmetricEncryption.createIv();
            ivTextArea.setText(key);
        });
        copyAllIvItem.addActionListener(e -> {
            String textToCopy = ivTextArea.getText();
            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

            //popup output
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllOutputItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

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

        JLabel algorithmLabel = new JLabel(absSymmetricEncryptionEncrypt.name);
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
}
