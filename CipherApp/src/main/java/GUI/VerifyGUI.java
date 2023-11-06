package GUI;

import GUI.Component.CustomScrollBarUI;
import GUI.Component.ImageButton;
import constant.Constants;
import model.ElectronicSignature.ElectronicSignature;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class VerifyGUI extends JFrame {
    private Point mouseDownCompCoords = null;
    ElectronicSignature electronicSignature;
    String filePath;
    JTextArea outTextArea;
    JTextField hashTextArea;

    public VerifyGUI(ElectronicSignature electronicSignature) {
        this.electronicSignature = electronicSignature;
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
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));
        panelBody.setBackground(Color.WHITE);
        TitledBorder cipherBorder = BorderFactory.createTitledBorder(null, "Verify File ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.PLAIN, 16), Color.BLACK);
        cipherBorder.setBorder(new LineBorder(Color.BLACK, 1));
        panelBody.setBorder(cipherBorder);

        //render Input
        renderFileInput(panelBody);
        //render input text
        renderTextInput(panelBody);
        //render select mode
        renderSelectMode(panelBody);
        //render button
        renderButton(panelBody);
        //render output
        renderOutput(panelBody);

        panel.add(panelBody);

        this.add(panel, BorderLayout.CENTER);

        setFocusable(true);
        requestFocus();

        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderOutput(JPanel panelBody) {
        //input name
        JPanel nameOutputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameOutputPanel.setBackground(Color.WHITE);
        JLabel outputLabel = new JLabel("Result");
        outputLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameOutputPanel.add(outputLabel);
        panelBody.add(nameOutputPanel);

        outTextArea = new JTextArea() {
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
        outTextArea.setFont(new Font("Arial", Font.BOLD, 24));
        outTextArea.setLineWrap(true);
        outTextArea.setWrapStyleWord(true);

        JScrollPane outScrollPane = new JScrollPane(outTextArea);
        outScrollPane.setPreferredSize(new Dimension(600, 100));
        outScrollPane.setForeground(Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        panelBody.add(outScrollPane);
    }

    private void renderButton(JPanel panelBody) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton verifyButton = new JButton("Verify");
        verifyButton.setPreferredSize(new Dimension(120, 40));
        verifyButton.setFont(new Font("Arial", Font.BOLD, 20));
        verifyButton.setBackground(new Color(35, 128, 251));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setFocusable(false);
        verifyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(verifyButton);

        panelBody.add(buttonPanel);

        //event button
        verifyButton.addActionListener(e->{
            if(filePath==null){
                JOptionPane.showMessageDialog(null, "Choose File is verify!!!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(hashTextArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter to your hashcode", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean verified = electronicSignature.verify(filePath, hashTextArea.getText());
            if(verified){
                outTextArea.setText("Verified!!!");
                outTextArea.setForeground(Color.GREEN);
            }
            else {
                outTextArea.setText("Unverified!!!");
                outTextArea.setForeground(Color.RED);
            }

        });
    }

    private void renderSelectMode(JPanel panelBody) {
        JPanel selectModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        selectModePanel.setBackground(Color.WHITE);

        JLabel modeHashLabel = new JLabel("Type Hash: ");
        modeHashLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectModePanel.add(modeHashLabel);

        JRadioButton md5RadioButton = new JRadioButton(Constants.Cipher.MD5);
        md5RadioButton.setBackground(Color.WHITE);
        md5RadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        md5RadioButton.setFocusable(false);
        selectModePanel.add(md5RadioButton);

        JRadioButton sha1RadioButton = new JRadioButton(Constants.Cipher.SHA_1);
        sha1RadioButton.setBackground(Color.WHITE);
        sha1RadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sha1RadioButton.setFocusable(false);
        selectModePanel.add(sha1RadioButton);

        JRadioButton sha256RadioButton = new JRadioButton(Constants.Cipher.SHA_256);
        sha256RadioButton.setSelected(true);
        sha256RadioButton.setBackground(Color.WHITE);
        sha256RadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sha256RadioButton.setFocusable(false);
        selectModePanel.add(sha256RadioButton);

        JRadioButton sha512RadioButton = new JRadioButton(Constants.Cipher.SHA_512);
        sha512RadioButton.setBackground(Color.WHITE);
        sha512RadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sha512RadioButton.setFocusable(false);
        selectModePanel.add(sha512RadioButton);

        JRadioButton sha3RadioButton = new JRadioButton(Constants.Cipher.SHA3_224);
        sha3RadioButton.setBackground(Color.WHITE);
        sha3RadioButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sha3RadioButton.setFocusable(false);
        selectModePanel.add(sha3RadioButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(md5RadioButton);
        buttonGroup.add(sha1RadioButton);
        buttonGroup.add(sha3RadioButton);
        buttonGroup.add(sha256RadioButton);
        buttonGroup.add(sha512RadioButton);

        panelBody.add(selectModePanel);

        ItemListener itemListener = (e) -> {
            Object source = e.getItemSelectable();

            if (source instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) source;
                if (radioButton.isSelected()) {
                    System.out.println("Checkbox item selected: " + radioButton.getLabel());
                }

            }
        };

        md5RadioButton.addItemListener(itemListener);
        sha1RadioButton.addItemListener(itemListener);
        sha3RadioButton.addItemListener(itemListener);
        sha256RadioButton.addItemListener(itemListener);
        sha512RadioButton.addItemListener(itemListener);
    }

    private void renderTextInput(JPanel panelBody) {
        //input name
        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameInputPanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel("Enter Your Hashcode");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameInputPanel.add(inputLabel);
        panelBody.add(nameInputPanel);

        //textfield hash
        hashTextArea = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter Your Hashcode", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        hashTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        hashTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        hashTextArea.setPreferredSize(new Dimension(600, 34));

        JScrollPane hashScrollPane = new JScrollPane(hashTextArea);
        hashScrollPane.setForeground(Color.WHITE);
        panelBody.add(hashScrollPane);

        JPopupMenu hashPopupMenu = new JPopupMenu();
        JMenuItem pasteHashItem = new JMenuItem("Paste");
        JMenuItem clearHashItem = new JMenuItem("Clear");
        hashPopupMenu.add(pasteHashItem);
        hashPopupMenu.add(clearHashItem);

        hashTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    hashPopupMenu.show(hashTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteHashItem.addActionListener(e -> {
            hashTextArea.paste();
        });
        clearHashItem.addActionListener(e -> {
            hashTextArea.setText("");
        });
    }

    private void renderFileInput(JPanel panelBody) {
        //input name
        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameInputPanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel("Input File");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameInputPanel.add(inputLabel);
        panelBody.add(nameInputPanel);

        //load file
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Color.WHITE);
        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setPreferredSize(new Dimension(120, 40));
        chooseFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        chooseFileButton.setBackground(new Color(239, 239, 239));
        chooseFileButton.setForeground(Color.BLACK);
        chooseFileButton.setFocusable(false);
        chooseFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inputPanel.add(chooseFileButton);

        //label file name
        JLabel nameFileLabel = new JLabel("No file chosen");
        nameFileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(nameFileLabel);

        JPopupMenu filePopupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("CloseFile");
        filePopupMenu.add(deleteItem);

        nameFileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && filePath != null) {
                    filePopupMenu.show(nameFileLabel, e.getX(), e.getY());
                }
            }
        });

        deleteItem.addActionListener(e -> {
            filePath = null;
            nameFileLabel.setText("No file chosen");
            outTextArea.setText("");
        });

        //event
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    nameFileLabel.setText(selectedFile.getName());
                    filePath = selectedFile.getPath();
                } else JOptionPane.showMessageDialog(null, "File is not exists!!!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        panelBody.add(inputPanel);
    }

    private void renderCustomTitleBar() {
        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(Color.WHITE);

        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        algorithmPanel.setBackground(Color.WHITE);
//        algorithmPanel.setPreferredSize(new Dimension(600, 50));

        JLabel algorithmLabel = new JLabel(electronicSignature.name);
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
        new VerifyGUI(new ElectronicSignature());
    }
}
