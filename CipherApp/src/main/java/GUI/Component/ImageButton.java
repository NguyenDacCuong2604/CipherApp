package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class ImageButton extends JButton {
    private Image image;

    public ImageButton(ImageIcon icon, int width, int height) {
        this.image = icon.getImage();
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth()-6, getHeight()-6, this);
        }
    }
}
