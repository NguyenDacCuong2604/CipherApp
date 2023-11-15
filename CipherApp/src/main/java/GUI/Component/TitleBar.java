package GUI.Component;

import constant.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleBar {
    private Point mouseDownCompCoords = null;
    public TitleBar(JFrame jFrame, JPanel mainPanel, String name){
        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(Constants.ColorUI.TEXT_WHITE);

        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        algorithmPanel.setBackground(Constants.ColorUI.TEXT_WHITE);

        JLabel nameLabel = new JLabel("Algorithm");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        algorithmPanel.add(nameLabel);

        JLabel algorithmLabel = new JLabel(name);
        algorithmLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        algorithmPanel.add(algorithmLabel);

        customTitleBar.add(algorithmPanel, BorderLayout.WEST);


        ImageButton minimizeButton = new ImageButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.Image.MINIMIZE_BLACK)), 25, 25);
        minimizeButton.setToolTipText("Minimize");
        ImageButton closeButton = new ImageButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.Image.CLOSE_BLACK)), 25, 25);
        closeButton.setToolTipText("Close");
        //event
        minimizeButton.addActionListener(e -> jFrame.setState(Frame.ICONIFIED));
        closeButton.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            currentFrame.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setBackground(Constants.ColorUI.TEXT_WHITE);
        buttonPanel.add(minimizeButton);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(closeButton);

        customTitleBar.add(buttonPanel, BorderLayout.EAST);

        customTitleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        customTitleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                jFrame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        mainPanel.add(customTitleBar, BorderLayout.NORTH);
    }
    public TitleBar(JFrame jFrame){
        JPanel customTitleBar = new JPanel(new BorderLayout());
        customTitleBar.setBackground(new Color(50, 27, 140));

        ImageButton minimizeButton = new ImageButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.Image.MINIMIZE)), 25, 25);
        minimizeButton.setToolTipText(Constants.Description.MINIMIZE);
        ImageButton closeButton = new ImageButton(new ImageIcon(getClass().getClassLoader().getResource(Constants.Image.CLOSE)), 25, 25);
        closeButton.setToolTipText(Constants.Description.CLOSE);
        //event
        minimizeButton.addActionListener(e -> jFrame.setState(Frame.ICONIFIED));
        closeButton.addActionListener(e -> System.exit(0));
        // Add title text
        JLabel titleText = new JLabel(Constants.Description.CIPHER_APP);
        titleText.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setBackground(new Color(50, 27, 140));
        buttonPanel.add(minimizeButton);
        buttonPanel.add(new JLabel(" "));
        buttonPanel.add(closeButton);

        customTitleBar.add(titleText, BorderLayout.WEST);
        customTitleBar.add(buttonPanel, BorderLayout.EAST);

        customTitleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        customTitleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                jFrame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        jFrame.add(customTitleBar, BorderLayout.NORTH);
    }
}
