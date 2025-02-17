package org.example.GUI.Auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class FloatingTextField extends JTextField {
    String placeholder;
    boolean isFloating = false;

    public FloatingTextField(String placeholder) {
        this.placeholder = placeholder;
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                isFloating = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                isFloating = !getText().isEmpty();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isFloating || !getText().isEmpty()) {
            g2.setColor(new Color(41, 128, 185));
            g2.setFont(getFont().deriveFont(12f));
            g2.drawString(placeholder, getInsets().left, 15);
        } else if (getText().isEmpty()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(placeholder, getInsets().left, y);
        }
        g2.dispose();
    }
}