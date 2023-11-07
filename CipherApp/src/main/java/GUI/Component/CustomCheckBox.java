package GUI.Component;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

public class CustomCheckBox extends JCheckBox {
    public CustomCheckBox(String text, Color backgroundColor, Font font){
        setText(text);
        setFocusable(false);
        setBackground(backgroundColor);
        setFont(font);
    }
}
