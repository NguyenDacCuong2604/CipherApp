package screens;

import constant.Constants;
import model.Hash.AbsHash;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class HashScreen extends JFrame {
    JPanel controlPanel, inputPanel, outputPanel, textPanel, filePanel;
    JLabel nameCipherLabel, fileNameLabel;
    AbsHash absHash;
    JRadioButton radioButtonText, radioButtonFile;
    ButtonGroup buttonGroup;
    JTextArea inputTextArea, outputTextArea;
    JFileChooser fileChooser;
    JButton openButton;

    public HashScreen(AbsHash absHash){
        this.absHash = absHash;


        this.setResizable(false);
        this.setTitle(absHash.name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //control
        renderControl(panel);

        //input
        renderInput(panel);
        
        //output
        renderOutput(panel);

        setFocusable(true);
        requestFocus();

        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderInput(JPanel panel) {
        inputPanel = new JPanel();
        TitledBorder inputBorder = BorderFactory.createTitledBorder(null, Constants.Description.INPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        inputBorder.setBorder(new LineBorder(Color.BLACK, 2));
        inputPanel.setBorder(inputBorder);
        //Text
        textPanel = new JPanel();
        inputTextArea = new JTextArea(12, 40);
        inputTextArea.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        textPanel.add(scrollPane);

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
                String text = inputTextArea.getText();
                outputTextArea.setText(absHash.hashText(text));
            }
        });

        inputPanel.add(textPanel);

        //File
        filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filePanel.setPreferredSize(inputTextArea.getPreferredSize());

        //label name File
        fileNameLabel = new JLabel("Choose File...");
        fileNameLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        filePanel.add(fileNameLabel);

        //button
        openButton = new JButton("Open File");
        openButton.setFocusable(false);
        filePanel.add(openButton);

        //event button
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileNameLabel.setText(selectedFile.getName());
                    String textHash = absHash.hashFile(selectedFile.getPath());
                    outputTextArea.setText(textHash);
                }
            }
        });

        inputPanel.add(filePanel);

        //unvisible
        filePanel.setVisible(false);

        panel.add(inputPanel);

    }

    private void renderOutput(JPanel panel) {
        outputPanel = new JPanel();
        TitledBorder outputBorder = BorderFactory.createTitledBorder(null, Constants.Description.OUTPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        outputBorder.setBorder(new LineBorder(Color.BLACK, 2));
        outputPanel.setBorder(outputBorder);
        //output
        outputTextArea = new JTextArea(4, 40);
        outputTextArea.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        outputPanel.add(scrollPane);

        panel.add(outputPanel);
    }

    private void renderControl(JPanel panel) {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        Font font = new Font("Serif", Font.BOLD, 25);

        JLabel nameLabel = new JLabel("Algorithm :");
        nameLabel.setFont(font);
        controlPanel.add(nameLabel);

        nameCipherLabel = new JLabel(absHash.name);

        nameCipherLabel.setFont(font);
        controlPanel.add(nameCipherLabel);

        //space
        JLabel spaceLabel1 = new JLabel("          ");
        controlPanel.add(spaceLabel1);

        Font fontSelect = new Font("Serif", Font.BOLD, 20);
        //type
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(fontSelect);
        controlPanel.add(typeLabel);

        //radioButton Text
        radioButtonText = new JRadioButton("Text");
        radioButtonText.setFont(fontSelect);
        radioButtonText.setMnemonic(KeyEvent.VK_T);
        radioButtonText.setFocusable(false);
        radioButtonText.setSelected(true);
        controlPanel.add(radioButtonText);

        //radioButton File
        radioButtonFile = new JRadioButton("File");
        radioButtonFile.setFont(fontSelect);
        radioButtonFile.setMnemonic(KeyEvent.VK_F);
        radioButtonFile.setFocusable(false);
        controlPanel.add(radioButtonFile);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonText);
        buttonGroup.add(radioButtonFile);

        //event
        radioButtonFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                textPanel.setVisible(false);
                filePanel.setVisible(true);
                fileNameLabel.setText("Choose File...");
                outputTextArea.setText("");
            }
        });

        radioButtonText.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                textPanel.setVisible(true);
                inputTextArea.setText("");
                outputTextArea.setText("");
                filePanel.setVisible(false);
            }
        });

        panel.add(controlPanel);
    }
}
