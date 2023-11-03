package GUI;

import constant.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class HomeGUI extends JFrame implements ActionListener {
    JButton aesButton, desButton, tripleDesButton, blowfishButton, cast_6Button, twofishButton, hillButton, vigenereButton,
            rsaButton,
            md4Button, md5Button, sha1Button, sha256Button, sha512Button, sha3_224Button, ripemd_256Button, crc_32Button, shake_256Button,
            chuKyDienTuButton;
    Font font;
    Dimension btnDimension;
    JPanel maHoaDoiXungPanel, maHoaBatDoiXungPanel, hashAlgorithmPanel, chuKyDienTuPanel;
    public HomeGUI(){
        this.setResizable(false);
        this.setTitle("CipherApp_NDCopyright");
        this.setLayout(new FlowLayout());
        this.setResizable(false);

        btnDimension = new Dimension(140, 35);
        font = new Font("Arial", Font.BOLD, 16);

        Image icon = Toolkit.getDefaultToolkit().getImage("assets/images/icon.png");
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

        this.getContentPane().add(panel);
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

        chuKyDienTuButton = new JButton(Constants.Description.E_SIGNATURE);
        chuKyDienTuButton.setFont(font);
        chuKyDienTuButton.setFocusable(false);
        chuKyDienTuButton.setMnemonic(KeyEvent.VK_E);
        chuKyDienTuButton.setPreferredSize(btnDimension);
        chuKyDienTuPanel.add(chuKyDienTuButton);
        chuKyDienTuButton.setActionCommand(Constants.Description.E_SIGNATURE);
        chuKyDienTuButton.addActionListener(this);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Digital Signatures ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 20), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        chuKyDienTuPanel.setBorder(titledBorder);

        panel.add(chuKyDienTuPanel);
    }

    private void renderHash(JPanel panel) {
        hashAlgorithmPanel = new JPanel();
        hashAlgorithmPanel.setPreferredSize(new Dimension(480, 180));
        hashAlgorithmPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        md4Button = new JButton(Constants.Cipher.MD4);
        md4Button.setMnemonic(KeyEvent.VK_4);
        md4Button.setFont(font);
        md4Button.setFocusable(false);
        md4Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md4Button);
        md4Button.setActionCommand(Constants.Cipher.MD4);
        md4Button.addActionListener(this);

        md5Button = new JButton(Constants.Cipher.MD5);
        md5Button.setMnemonic(KeyEvent.VK_5);
        md5Button.setFont(font);
        md5Button.setFocusable(false);
        md5Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(md5Button);
        md5Button.setActionCommand(Constants.Cipher.MD5);
        md5Button.addActionListener(this);

        sha1Button = new JButton(Constants.Cipher.SHA_1);
        sha1Button.setMnemonic(KeyEvent.VK_1);
        sha1Button.setFont(font);
        sha1Button.setFocusable(false);
        sha1Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha1Button);
        sha1Button.setActionCommand(Constants.Cipher.SHA_1);
        sha1Button.addActionListener(this);

        sha256Button = new JButton(Constants.Cipher.SHA_256);
        sha256Button.setMnemonic(KeyEvent.VK_6);
        sha256Button.setFont(font);
        sha256Button.setFocusable(false);
        sha256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha256Button);
        sha256Button.setActionCommand(Constants.Cipher.SHA_256);
        sha256Button.addActionListener(this);

        sha512Button = new JButton(Constants.Cipher.SHA_512);
        sha512Button.setMnemonic(KeyEvent.VK_H);
        sha512Button.setFont(font);
        sha512Button.setFocusable(false);
        sha512Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha512Button);
        sha512Button.setActionCommand(Constants.Cipher.SHA_512);
        sha512Button.addActionListener(this);

        sha3_224Button = new JButton(Constants.Cipher.SHA3_224);
        sha3_224Button.setMnemonic(KeyEvent.VK_2);
        sha3_224Button.setFont(font);
        sha3_224Button.setFocusable(false);
        sha3_224Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(sha3_224Button);
        sha3_224Button.setActionCommand(Constants.Cipher.SHA3_224);
        sha3_224Button.addActionListener(this);

        crc_32Button = new JButton(Constants.Cipher.CRC_32);
        crc_32Button.setMnemonic(KeyEvent.VK_3);
        crc_32Button.setFont(font);
        crc_32Button.setFocusable(false);
        crc_32Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(crc_32Button);
        crc_32Button.setActionCommand(Constants.Cipher.CRC_32);
        crc_32Button.addActionListener(this);

        shake_256Button = new JButton(Constants.Cipher.SHAKE_256);
        shake_256Button.setMnemonic(KeyEvent.VK_K);
        shake_256Button.setFont(font);
        shake_256Button.setFocusable(false);
        shake_256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(shake_256Button);
        shake_256Button.setActionCommand(Constants.Cipher.SHAKE_256);
        shake_256Button.addActionListener(this);

        ripemd_256Button = new JButton(Constants.Cipher.RIPEMD_256);
        ripemd_256Button.setMnemonic(KeyEvent.VK_P);
        ripemd_256Button.setFont(font);
        ripemd_256Button.setFocusable(false);
        ripemd_256Button.setPreferredSize(btnDimension);
        hashAlgorithmPanel.add(ripemd_256Button);
        ripemd_256Button.setActionCommand(Constants.Cipher.RIPEMD_256);
        ripemd_256Button.addActionListener(this);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Hash ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 20), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        hashAlgorithmPanel.setBorder(titledBorder);

        panel.add(hashAlgorithmPanel);
    }

    private void renderMaHoaBatDoiXung(JPanel panel) {
        maHoaBatDoiXungPanel = new JPanel();
        maHoaBatDoiXungPanel.setPreferredSize(new Dimension(480, 80));
        maHoaBatDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        rsaButton = new JButton(Constants.Cipher.RSA);
        rsaButton.setMnemonic(KeyEvent.VK_R);
        rsaButton.setFont(font);
        rsaButton.setFocusable(false);
        rsaButton.setActionCommand(Constants.Cipher.RSA);
        rsaButton.addActionListener(this);
        rsaButton.setPreferredSize(btnDimension);
        maHoaBatDoiXungPanel.add(rsaButton);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Asymmetric-key Algorithms ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 20), Color.BLACK);
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
        renderUIButton(rsaButton,new Color(146, 57, 60), new Color(94, 34, 68) );
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
        maHoaDoiXungPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));

        desButton = new JButton(Constants.Cipher.DES);
        desButton.setMnemonic(KeyEvent.VK_D);
        desButton.setFont(font);
        desButton.setFocusable(false);
        desButton.setPreferredSize(btnDimension);
        desButton.setActionCommand(Constants.Cipher.DES);
        desButton.addActionListener(this);
        maHoaDoiXungPanel.add(desButton);

        aesButton = new JButton(Constants.Cipher.AES);
        aesButton.setMnemonic(KeyEvent.VK_A);
        aesButton.setFont(font);
        aesButton.setFocusable(false);
        aesButton.setPreferredSize(btnDimension);
        aesButton.setActionCommand(Constants.Cipher.AES);
        aesButton.addActionListener(this);
        maHoaDoiXungPanel.add(aesButton);

        tripleDesButton = new JButton(Constants.Cipher.TRIPLE_DES);
        tripleDesButton.setMnemonic(KeyEvent.VK_S);
        tripleDesButton.setFont(font);
        tripleDesButton.setFocusable(false);
        tripleDesButton.setPreferredSize(btnDimension);
        tripleDesButton.setActionCommand(Constants.Cipher.TRIPLE_DES);
        tripleDesButton.addActionListener(this);
        maHoaDoiXungPanel.add(tripleDesButton);

        blowfishButton = new JButton(Constants.Cipher.BLOWFISH);
        blowfishButton.setMnemonic(KeyEvent.VK_B);
        blowfishButton.setFont(font);
        blowfishButton.setFocusable(false);
        blowfishButton.setPreferredSize(btnDimension);
        blowfishButton.setActionCommand(Constants.Cipher.BLOWFISH);
        blowfishButton.addActionListener(this);
        maHoaDoiXungPanel.add(blowfishButton);

        cast_6Button = new JButton(Constants.Cipher.CAST_6);
        cast_6Button.setMnemonic(KeyEvent.VK_C);
        cast_6Button.setFont(font);
        cast_6Button.setFocusable(false);
        cast_6Button.setPreferredSize(btnDimension);
        cast_6Button.setActionCommand(Constants.Cipher.CAST_6);
        cast_6Button.addActionListener(this);
        maHoaDoiXungPanel.add(cast_6Button);

        twofishButton = new JButton(Constants.Cipher.TWOFISH);
        twofishButton.setMnemonic(KeyEvent.VK_T);
        twofishButton.setFont(font);
        twofishButton.setFocusable(false);
        twofishButton.setPreferredSize(btnDimension);
        twofishButton.setActionCommand(Constants.Cipher.TWOFISH);
        twofishButton.addActionListener(this);
        maHoaDoiXungPanel.add(twofishButton);

        hillButton = new JButton(Constants.Cipher.HILL);
        hillButton.setMnemonic(KeyEvent.VK_L);
        hillButton.setFont(font);
        hillButton.setFocusable(false);
        hillButton.setPreferredSize(btnDimension);
        hillButton.setActionCommand(Constants.Cipher.HILL);
        hillButton.addActionListener(this);
        maHoaDoiXungPanel.add(hillButton);

        vigenereButton = new JButton(Constants.Cipher.VIGENERE);
        vigenereButton.setMnemonic(KeyEvent.VK_V);
        vigenereButton.setFont(font);
        vigenereButton.setFocusable(false);
        vigenereButton.setPreferredSize(btnDimension);
        vigenereButton.setActionCommand(Constants.Cipher.VIGENERE);
        vigenereButton.addActionListener(this);
        maHoaDoiXungPanel.add(vigenereButton);

        //border
        TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Symmetric-key Algorithms ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 20), Color.BLACK);
        titledBorder.setBorder(new LineBorder(Color.BLACK, 4));
        maHoaDoiXungPanel.setBorder(titledBorder);

        panel.add(maHoaDoiXungPanel);
    }


    private void renderUIButton(JButton gradientButton, Color left, Color right){
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

    public static void main(String[] args) {
        new HomeGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
