package GUI.Component;

import constant.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomTextArea extends JTextArea {
    public CustomTextArea(Font font){
        setFont(font);
        setLineWrap(true);
        setWrapStyleWord(true);
    }
}
