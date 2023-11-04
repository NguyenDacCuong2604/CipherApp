package GUI;

import GUI.Component.CustomScrollBarUI;
import GUI.Component.ImageButton;
import constant.Constants;
import model.Hash.AbsHash;
import model.Hash.SHA1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class HashGUI extends JFrame {
    private Point mouseDownCompCoords = null;
    boolean isText = true;
    AbsHash absHash;
    boolean isAuto = true;
    JTextArea outputTextArea, inputTextArea;
    String filePath = null;

    public HashGUI(AbsHash absHash) {
        this.absHash = absHash;
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);
        renderCustomTitleBar();

        JPanel panelBody = new JPanel();
        panelBody.setBorder(new LineBorder(Color.WHITE, 20));
        panelBody.setLayout(new BoxLayout(panelBody, BoxLayout.Y_AXIS));

        // input
        renderInput(panelBody);
        //auto update
        renderButtonHash(panelBody);
        // output
        renderOutput(panelBody);

        this.add(panelBody, BorderLayout.CENTER);this.add(panelBody, BorderLayout.CENTER);

        setFocusable(true);
        requestFocus();

        this.pack();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderButtonHash(JPanel panelBody) {
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButton.setBackground(Color.WHITE);
        panelButton.setBorder(new EmptyBorder(8, 0, 0, 0));
        //button
        JButton hashButton = new JButton("Hash");
        hashButton.setPreferredSize(new Dimension(70, 28));
        hashButton.setFont(new Font("Arial", Font.BOLD, 14));
        hashButton.setBackground(new Color(239, 239, 239));
        hashButton.setForeground(Color.BLACK);
        hashButton.setFocusable(false);
        hashButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        JCheckBox autoUpdateCheckBox = new JCheckBox("Auto Update");
        autoUpdateCheckBox.setFocusable(false);
        autoUpdateCheckBox.setBackground(Color.WHITE);
        autoUpdateCheckBox.setSelected(true);
        autoUpdateCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
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
        titleOutputPanel.setBackground(Color.WHITE);
        JLabel outputLabel = new JLabel(Constants.Description.OUTPUT);
        outputLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleOutputPanel.add(outputLabel);

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy_output.png");
        JButton copyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        copyButton.setPreferredSize(new Dimension(25, 25));
        copyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        copyButton.setFocusable(false);
        copyButton.setBorderPainted(false);
        copyButton.setBackground(Color.WHITE);
        titleOutputPanel.add(copyButton);

        //event
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!outputTextArea.getText().isEmpty()) {
                    String hash = outputTextArea.getText(); // Thay "Your hash code" bằng mã hash thực tế của bạn

                    // Copy the text to the clipboard
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection selection = new StringSelection(hash);
                    clipboard.setContents(selection, null);

                    // Tạo một JDialog cho toast
                    JDialog toast = new JDialog();
                    toast.setUndecorated(true);
                    toast.setBackground(new Color(0, 0, 0, 0)); // Để làm cho nền trong suốt
                    toast.setLayout(new BorderLayout());
                    JLabel label = new JLabel("Copied the hash code: " + hash);
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
            }
        });


        panelBody.add(titleOutputPanel);

        JPanel outPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        outPanel.setBackground(Color.WHITE);
        outPanel.setBorder(new LineBorder(Color.BLACK, 3));

        //Text
        outputTextArea = new JTextArea();
        outputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        outputTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);

        //popup
        JPopupMenu outputPopupMenu = new JPopupMenu();
        JMenuItem copyAllItem = new JMenuItem("Copy All");
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
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(textToCopy);
            clipboard.setContents(selection, null);
        });

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.setPreferredSize(new Dimension(600, 100));
        scrollPane.setForeground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        outPanel.add(scrollPane);
        panelBody.add(outPanel);
    }

    private void renderInput(JPanel panelBody) {
        JPanel titleInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleInputPanel.setBackground(Color.WHITE);
        JLabel inputLabel = new JLabel(Constants.Description.INPUT);
        inputLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleInputPanel.add(inputLabel);
        panelBody.add(titleInputPanel);
        //type
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        typePanel.setBackground(Color.WHITE);
        //text
        JButton textInputButton = new JButton("Text");
        textInputButton.setPreferredSize(new Dimension(100, 40));
        textInputButton.setFont(new Font("Arial", Font.BOLD, 14));
        textInputButton.setBackground(Color.BLACK);
        textInputButton.setForeground(Color.WHITE);
        textInputButton.setFocusable(false);
        textInputButton.setBorderPainted(false);
        textInputButton.setEnabled(false);

        //file
        JButton fileInputButton = new JButton("File");
        fileInputButton.setPreferredSize(new Dimension(100, 40));
        fileInputButton.setFont(new Font("Arial", Font.BOLD, 14));
        fileInputButton.setBackground(new Color(239, 239, 239));
        fileInputButton.setForeground(Color.BLACK);
        fileInputButton.setFocusable(false);
        fileInputButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //fileInputButton.setBorderPainted(false);

        typePanel.add(textInputButton);
        typePanel.add(fileInputButton);

        panelBody.add(typePanel);
        //input
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new LineBorder(Color.BLACK, 3));

        //Text
        inputTextArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.drawString("Enter your text here!!!", getInsets().left, g.getFontMetrics().getAscent() + getInsets().top);
                }
            }
        };
        inputTextArea.setBorder(new LineBorder(Color.WHITE, 8));
        inputTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);

        //event inputtext
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
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem clearItem = new JMenuItem("Clear");
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
            inputTextArea.setText("");
            outputTextArea.setText("");
        });

        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        scrollPane.setPreferredSize(new Dimension(600, 180));
        scrollPane.setForeground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        inputPanel.add(scrollPane);

        //File
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        filePanel.setBackground(Color.WHITE);
        //button choose
        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.setPreferredSize(new Dimension(140, 40));
        chooseFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        chooseFileButton.setBackground(new Color(239, 239, 239));
        chooseFileButton.setForeground(Color.BLACK);
        chooseFileButton.setFocusable(false);
        chooseFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        filePanel.add(chooseFileButton);

        //label file name
        JLabel nameFileLabel = new JLabel("No file chosen");
        nameFileLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filePanel.add(nameFileLabel);
        filePanel.setVisible(false);
        inputPanel.add(filePanel);

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
            outputTextArea.setText("");
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
                } else JOptionPane.showMessageDialog(null, "File is not exists!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
                outputTextArea.setText("");
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
                outputTextArea.setText("");
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
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setEnabled(false);
        } else {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBackground(new Color(239, 239, 239));
            button.setForeground(Color.BLACK);
            button.setBorderPainted(true);
            button.setEnabled(true);
        }
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

        JLabel algorithmLabel = new JLabel(absHash.name);
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
        new HashGUI(new SHA1());
    }
}
