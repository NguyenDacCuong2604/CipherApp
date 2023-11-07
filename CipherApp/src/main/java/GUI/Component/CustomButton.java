package GUI.Component;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String title, Dimension dimension, Font font, Color backgroundColor, Color foregroundColor){
        setText(title);
        setPreferredSize(dimension);
        setFont(font);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setFocusable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
