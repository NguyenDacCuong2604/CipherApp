package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class ShowToast extends JDialog {
    public ShowToast(Frame parent, String message){
        super(parent, true);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());

        JLabel label = new JLabel(message);
        label.setForeground(Color.BLACK);
        add(label, BorderLayout.CENTER);

        pack();
    }
    public void showToast(int duration) {
        Point frameLocation = getParent().getLocationOnScreen();
        int frameHeight = getParent().getHeight();
        int toastWidth = 300;
        int toastHeight = 25;
        setSize(toastWidth, toastHeight);

        int toastX = (int) (frameLocation.getX() + 30);
        int toastY = (int) (frameLocation.getY() + frameHeight - toastHeight);
        setLocation(toastX, toastY);

        Timer timer = new Timer(duration, e -> dispose());
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }
}
