package GUI;

import GUI.Component.CustomScrollBarUI;
import GUI.Component.ImageButton;
import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;
import model.ASysmmetricEncryption.CreateKeyRSA;
import model.ASysmmetricEncryption.RSA;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ASymmetricEncryptGUI extends JFrame {
    private Point mouseDownCompCoords = null;
    AbsASymmetricEncryption aSymmetricEncryption;
    public ASymmetricEncryptGUI(AbsASymmetricEncryption aSymmetricEncryption, String[] methods){
        this.aSymmetricEncryption = aSymmetricEncryption;
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        renderCustomTitleBar();

        JPanel panelBody = new JPanel();
        panelBody.setBorder(new LineBorder(Color.WHITE, 20));
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));

        //create Key
        renderCreateKey(panelBody);
        
        this.add(panelBody, BorderLayout.CENTER);

        setFocusable(true);
        requestFocus();

        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderCreateKey(JPanel panelBody) {
        CreateKeyRSA createKeyRSA = new CreateKeyRSA();


        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4,0));
        keyPanel.setBackground(Color.WHITE);

        JTextField publicKeyTextArea = new JTextField();
        publicKeyTextArea.setBackground(Color.WHITE);
        publicKeyTextArea.setBorder(new LineBorder(Color.BLACK, 2));
        publicKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        publicKeyTextArea.setPreferredSize(new Dimension(240, 38));
        publicKeyTextArea.setEditable(false);

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

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setPreferredSize(new Dimension(16, 42));
        keyPanel.add(leftPanel);

        JPanel linePanel = new JPanel();
        linePanel.setBackground(Color.BLACK);
        linePanel.setPreferredSize(new Dimension(3, 42));
        keyPanel.add(linePanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(16, 42));
        keyPanel.add(rightPanel);

        JTextField privateKeyTextArea = new JTextField();
        privateKeyTextArea.setBackground(Color.WHITE);
        privateKeyTextArea.setBorder(new LineBorder(Color.BLACK, 3));
        privateKeyTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        privateKeyTextArea.setPreferredSize(new Dimension(240, 38));
        privateKeyTextArea.setEditable(false);

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

            // Copy the text to the clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });


        panelBody.add(keyPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
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
                if((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT515)){
                    createKeyRSA.createKey(515);
                }
                else if((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT1024)){
                    createKeyRSA.createKey(1024);
                }
                else if((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT2048)){
                    createKeyRSA.createKey(2048);
                }
                else if((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT4096)){
                    createKeyRSA.createKey(4096);
                }
                else if((sizeKeyCombobox.getSelectedItem()).equals(Constants.Size.BIT3072)){
                    createKeyRSA.createKey(3072);
                }
                publicKeyTextArea.setText(createKeyRSA.getPublicKey());
                privateKeyTextArea.setText(createKeyRSA.getPrivateKey());
                repaint();
            }
        });

        panelBody.add(buttonPanel);
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
