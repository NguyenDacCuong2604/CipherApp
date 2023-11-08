package GUI;

import GUI.Component.*;
import constant.Constants;
import model.Hash.AbsHash;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class HashGUI extends JFrame {
    boolean isText = true;
    AbsHash absHash;
    boolean isAuto = true;
    JTextArea outputTextArea, inputTextArea;
    String filePath = null;
    public HashGUI(AbsHash absHash) {
        this.absHash = absHash;
        this.setUndecorated(true);
        Image icon = Toolkit.getDefaultToolkit().getImage(Constants.Image.ICON);
        this.setIconImage(icon);
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new LineBorder(Color.BLACK, 1));
        mainPanel.setLayout(new BorderLayout());
        //titlebar
        new TitleBar(this, mainPanel, absHash.name);
        JPanel panelBody = new JPanel();
        panelBody.setBackground(Constants.ColorUI.TEXT_WHITE);
        panelBody.setBorder(new LineBorder(Constants.ColorUI.TEXT_WHITE, 20));
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));
        // input
        renderInput(panelBody);
        //auto update
        renderButtonHash(panelBody);
        // output
        renderOutput(panelBody);
        mainPanel.add(panelBody, BorderLayout.CENTER);
        this.add(mainPanel);
        setFocusable(true);
        requestFocus();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    private void renderButtonHash(JPanel panelBody) {
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButton.setBackground(Constants.ColorUI.TEXT_WHITE);
        panelButton.setBorder(new EmptyBorder(8, 0, 0, 0));
        //button
        JButton hashButton = new CustomButton(Constants.Description.HASH, new Dimension(70, 28),new Font("Arial", Font.BOLD, 14), Constants.ColorUI.BUTTON, Color.BLACK);
        panelButton.add(hashButton);
        //event button
        hashButton.addActionListener(e -> {
            if (isText) {
                String text = inputTextArea.getText();
                outputTextArea.setText(absHash.hashText(text));
            } else {
                if (filePath != null) {
                    String hashFile = absHash.hashFile(filePath);
                    outputTextArea.setText(hashFile);
                }
            }
        });
        //auto hash
        JCheckBox autoUpdateCheckBox = new CustomCheckBox(Constants.Description.AUTO_UPDATE, Constants.ColorUI.TEXT_WHITE, new Font("Arial", Font.PLAIN, 14));
        autoUpdateCheckBox.setSelected(true);
        //event
        autoUpdateCheckBox.addActionListener(e -> {
            if (!autoUpdateCheckBox.isSelected()) {
                isAuto = false;
            } else {
                isAuto = true;
                if (isText) {
                    String text = inputTextArea.getText();
                    outputTextArea.setText(absHash.hashText(text));
                } else {
                    if (filePath != null) {
                        String hashFile = absHash.hashFile(filePath);
                        outputTextArea.setText(hashFile);
                    }
                }
            }
            revalidate();
            repaint();
        });
        panelButton.add(autoUpdateCheckBox);
        panelBody.add(panelButton);
    }
    private void renderOutput(JPanel panelBody) {
        JPanel titleOutputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        titleOutputPanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        JLabel outputLabel = new JLabel(Constants.Description.OUTPUT);
        outputLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleOutputPanel.add(outputLabel);
        ImageIcon copyIcon = new ImageIcon(Constants.Image.COPY);
        JButton copyButton = new CustomIconButton(new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)), new Dimension(25, 25), Constants.ColorUI.TEXT_WHITE);
        titleOutputPanel.add(copyButton);
        //event
        copyButton.addActionListener(e -> {
            if (!outputTextArea.getText().isEmpty()) {
                String hash = outputTextArea.getText();
                // Copy the text to the clipboard
                model.Toolkit.copy(hash);
                ShowToast showToast = new ShowToast(this, Constants.Description.COPIED + hash);
                showToast.showToast(2000);
            }
        });
        panelBody.add(titleOutputPanel);
        JPanel outPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outPanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        outPanel.setBorder(new LineBorder(Color.BLACK, 3));
        //Text
        outputTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 20));
        outputTextArea.setBorder(new LineBorder(Constants.ColorUI.TEXT_WHITE, 8));
        outputTextArea.setEditable(false);
        //popup
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem(Constants.Description.COPY_ALL);
        outputPopupMenu.add(copyAllItem);
        outputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    outputPopupMenu.show(outputTextArea, e.getX(), e.getY());
                }
            }
        });
        copyAllItem.addActionListener(e -> {
            String textToCopy = outputTextArea.getText();
            // Copy the text to the clipboard
            model.Toolkit.copy(textToCopy);
        });
        JScrollPane scrollPane = new CustomScrollPanel(outputTextArea,new Dimension(600, 100), Constants.ColorUI.TEXT_WHITE);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        outPanel.add(scrollPane);
        panelBody.add(outPanel);
    }
    private void renderInput(JPanel panelBody) {
        JPanel titleInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleInputPanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        JLabel inputLabel = new JLabel(Constants.Description.INPUT);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleInputPanel.add(inputLabel);
        panelBody.add(titleInputPanel);
        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        typePanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        //text
        JButton textInputButton = new CustomButton(Constants.Description.TEXT, new Dimension(100, 40), new Font("Arial", Font.BOLD, 14), Color.BLACK, Constants.ColorUI.TEXT_WHITE);
        textInputButton.setEnabled(false);
        //file
        JButton fileInputButton = new CustomButton(Constants.Description.FILE, new Dimension(100, 40), new Font("Arial", Font.BOLD, 14), Constants.ColorUI.BUTTON, Color.BLACK);
        typePanel.add(textInputButton);
        typePanel.add(fileInputButton);
        panelBody.add(typePanel);
        //input
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        inputPanel.setBorder(new LineBorder(Color.BLACK, 3));
        //Text
        inputTextArea = new CustomTextArea(new Font("Arial", Font.PLAIN, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString(Constants.Description.ENTER_TEXT, getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Constants.ColorUI.TEXT_WHITE, 8));
        //event input text
        inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSHA1Hash();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSHA1Hash();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSHA1Hash();
            }
            private void updateSHA1Hash() {
                if (isAuto) {
                    String text = inputTextArea.getText();
                    outputTextArea.setText(absHash.hashText(text));
                }
            }
        });
        //popup
        JPopupMenu inputPopupMenu = new JPopupMenu();
        JMenuItem pasteItem = new JMenuItem(Constants.Description.PASTE);
        JMenuItem clearItem = new JMenuItem(Constants.Description.CLEAR);
        inputPopupMenu.add(pasteItem);
        inputPopupMenu.add(clearItem);
        inputTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    inputPopupMenu.show(inputTextArea, e.getX(), e.getY());
                }
            }
        });
        pasteItem.addActionListener(e -> {
            inputTextArea.paste();
        });
        clearItem.addActionListener(e -> {
            inputTextArea.setText(Constants.Description.EMPTY);
            outputTextArea.setText(Constants.Description.EMPTY);
        });
        JScrollPane scrollPane = new CustomScrollPanel(inputTextArea,new Dimension(600, 180),Constants.ColorUI.TEXT_WHITE);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        inputPanel.add(scrollPane);
        //File
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        filePanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        //button choose
        JButton chooseFileButton = new CustomButton(Constants.Description.CHOOSE_FILE, new Dimension(140, 40), new Font("Arial", Font.BOLD, 14), Constants.ColorUI.BUTTON, Color.BLACK);
        filePanel.add(chooseFileButton);
        //label file name
        JLabel nameFileLabel = new JLabel(Constants.Description.NO_FILE_CHOSEN);
        nameFileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filePanel.add(nameFileLabel);
        filePanel.setVisible(false);
        inputPanel.add(filePanel);
        JPopupMenu filePopupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem(Constants.Description.CLOSE_FILE);
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
            nameFileLabel.setText(Constants.Description.NO_FILE_CHOSEN);
            outputTextArea.setText(Constants.Description.EMPTY);
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
                    if (isAuto) {
                        String textHash = absHash.hashFile(filePath);
                        outputTextArea.setText(textHash);
                    }
                } else JOptionPane.showMessageDialog(null, Constants.Description.FILE_NOT_EXIST, Constants.Description.ERROR, JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBody.add(inputPanel);
        //event
        textInputButton.addActionListener(e -> {
            isText = true;
            filePanel.setVisible(false);
            scrollPane.setVisible(true);
            setChooseButton(textInputButton, true);
            setChooseButton(fileInputButton, false);
            if (!inputTextArea.getText().isEmpty()) {
                String text = inputTextArea.getText();
                outputTextArea.setText(absHash.hashText(text));
            } else {
                outputTextArea.setText(Constants.Description.EMPTY);
            }
            revalidate();
            repaint();
            this.pack();
        });
        fileInputButton.addActionListener(e -> {
            isText = false;
            filePanel.setVisible(true);
            scrollPane.setVisible(false);
            setChooseButton(fileInputButton, true);
            setChooseButton(textInputButton, false);
            if (filePath != null) {
                String hashFile = absHash.hashFile(filePath);
                outputTextArea.setText(hashFile);
            } else {
                outputTextArea.setText(Constants.Description.EMPTY);
            }
            revalidate();
            repaint();
            this.pack();
        });
    }
    public void setChooseButton(JButton button, boolean isChoose) {
        if (isChoose) {
            button.setCursor(null);
            button.setBackground(Color.BLACK);
            button.setForeground(Constants.ColorUI.TEXT_WHITE);
            button.setBorderPainted(false);
            button.setEnabled(false);
        } else {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBackground(Constants.ColorUI.BUTTON);
            button.setForeground(Color.BLACK);
            button.setBorderPainted(true);
            button.setEnabled(true);
        }
    }
}
