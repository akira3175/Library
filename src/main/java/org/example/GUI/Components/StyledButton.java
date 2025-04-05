package org.example.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A custom styled button component with modern appearance and hover effects.
 * Can be easily reused across the application for consistent UI.
 */
public class StyledButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;

    /**
     * Creates a new styled button with the specified text and background color.
     *
     * @param text The text to display on the button
     * @param backgroundColor The background color of the button
     */
    public StyledButton(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.hoverColor = darkenColor(backgroundColor, 0.2f);
        this.pressedColor = darkenColor(backgroundColor, 0.3f);

        setupButton();
    }

    /**
     * Creates a new styled button with the specified text, background color, and size.
     *
     * @param text The text to display on the button
     * @param backgroundColor The background color of the button
     * @param width The width of the button
     * @param height The height of the button
     */
    public StyledButton(String text, Color backgroundColor, int width, int height) {
        this(text, backgroundColor);
        setPreferredSize(new Dimension(width, height));
    }

    private void setupButton() {
        setFont(new Font(getFont().getFamily(), Font.BOLD, 13));
        setBackground(backgroundColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setBackground(hoverColor);
                } else {
                    setBackground(backgroundColor);
                }
            }
        });
    }

    /**
     * Darkens a color by the specified factor.
     *
     * @param color The color to darken
     * @param factor The factor by which to darken (0.0 to 1.0)
     * @return The darkened color
     */
    private Color darkenColor(Color color, float factor) {
        int r = Math.max(0, (int)(color.getRed() * (1 - factor)));
        int g = Math.max(0, (int)(color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int)(color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }

    /**
     * Sets a new background color for the button and updates hover/pressed colors.
     *
     * @param backgroundColor The new background color
     */
    public void setButtonBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.hoverColor = darkenColor(backgroundColor, 0.2f);
        this.pressedColor = darkenColor(backgroundColor, 0.3f);
        setBackground(backgroundColor);
    }
}
