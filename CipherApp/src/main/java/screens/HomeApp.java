package screens;

import constant.Constants;
import model.ASymmetricEncryption;
import model.DES;
import structure.Structure;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeApp extends JFrame implements ActionListener{
    JPanel maHoaDoiXungPanel, maHoaBatDoiXungPanel, hashAlgorithmPanel, chuKyDienTuPanel;
    JButton aesButton, desButton, tripleDesButton, blowfishButton, rivestCipher4Button, twofishButton,
            rsaButton,
            md4Button, md5Button, sha1Button, sha256Button, sha512Button, sha3_224Button, crc_16Button, crc_32Button, shake_256Button,
            chuKyDienTuButton;
    Dimension btnDimension;
    public HomeApp(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CipherApp");
        this.setLayout(new FlowLayout());
        this.setResizable(false);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
        this.setIconImage(icon);

        btnDimension = new Dimension(100, 25);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Ma hoa doi xung
        renderMaHoaDoiXung(panel);
        //Ma hoa bat doi xung
        renderMaHoaBatDoiXung(panel);
        //hash
        renderHashAlgorithmsHash(panel);
        //Chu ky dien tu
        renderChuKyDienTu(panel);

        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    private void renderChuKyDienTu(JPanel panel) {
        chuKyDienTuPanel = new JPanel();
        chuKyDienTuPanel.setPreferredSize(new Dimension(380, 60));
        chuKyDienTuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        chuKyDienTuButton = new JButton("ChuKyDienTu");
        chuKyDienTuButton.setPreferredSize(new Dimension(130,25));
        chuKyDienTuPanel.add(chuKyDienTuButton);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Chu ky dien tu", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 2));
        chuKyDienTuPanel.setBorder(titledBorder);

        panel.add(chuKyDienTuPanel);
    }

    private void renderMaHoaDoiXung(JPanel panel){
        maHoaDoiXungPanel = new JPanel();
        maHoaDoiXungPanel.setPreferredSize(new Dimension(380, 90));
        maHoaDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        desButton = new JButton("DES");
        desButton.setPreferredSize(btnDimension);
        desButton.setActionCommand(Constants.Cipher.DES);
        desButton.addActionListener(this);
        maHoaDoiXungPanel.add(desButton);

        aesButton = new JButton("AES");
        aesButton.setPreferredSize(btnDimension);
        aesButton.setActionCommand(Constants.Cipher.AES);
        aesButton.addActionListener(this);
        maHoaDoiXungPanel.add(aesButton);

        tripleDesButton = new JButton("3DES");
        tripleDesButton.setPreferredSize(btnDimension);
        tripleDesButton.setActionCommand(Constants.Cipher.TRIPLE_DES);
        tripleDesButton.addActionListener(this);
        maHoaDoiXungPanel.add(tripleDesButton);

        blowfishButton = new JButton("Blowfish");
        blowfishButton.setPreferredSize(btnDimension);
        blowfishButton.setActionCommand(Constants.Cipher.BLOWFISH);
        blowfishButton.addActionListener(this);
        maHoaDoiXungPanel.add(blowfishButton);

        rivestCipher4Button = new JButton("RC4");
        rivestCipher4Button.setPreferredSize(btnDimension);
        rivestCipher4Button.setActionCommand(Constants.Cipher.RC4);
        rivestCipher4Button.addActionListener(this);
        maHoaDoiXungPanel.add(rivestCipher4Button);

        twofishButton = new JButton("Twofish");
        twofishButton.setPreferredSize(btnDimension);
        twofishButton.setActionCommand(Constants.Cipher.TWOFISH);
        twofishButton.addActionListener(this);
        maHoaDoiXungPanel.add(twofishButton);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Ma hoa doi xung", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 2));
        maHoaDoiXungPanel.setBorder(titledBorder);

        panel.add(maHoaDoiXungPanel);
    }

    private void renderMaHoaBatDoiXung(JPanel panel){
        maHoaBatDoiXungPanel = new JPanel();
        maHoaBatDoiXungPanel.setPreferredSize(new Dimension(380, 60));
        maHoaBatDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        rsaButton = new JButton("RSA");
        rsaButton.setPreferredSize(btnDimension);
        maHoaBatDoiXungPanel.add(rsaButton);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Ma hoa bat doi xung", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 2));
        maHoaBatDoiXungPanel.setBorder(titledBorder);

        panel.add(maHoaBatDoiXungPanel);
    }

    private void renderHashAlgorithmsHash(JPanel panel){
        hashAlgorithmPanel = new JPanel();
        hashAlgorithmPanel.setPreferredSize(new Dimension(380, 120));
        hashAlgorithmPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        md4Button = new JButton("MD4");
        md4Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md4Button);

        md5Button = new JButton("MD5");
        md5Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md5Button);

        sha1Button = new JButton("SHA1");
        sha1Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha1Button);

        sha256Button = new JButton("SHA256");
        sha256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha256Button);

        sha512Button = new JButton("SHA512");
        sha512Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha512Button);

        sha3_224Button = new JButton("SHA3-224");
        sha3_224Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha3_224Button);

        crc_16Button = new JButton("CRC-16");
        crc_16Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(crc_16Button);

        crc_32Button = new JButton("CRC-32");
        crc_32Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(crc_32Button);

        shake_256Button = new JButton("Shake-256");
        shake_256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(shake_256Button);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Giai thuat Hash", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 2));
        hashAlgorithmPanel.setBorder(titledBorder);

        panel.add(hashAlgorithmPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Constants.Cipher.DES:
                ASymmetricEncryption aSymmetricEncryption = new DES();
                Structure structure = new Structure(Constants.Cipher.DES, Constants.List_Mode.DES_MODES, Constants.List_Padding.PADDINGS);
                new CipherScreen(aSymmetricEncryption, structure);
                break;
        }
    }
}
