package GUI;

import GUI.Component.CustomButton;
import GUI.Component.TitleBar;
import constant.Constants;
import model.ASysmmetricEncryption.RSA;
import model.ElectronicSignature.ElectronicSignature;
import model.Hash.*;
import model.SysmmetricEncryption.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class HomeGUI extends JFrame implements ActionListener {
    JButton aesButton, desButton, tripleDesButton, blowfishButton, cast_6Button, twofishButton, hillButton, vigenereButton,
            rsaButton,
            md4Button, md5Button, sha1Button, sha256Button, sha512Button, sha3_224Button, ripemd_256Button, crc_32Button, shake_256Button,
            chuKyDienTuButton;
    Font font;
    Dimension btnDimension;
    JPanel maHoaDoiXungPanel, maHoaBatDoiXungPanel, hashAlgorithmPanel, chuKyDienTuPanel;

    public HomeGUI() {
        this.setLayout(new FlowLayout());
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        //render custom title bar
        new TitleBar(this);
        btnDimension = new Dimension(140, 35);
        font = Constants.Font_Text.BOLD_16;
        Image icon = Toolkit.getDefaultToolkit().getImage(Constants.Image.ICON);
        this.setIconImage(icon);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Ma hoa doi xung
        renderMaHoaDoiXung(panel);
        //Ma hoa bat doi xung
        renderMaHoaBatDoiXung(panel);
        //hash
        renderHash(panel);
        //Chu ky dien tu
        renderChuKyDienTu(panel);
        setFocusable(true);
        requestFocus();
        this.add(panel, BorderLayout.CENTER);
        this.pack();
        //UI
        renderUI();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void renderChuKyDienTu(JPanel panel) {
        chuKyDienTuPanel = new JPanel();
        chuKyDienTuPanel.setPreferredSize(new Dimension(480, 80));
        chuKyDienTuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        chuKyDienTuButton = new CustomButton(Constants.Description.E_SIGNATURE, btnDimension, font);
        chuKyDienTuButton.setMnemonic(KeyEvent.VK_E);
        chuKyDienTuPanel.add(chuKyDienTuButton);
        chuKyDienTuButton.addActionListener(this);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, Constants.Description.E_SIGNATURE, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, Constants.Font_Text.BOLD_16, Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        chuKyDienTuPanel.setBorder(titledBorder);
        panel.add(chuKyDienTuPanel);
    }

    private void renderHash(JPanel panel) {
        hashAlgorithmPanel = new JPanel();
        hashAlgorithmPanel.setPreferredSize(new Dimension(480, 180));
        hashAlgorithmPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //md4
        md4Button = new CustomButton(Constants.Cipher.MD4, btnDimension, font);
        md4Button.setMnemonic(KeyEvent.VK_4);
        hashAlgorithmPanel.add(md4Button);
        md4Button.addActionListener(this);
        //md5
        md5Button = new CustomButton(Constants.Cipher.MD5, btnDimension, font);
        md5Button.setMnemonic(KeyEvent.VK_5);
        hashAlgorithmPanel.add(md5Button);
        md5Button.addActionListener(this);
        //sha1
        sha1Button = new CustomButton(Constants.Cipher.SHA_1, btnDimension, font);
        sha1Button.setMnemonic(KeyEvent.VK_1);
        hashAlgorithmPanel.add(sha1Button);
        sha1Button.addActionListener(this);
        //sha256
        sha256Button = new CustomButton(Constants.Cipher.SHA_256, btnDimension, font);
        sha256Button.setMnemonic(KeyEvent.VK_6);
        hashAlgorithmPanel.add(sha256Button);
        sha256Button.addActionListener(this);
        //sha512
        sha512Button = new CustomButton(Constants.Cipher.SHA_512, btnDimension, font);
        sha512Button.setMnemonic(KeyEvent.VK_H);
        hashAlgorithmPanel.add(sha512Button);
        sha512Button.addActionListener(this);
        //sha3_244
        sha3_224Button = new CustomButton(Constants.Cipher.SHA3_224, btnDimension, font);
        sha3_224Button.setMnemonic(KeyEvent.VK_2);
        hashAlgorithmPanel.add(sha3_224Button);
        sha3_224Button.addActionListener(this);
        //crc
        crc_32Button = new CustomButton(Constants.Cipher.CRC_32, btnDimension, font);
        crc_32Button.setMnemonic(KeyEvent.VK_3);
        hashAlgorithmPanel.add(crc_32Button);
        crc_32Button.addActionListener(this);
        //shake
        shake_256Button = new CustomButton(Constants.Cipher.SHAKE_256, btnDimension, font);
        shake_256Button.setMnemonic(KeyEvent.VK_K);
        hashAlgorithmPanel.add(shake_256Button);
        shake_256Button.addActionListener(this);
        //ripemd
        ripemd_256Button = new CustomButton(Constants.Cipher.RIPEMD_256, btnDimension, font);
        ripemd_256Button.setMnemonic(KeyEvent.VK_P);
        hashAlgorithmPanel.add(ripemd_256Button);
        ripemd_256Button.addActionListener(this);
        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, Constants.Description.HASH, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, Constants.Font_Text.BOLD_16, Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        hashAlgorithmPanel.setBorder(titledBorder);
        panel.add(hashAlgorithmPanel);
    }

    private void renderMaHoaBatDoiXung(JPanel panel) {
        maHoaBatDoiXungPanel = new JPanel();
        maHoaBatDoiXungPanel.setPreferredSize(new Dimension(480, 80));
        maHoaBatDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //rsa
        rsaButton = new CustomButton(Constants.Cipher.RSA, btnDimension, font);
        rsaButton.setMnemonic(KeyEvent.VK_R);
        rsaButton.addActionListener(this);
        maHoaBatDoiXungPanel.add(rsaButton);
        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, Constants.Description.ASYMMETRIC, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, Constants.Font_Text.BOLD_16, Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        maHoaBatDoiXungPanel.setBorder(titledBorder);
        panel.add(maHoaBatDoiXungPanel);
    }

    private void renderUI() {
        //mahoadoixung
        renderUIButton(desButton, new Color(198, 81, 50), new Color(147, 58, 60));
        renderUIButton(aesButton, new Color(146, 57, 60), new Color(94, 34, 68));
        renderUIButton(tripleDesButton, new Color(94, 34, 68), new Color(38, 9, 77));
        renderUIButton(blowfishButton, new Color(198, 81, 50), new Color(147, 58, 60));
        renderUIButton(cast_6Button, new Color(146, 57, 60), new Color(94, 34, 68));
        renderUIButton(twofishButton, new Color(94, 34, 68), new Color(38, 9, 77));
        renderUIButton(hillButton, new Color(172, 69, 55), new Color(123, 47, 64));
        renderUIButton(vigenereButton, new Color(117, 44, 64), new Color(43, 13, 52));
        //mahoabatdoixung
        renderUIButton(rsaButton, new Color(146, 57, 60), new Color(94, 34, 68));
        //hash
        renderUIButton(md4Button, new Color(198, 81, 50), new Color(147, 58, 60));
        renderUIButton(md5Button, new Color(146, 57, 60), new Color(94, 34, 68));
        renderUIButton(sha1Button, new Color(94, 34, 68), new Color(38, 9, 77));
        renderUIButton(sha256Button, new Color(198, 81, 50), new Color(147, 58, 60));
        renderUIButton(sha512Button, new Color(146, 57, 60), new Color(94, 34, 68));
        renderUIButton(sha3_224Button, new Color(94, 34, 68), new Color(38, 9, 77));
        renderUIButton(crc_32Button, new Color(198, 81, 50), new Color(147, 58, 60));
        renderUIButton(shake_256Button, new Color(146, 57, 60), new Color(94, 34, 68));
        renderUIButton(ripemd_256Button, new Color(94, 34, 68), new Color(38, 9, 77));
        //chu ky dien tu
        renderUIButton(chuKyDienTuButton, new Color(146, 57, 60), new Color(94, 34, 68));
    }

    private void renderMaHoaDoiXung(JPanel panel) {
        maHoaDoiXungPanel = new JPanel();
        maHoaDoiXungPanel.setPreferredSize(new Dimension(480, 180));
        maHoaDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //des
        desButton = new CustomButton(Constants.Cipher.DES, btnDimension, font);
        desButton.setMnemonic(KeyEvent.VK_D);
        desButton.addActionListener(this);
        maHoaDoiXungPanel.add(desButton);
        //aes
        aesButton = new CustomButton(Constants.Cipher.AES, btnDimension, font);
        aesButton.setMnemonic(KeyEvent.VK_A);
        aesButton.addActionListener(this);
        maHoaDoiXungPanel.add(aesButton);
        //trip des
        tripleDesButton = new CustomButton(Constants.Cipher.TRIPLE_DES, btnDimension, font);
        tripleDesButton.setMnemonic(KeyEvent.VK_S);
        tripleDesButton.addActionListener(this);
        maHoaDoiXungPanel.add(tripleDesButton);
        //blowfish
        blowfishButton = new CustomButton(Constants.Cipher.BLOWFISH, btnDimension, font);
        blowfishButton.setMnemonic(KeyEvent.VK_B);
        blowfishButton.addActionListener(this);
        maHoaDoiXungPanel.add(blowfishButton);
        //cast
        cast_6Button = new CustomButton(Constants.Cipher.CAST_6, btnDimension, font);
        cast_6Button.setMnemonic(KeyEvent.VK_C);
        cast_6Button.addActionListener(this);
        maHoaDoiXungPanel.add(cast_6Button);
        //twofish
        twofishButton = new CustomButton(Constants.Cipher.TWOFISH, btnDimension, font);
        twofishButton.setMnemonic(KeyEvent.VK_T);
        twofishButton.addActionListener(this);
        maHoaDoiXungPanel.add(twofishButton);
        //hill
        hillButton = new CustomButton(Constants.Cipher.HILL, btnDimension, font);
        hillButton.setMnemonic(KeyEvent.VK_L);
        hillButton.addActionListener(this);
        maHoaDoiXungPanel.add(hillButton);
        //vigenere
        vigenereButton = new CustomButton(Constants.Cipher.VIGENERE, btnDimension, font);
        vigenereButton.setMnemonic(KeyEvent.VK_V);
        vigenereButton.addActionListener(this);
        maHoaDoiXungPanel.add(vigenereButton);
        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, Constants.Description.SYMMETRIC, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, Constants.Font_Text.BOLD_16, Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        maHoaDoiXungPanel.setBorder(titledBorder);
        panel.add(maHoaDoiXungPanel);
    }

    private void renderUIButton(JButton gradientButton, Color left, Color right) {
        gradientButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gradientButton.setForeground(Color.WHITE);
        GradientPaint gradient = new GradientPaint(0, 0, left, gradientButton.getWidth(), (float) gradientButton.getHeight(), right);
        gradientButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(gradient);
                g2d.fill(new Rectangle2D.Float(0, 0, gradientButton.getWidth(), gradientButton.getHeight()));
                super.paint(g, c);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            //symmetric encryption
            case Constants.Cipher.DES -> {
                new SymmetricEncryptionGUI(new DES(), new DES(), Constants.List_Method.METHODS_DES);
            }
            case Constants.Cipher.AES -> {
                new SymmetricEncryptionGUI(new AES(), new AES(), Constants.List_Method.METHODS_AES);
            }
            case Constants.Cipher.TRIPLE_DES -> {
                new SymmetricEncryptionGUI(new TripleDES(), new TripleDES(), Constants.List_Method.METHODS_TRIPLEDES);
            }
            case Constants.Cipher.BLOWFISH -> {
                new SymmetricEncryptionGUI(new Blowfish(), new Blowfish(), Constants.List_Method.METHODS_BLOWFISH);
            }
            case Constants.Cipher.CAST_6 -> {
                new SymmetricEncryptionGUI(new Cast6(), new Cast6(), Constants.List_Method.METHODS_CAST6);
            }
            case Constants.Cipher.TWOFISH -> {
                new SymmetricEncryptionGUI(new Twofish(), new Twofish(), Constants.List_Method.METHODS_TWOFISH);
            }
            case Constants.Cipher.HILL -> {
                new SymmetricEncryptionGUI(new Hill(), new Hill(), Constants.List_Method.METHODS_HILL);
            }
            case Constants.Cipher.VIGENERE -> {
                new SymmetricEncryptionGUI(new Vigenere(), new Vigenere(), Constants.List_Method.METHODS_VIGENERE);
            }
            //hash
            case Constants.Cipher.SHA_1 -> {
                new HashGUI(new SHA1());
            }
            case Constants.Cipher.SHA_256 -> {
                new HashGUI(new SHA256());
            }
            case Constants.Cipher.SHA_512 -> {
                new HashGUI(new SHA512());
            }
            case Constants.Cipher.SHA3_224 -> {
                new HashGUI(new SHA3_224());
            }
            case Constants.Cipher.MD4 -> {
                new HashGUI(new MD4());
            }
            case Constants.Cipher.MD5 -> {
                new HashGUI(new MD5());
            }
            case Constants.Cipher.CRC_32 -> {
                new HashGUI(new CRC_32());
            }
            case Constants.Cipher.SHAKE_256 -> {
                new HashGUI(new SHAKE256());
            }
            case Constants.Cipher.RIPEMD_256 -> {
                new HashGUI(new RIPEMD_256());
            }
            //asymmetric encryption
            case Constants.Cipher.RSA -> {
                new ASymmetricEncryptGUI(new RSA(), Constants.List_Method.METHODS_RSA);
            }
            //verify
            case Constants.Description.E_SIGNATURE -> {
                new VerifyGUI(new ElectronicSignature());
            }
        }
    }
}
