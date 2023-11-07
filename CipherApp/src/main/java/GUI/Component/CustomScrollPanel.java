package GUI.Component;

import constant.Constants;

import javax.swing.*;
import java.awt.*;

public class CustomScrollPanel extends JScrollPane {
    public CustomScrollPanel(JComponent component, Dimension dimension, Color foregroundColor){
        super(component);
        setPreferredSize(dimension);
        setForeground(foregroundColor);
    }
}
