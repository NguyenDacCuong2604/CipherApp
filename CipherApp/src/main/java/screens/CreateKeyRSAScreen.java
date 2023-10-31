package screens;

import constant.Constants;
import model.ASysmmetricEncryption.CreateKeyRSA;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateKeyRSAScreen extends JFrame {
    CreateKeyRSA createKeyRSA;
    JButton createKeyButton, copyPublicKeyButton, copyPrivateKeyButton;
    JTextField publicKeyText, privateKeyText;
    JComboBox sizeComboBox;
    public CreateKeyRSAScreen(){
        createKeyRSA = new CreateKeyRSA();

        this.setTitle("Create Key RSA");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        renderSelectSize(panel);
        renderPublicKey(panel);
        renderPrivateKey(panel);
        renderButtonCreate(panel);

        setFocusable(true);
        requestFocus();

        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void renderSelectSize(JPanel panel) {
        JPanel panelSelect = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //label
        JLabel selectLabel = new JLabel("Select RSA Key Size: ");
        panelSelect.add(selectLabel);

        //combobox
        sizeComboBox = new JComboBox(Constants.List_Size.SIZE_RSA);
        sizeComboBox.setSelectedItem(Constants.Size.BIT1024);
        sizeComboBox.setFocusable(false);
        panelSelect.add(sizeComboBox);

        panel.add(panelSelect);
    }

    private void renderButtonCreate(JPanel panel) {
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButton.setPreferredSize(new Dimension(200, 50));
        createKeyButton = new JButton("CREATE");
        createKeyButton.setPreferredSize(new Dimension(180, 30));
        createKeyButton.setFocusable(false);
        panelButton.add(createKeyButton);
        panel.add(panelButton);

        createKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((sizeComboBox.getSelectedItem()).equals(Constants.Size.BIT515)){
                    createKeyRSA.createKey(515);
                }
                else if((sizeComboBox.getSelectedItem()).equals(Constants.Size.BIT1024)){
                    createKeyRSA.createKey(1024);
                }
                else if((sizeComboBox.getSelectedItem()).equals(Constants.Size.BIT2048)){
                    createKeyRSA.createKey(2048);
                }
                else if((sizeComboBox.getSelectedItem()).equals(Constants.Size.BIT4096)){
                    createKeyRSA.createKey(4096);
                }
                else if((sizeComboBox.getSelectedItem()).equals(Constants.Size.BIT3072)){
                    createKeyRSA.createKey(3072);
                }

                publicKeyText.setText(createKeyRSA.getPublicKey());
                privateKeyText.setText(createKeyRSA.getPrivateKey());
                repaint();
            }
        });
    }

    private void renderPrivateKey(JPanel panel) {
        JPanel panelPrivateKey = new JPanel();
        panelPrivateKey.setLayout(new FlowLayout(FlowLayout.LEFT));
        privateKeyText = new JTextField();
        privateKeyText.setFont( new Font("TimesRoman", Font.PLAIN, 16));
        privateKeyText.setPreferredSize(new Dimension(450, 60));
        TitledBorder privateTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PRIVATE_KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        privateTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        privateKeyText.setBorder(privateTitledBorder);
        privateKeyText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(privateKeyText);

        panelPrivateKey.add(scrollPane);

        ImageIcon copyIcon = new ImageIcon("assets/Images/copy.png");
        copyPrivateKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        copyPrivateKeyButton.setToolTipText("Copy PublicKey");
        copyPrivateKeyButton.setPreferredSize(new Dimension(35, 35));

        panelPrivateKey.add(copyPrivateKeyButton);
        //event
        copyPrivateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = privateKeyText.getText();

                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
            }
        });

        panel.add(panelPrivateKey);
    }

    private void renderPublicKey(JPanel panel) {
        JPanel panelPublicKey = new JPanel();
        panelPublicKey.setLayout(new FlowLayout(FlowLayout.LEFT));
        publicKeyText = new JTextField();
        publicKeyText.setFont( new Font("TimesRoman", Font.PLAIN, 16));
        publicKeyText.setPreferredSize(new Dimension(450, 60));
        TitledBorder publicTitledBorder = BorderFactory.createTitledBorder(null, Constants.Description.PUBLIC_KEY, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        publicTitledBorder.setBorder(new LineBorder(Color.BLACK, 1));
        publicKeyText.setBorder(publicTitledBorder);
        publicKeyText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(publicKeyText);

        panelPublicKey.add(scrollPane);


        ImageIcon copyIcon = new ImageIcon("assets/Images/copy.png");
        copyPublicKeyButton = new JButton(new ImageIcon(copyIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        copyPublicKeyButton.setToolTipText("Copy PrivateKey");
        copyPublicKeyButton.setPreferredSize(new Dimension(35, 35));
        panelPublicKey.add(copyPublicKeyButton);
        //event
        copyPublicKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = publicKeyText.getText();

                // Copy the text to the clipboard
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection selection = new StringSelection(textToCopy);
                clipboard.setContents(selection, null);
            }
        });


        panel.add(panelPublicKey);
    }
}
