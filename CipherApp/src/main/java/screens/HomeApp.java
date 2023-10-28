package screens;

import constant.Constants;
import model.ASysmmetricEncryption.AbsASymmetricEncryption;
import model.ASysmmetricEncryption.RSA;
import model.ElectronicSignature.ElectronicSignature;
import model.Hash.*;
import model.SysmmetricEncryption.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeApp extends JFrame implements ActionListener{
    JPanel maHoaDoiXungPanel, maHoaBatDoiXungPanel, hashAlgorithmPanel, chuKyDienTuPanel;
    JButton aesButton, desButton, tripleDesButton, blowfishButton, cast_6Button, twofishButton,
            rsaButton,
            md4Button, md5Button, sha1Button, sha256Button, sha512Button, sha3_224Button, ripemd_256Button, crc_32Button, shake_256Button,
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

        chuKyDienTuButton = new JButton(Constants.Description.E_SIGNATURE);
        chuKyDienTuButton.setPreferredSize(new Dimension(130,25));
        chuKyDienTuPanel.add(chuKyDienTuButton);
        chuKyDienTuButton.setActionCommand(Constants.Description.E_SIGNATURE);
        chuKyDienTuButton.addActionListener(this);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Chu ky dien tu", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 16), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 2));
        chuKyDienTuPanel.setBorder(titledBorder);

        panel.add(chuKyDienTuPanel);
    }

    private void renderMaHoaDoiXung(JPanel panel){
        maHoaDoiXungPanel = new JPanel();
        maHoaDoiXungPanel.setPreferredSize(new Dimension(380, 90));
        maHoaDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        desButton = new JButton(Constants.Cipher.DES);
        desButton.setPreferredSize(btnDimension);
        desButton.setActionCommand(Constants.Cipher.DES);
        desButton.addActionListener(this);
        maHoaDoiXungPanel.add(desButton);

        aesButton = new JButton(Constants.Cipher.AES);
        aesButton.setPreferredSize(btnDimension);
        aesButton.setActionCommand(Constants.Cipher.AES);
        aesButton.addActionListener(this);
        maHoaDoiXungPanel.add(aesButton);

        tripleDesButton = new JButton(Constants.Cipher.TRIPLE_DES);
        tripleDesButton.setPreferredSize(btnDimension);
        tripleDesButton.setActionCommand(Constants.Cipher.TRIPLE_DES);
        tripleDesButton.addActionListener(this);
        maHoaDoiXungPanel.add(tripleDesButton);

        blowfishButton = new JButton(Constants.Cipher.BLOWFISH);
        blowfishButton.setPreferredSize(btnDimension);
        blowfishButton.setActionCommand(Constants.Cipher.BLOWFISH);
        blowfishButton.addActionListener(this);
        maHoaDoiXungPanel.add(blowfishButton);

        cast_6Button = new JButton(Constants.Cipher.CAST_6);
        cast_6Button.setPreferredSize(btnDimension);
        cast_6Button.setActionCommand(Constants.Cipher.CAST_6);
        cast_6Button.addActionListener(this);
        maHoaDoiXungPanel.add(cast_6Button);

        twofishButton = new JButton(Constants.Cipher.TWOFISH);
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

        rsaButton = new JButton(Constants.Cipher.RSA);
        rsaButton.setActionCommand(Constants.Cipher.RSA);
        rsaButton.addActionListener(this);
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

        md4Button = new JButton(Constants.Cipher.MD4);
        md4Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md4Button);
        md4Button.setActionCommand(Constants.Cipher.MD4);
        md4Button.addActionListener(this);

        md5Button = new JButton(Constants.Cipher.MD5);
        md5Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md5Button);
        md5Button.setActionCommand(Constants.Cipher.MD5);
        md5Button.addActionListener(this);

        sha1Button = new JButton(Constants.Cipher.SHA_1);
        sha1Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha1Button);
        sha1Button.setActionCommand(Constants.Cipher.SHA_1);
        sha1Button.addActionListener(this);

        sha256Button = new JButton(Constants.Cipher.SHA_256);
        sha256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha256Button);
        sha256Button.setActionCommand(Constants.Cipher.SHA_256);
        sha256Button.addActionListener(this);

        sha512Button = new JButton(Constants.Cipher.SHA_512);
        sha512Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha512Button);
        sha512Button.setActionCommand(Constants.Cipher.SHA_512);
        sha512Button.addActionListener(this);

        sha3_224Button = new JButton(Constants.Cipher.SHA3_224);
        sha3_224Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha3_224Button);
        sha3_224Button.setActionCommand(Constants.Cipher.SHA3_224);
        sha3_224Button.addActionListener(this);

        crc_32Button = new JButton(Constants.Cipher.CRC_32);
        crc_32Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(crc_32Button);
        crc_32Button.setActionCommand(Constants.Cipher.CRC_32);
        crc_32Button.addActionListener(this);

        shake_256Button = new JButton(Constants.Cipher.SHAKE_256);
        shake_256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(shake_256Button);
        shake_256Button.setActionCommand(Constants.Cipher.SHAKE_256);
        shake_256Button.addActionListener(this);

        ripemd_256Button = new JButton(Constants.Cipher.RIPEMD_256);
        ripemd_256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(ripemd_256Button);
        ripemd_256Button.setActionCommand(Constants.Cipher.RIPEMD_256);
        ripemd_256Button.addActionListener(this);

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
                AbsSymmetricEncryption des = new DES();
                new SymmetricEncryptScreen(des, Constants.List_Method.METHODS_DES);
                break;
            case  Constants.Cipher.TRIPLE_DES:
                AbsSymmetricEncryption tripleDes = new TripleDES();
                new SymmetricEncryptScreen(tripleDes, Constants.List_Method.METHODS_TRIPLEDES);
                break;
            case Constants.Cipher.AES:
                AbsSymmetricEncryption aes = new AES();
                new SymmetricEncryptScreen(aes, Constants.List_Method.METHODS_AES);
                break;
            case Constants.Cipher.BLOWFISH:
                AbsSymmetricEncryption blowFish = new Blowfish();
                new SymmetricEncryptScreen(blowFish, Constants.List_Method.METHODS_BLOWFISH);
                break;
            case Constants.Cipher.TWOFISH:
                AbsSymmetricEncryption twoFish = new Twofish();
                new SymmetricEncryptScreen(twoFish, Constants.List_Method.METHODS_TWOFISH);
                break;
            case Constants.Cipher.CAST_6:
                AbsSymmetricEncryption cast6 = new Cast6();
                new SymmetricEncryptScreen(cast6, Constants.List_Method.METHODS_CAST6);
                break;
            case Constants.Cipher.RSA:
                AbsASymmetricEncryption rsa = new RSA();
                new ASymmetricEncryptScreen(rsa, Constants.List_Method.METHODS_RSA);
                break;
            case Constants.Cipher.SHA_1:
                AbsHash sha1 = new SHA1();
                new HashScreen(sha1);
                break;
            case Constants.Cipher.SHA_256:
                AbsHash sha256 = new SHA256();
                new HashScreen(sha256);
                break;
            case Constants.Cipher.SHA_512:
                AbsHash sha512 = new SHA512();
                new HashScreen(sha512);
                break;
            case Constants.Cipher.SHA3_224:
                AbsHash sha3_224 = new SHA3_224();
                new HashScreen(sha3_224);
                break;
            case Constants.Cipher.MD4:
                AbsHash md4 = new MD4();
                new HashScreen(md4);
                break;
            case Constants.Cipher.MD5:
                AbsHash md5 = new MD5();
                new HashScreen(md5);
                break;
            case Constants.Cipher.CRC_32:
                AbsHash crc32 = new CRC_32();
                new HashScreen(crc32);
                break;
            case Constants.Cipher.SHAKE_256:
                AbsHash shake256 = new SHAKE256();
                new HashScreen(shake256);
                break;
            case Constants.Cipher.RIPEMD_256:
                AbsHash ripemd256 = new RIPEMD_256();
                new HashScreen(ripemd256);
                break;
            case Constants.Description.E_SIGNATURE:
                ElectronicSignature electronicSignature = new ElectronicSignature();
                new ElectronicSignatureScreen(electronicSignature);
                break;
        }
    }
}
