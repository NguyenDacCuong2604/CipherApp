package screens;

import constant.Constants;
import model.Hash.AbsHash;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderInput(JPanel panel) {
        inputPanel = new JPanel();
        TitledBorder inputBorder = BorderFactory.createTitledBorder(null, Constants.Description.INPUT, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        inputBorder.setBorder(new LineBorder(Color.BLACK, 1));
        inputPanel.setBorder(inputBorder);
        //Text
        textPanel = new JPanel();
        inputTextArea = new JTextArea();
        inputTextArea.setPreferredSize(new Dimension(600,200));
        textPanel.add(inputTextArea);

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
        filePanel.setPreferredSize(new Dimension(600,200));

        //label name File
        fileNameLabel = new JLabel("Choose File...");
        filePanel.add(fileNameLabel);

        //button
        openButton = new JButton("Open File");
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
        outputBorder.setBorder(new LineBorder(Color.BLACK, 1));
        outputPanel.setBorder(outputBorder);
        //output
        outputTextArea = new JTextArea();
        outputTextArea.setPreferredSize(new Dimension(600,200));
        outputPanel.add(outputTextArea);

        panel.add(outputPanel);
    }

    private void renderControl(JPanel panel) {
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Algorithm :");
        controlPanel.add(nameLabel);

        nameCipherLabel = new JLabel(absHash.name);
        Font font = new Font("Serif", Font.BOLD, 20);
        nameCipherLabel.setFont(font);
        controlPanel.add(nameCipherLabel);

        //space
        JLabel spaceLabel1 = new JLabel("          ");
        controlPanel.add(spaceLabel1);

        //type
        JLabel typeLabel = new JLabel("Type: ");
        controlPanel.add(typeLabel);

        //radioButton Text
        radioButtonText = new JRadioButton("Text");
        radioButtonText.setSelected(true);
        controlPanel.add(radioButtonText);

        //radioButton File
        radioButtonFile = new JRadioButton("File");
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
