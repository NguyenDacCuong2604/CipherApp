package GUI.Component;

import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JComboBox {
    public CustomComboBox(String[] list, Dimension dimension, Font font, Color color){
        super(list);
        setPreferredSize(dimension);
        setFont(font);
        setBackground(color);
        setFocusable(false);
    }
}
