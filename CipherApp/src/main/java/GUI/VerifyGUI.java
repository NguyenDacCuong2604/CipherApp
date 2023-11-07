package GUI;

import GUI.Component.*;
import constant.Constants;
import model.ElectronicSignature.ElectronicSignature;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class VerifyGUI extends JFrame {
    ElectronicSignature electronicSignature;
    String filePath;
    JTextArea outTextArea;
    JTextField hashTextArea;

    public VerifyGUI(ElectronicSignature electronicSignature) {
        this.electronicSignature = electronicSignature;
        this.setUndecorated(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new LineBorder(Color.BLACK, 1));
        mainPanel.setLayout(new BorderLayout());
        //titleBar
        new TitleBar(this,mainPanel, electronicSignature.name);
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.WHITE, 15));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //body
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
        mainPanel.add(panel, BorderLayout.CENTER);
        this.add(mainPanel);
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

        outTextArea = new CustomTextArea(new Font("Arial", Font.BOLD, 24)) {
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
        JScrollPane outScrollPane = new CustomScrollPanel(outTextArea, new Dimension(600, 100), Color.WHITE);
        outScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        panelBody.add(outScrollPane);
    }

    private void renderButton(JPanel panelBody) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        //button
        JButton verifyButton = new CustomButton("Verify", new Dimension(120, 40), new Font("Arial", Font.BOLD, 20), new Color(35, 128, 251), Color.WHITE);
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
        //mode
        JLabel modeHashLabel = new JLabel("Type Hash: ");
        modeHashLabel.setFont(new Font("Arial", Font.BOLD, 20));
        selectModePanel.add(modeHashLabel);
        //md5
        JRadioButton md5RadioButton = new CustomRadioButton(Constants.Cipher.MD5, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        selectModePanel.add(md5RadioButton);
        //sha1
        JRadioButton sha1RadioButton = new CustomRadioButton(Constants.Cipher.SHA_1, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        selectModePanel.add(sha1RadioButton);
        //sha256
        JRadioButton sha256RadioButton = new CustomRadioButton(Constants.Cipher.SHA_256, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        sha256RadioButton.setSelected(true);
        selectModePanel.add(sha256RadioButton);
        //sha512
        JRadioButton sha512RadioButton = new CustomRadioButton(Constants.Cipher.SHA_512, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        selectModePanel.add(sha512RadioButton);
        //sha3
        JRadioButton sha3RadioButton = new CustomRadioButton(Constants.Cipher.SHA3_224, Color.WHITE, new Font("Arial", Font.PLAIN, 16));
        selectModePanel.add(sha3RadioButton);
        //button group
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(md5RadioButton);
        buttonGroup.add(sha1RadioButton);
        buttonGroup.add(sha3RadioButton);
        buttonGroup.add(sha256RadioButton);
        buttonGroup.add(sha512RadioButton);
        panelBody.add(selectModePanel);
        //item listener
        ItemListener itemListener = (e) -> {
            Object source = e.getItemSelectable();
            if (source instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) source;
                if (radioButton.isSelected()) {
                    electronicSignature.instance(radioButton.getLabel());
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
        //popup
        JPopupMenu hashPopupMenu = new JPopupMenu();
        JMenuItem pasteHashItem = new JMenuItem("Paste");
        JMenuItem clearHashItem = new JMenuItem("Clear");
        hashPopupMenu.add(pasteHashItem);
        hashPopupMenu.add(clearHashItem);
        //event
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
        JButton chooseFileButton = new CustomButton("Choose File",new Dimension(120, 40), new Font("Arial", Font.BOLD, 14), new Color(239, 239, 239), Color.BLACK);
        inputPanel.add(chooseFileButton);
        //label file name
        JLabel nameFileLabel = new JLabel("No file chosen");
        nameFileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(nameFileLabel);
        //popup
        JPopupMenu filePopupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("CloseFile");
        filePopupMenu.add(deleteItem);
        //event
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
}
