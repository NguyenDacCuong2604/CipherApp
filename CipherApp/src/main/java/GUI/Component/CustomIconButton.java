package GUI.Component;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

public class CustomIconButton extends JButton {
    public CustomIconButton(Icon icon, Dimension dimension, Color backgroundColor){
        setIcon(icon);
        setPreferredSize(dimension);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusable(false);
        setBorderPainted(false);
        setBackground(backgroundColor);
    }
}
