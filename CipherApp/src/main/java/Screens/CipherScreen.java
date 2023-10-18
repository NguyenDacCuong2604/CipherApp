package Screens;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CipherScreen extends JFrame {
    JRadioButton encryptRadioButton, decryptRadioButton;
    ButtonGroup selectButtonGroup;
    String headerInput = "PlantText", headerOutput="CipherText", temp = "Encrypt";
    JTextArea inputTextArea, outputTextArea;
    JButton clickButton;
    Dimension textDimension;
    JPanel inputPanel, selectPanel, buttonPanel;
    JTextField keyTextField;
    Color buttonColor = Color.RED;

    public CipherScreen(String cipherType){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(cipherType);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        textDimension = new Dimension(600,200);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Select
        renderSelect(panel);
        //input
        renderInput(panel);
        //button
        renderButton(panel);
        //output
        renderOutput(panel);


        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderSelect(JPanel panel){
        selectPanel = new JPanel();

        selectButtonGroup = new ButtonGroup();

        encryptRadioButton = new JRadioButton("Encrypt");
        encryptRadioButton.setSelected(true);
        selectPanel.add(encryptRadioButton);
        selectButtonGroup.add(encryptRadioButton);

        decryptRadioButton = new JRadioButton("Decrypt");
        selectPanel.add(decryptRadioButton);
        selectButtonGroup.add(decryptRadioButton);

        addAction(encryptRadioButton);
        addAction(decryptRadioButton);

        panel.add(selectPanel);
    }

    private void renderInput(JPanel panel){
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        inputTextArea = new JTextArea();
        inputTextArea.setPreferredSize(textDimension);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        inputTextArea.setBorder(titledBorder);
        inputPanel.add(inputTextArea);

        keyTextField = new JTextField();
        TitledBorder keyTitledBorder = BorderFactory.createTitledBorder(null, "Key", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        keyTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        keyTextField.setBorder(keyTitledBorder);
        inputPanel.add(keyTextField);

        panel.add(inputPanel);

    }
    private void renderButton(JPanel panel){
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(140,50));

        clickButton = new JButton(temp);
        clickButton.setFont(new Font("TimesRoman", Font.BOLD, 16));
        clickButton.setForeground(Color.BLACK);
        clickButton.setBackground(buttonColor);
        clickButton.setPreferredSize(new Dimension(120,40));
        buttonPanel.add(clickButton);

        panel.add(buttonPanel);
    }

    private void renderOutput(JPanel panel){
        outputTextArea = new JTextArea();
        outputTextArea.setPreferredSize(textDimension);
        outputTextArea.setEditable(false);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        outputTextArea.setBorder(titledBorder);

        panel.add(outputTextArea);
    }

    private void addAction(JRadioButton radioButton){
        radioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getSource() == encryptRadioButton) {
                        buttonColor = Color.RED;
                        temp = "Encrypt";
                        headerInput = "PlantText";
                        headerOutput = "CipherText";
                    } else if (e.getSource() == decryptRadioButton) {
                        buttonColor = Color.GREEN;
                        temp = "Decrypt";
                        headerInput = "CipherText";
                        headerOutput = "PlantText";
                    }
                    inputTextArea.setBorder(BorderFactory.createTitledBorder(null, headerInput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    clickButton.setBackground(buttonColor);
                    clickButton.setText(temp);
                    outputTextArea.setBorder(BorderFactory.createTitledBorder(null, headerOutput, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK));
                    revalidate();
                    repaint();
                }
            }
        });
    }
}
