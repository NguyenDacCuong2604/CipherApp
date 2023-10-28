package screens;

import constant.Constants;
import model.ElectronicSignature.ElectronicSignature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ElectronicSignatureScreen extends JFrame {
    ElectronicSignature electronicSignature;
    JPanel filePanel, buttonPanel;
    JLabel fileNameLabel;
    JButton openButton, verifyButton;
    boolean isSelectFile = false;
    String pathFile =null;
    public ElectronicSignatureScreen(ElectronicSignature electronicSignature){
        this.electronicSignature = electronicSignature;
        this.setTitle(electronicSignature.name);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //renderControl
        renderControl(panel);
        renderInput(panel);
        renderButton(panel);


        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderButton(JPanel panel) {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(400, 60));

        verifyButton = new JButton(Constants.Description.VERIFY);
        buttonPanel.add(verifyButton);

        //event button verify
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isSelectFile){
                    JOptionPane.showMessageDialog(null, "Select File, Please!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    System.out.println(electronicSignature.verifySignature(pathFile));
                }
            }
        });

        panel.add(buttonPanel);
    }

    private void renderInput(JPanel panel) {
        filePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filePanel.setPreferredSize(new Dimension(300,50));

        //label name File
        fileNameLabel = new JLabel("Select File...");
        filePanel.add(fileNameLabel);

        //button
        openButton = new JButton(Constants.Description.OPEN_FILE);
        filePanel.add(openButton);

        //event
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileNameLabel.setText(selectedFile.getName());
                    electronicSignature.loadFileData(selectedFile.getPath());
                    isSelectFile = true;
                    pathFile = selectedFile.getPath();
                }
            }
        });

        panel.add(filePanel);
    }

    private void renderControl(JPanel panel) {
        JPanel panelLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Verify E_Signature");
        Font font = new Font("Serif", Font.BOLD, 20);
        label.setFont(font);
        panelLabel.add(label);
        panel.add(panelLabel);
    }
}
