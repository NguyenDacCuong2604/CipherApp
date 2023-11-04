package GUI.Component;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(new Color(196, 196, 196));
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.setColor(Color.GRAY);
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }

    private JButton createEmptyButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }

}
