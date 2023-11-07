package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class CustomRadioButton extends JRadioButton {
    public CustomRadioButton(String name, Color backgroundColor, Font font){
        super(name);
        setBackground(backgroundColor);
        setFont(font);
        setFocusable(false);
    }
}
