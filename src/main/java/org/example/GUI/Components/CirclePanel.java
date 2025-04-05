package org.example.GUI.Components;

import javax.swing.*;
import java.awt.*;


// Panel tùy chỉnh hiển thị nền mặc định và hình tròn bên trong
class CirclePanel extends JPanel {
    private Color circleColor = Color.GRAY;
    private String circleText = "";
    private Color circleTextColor = Color.WHITE;
    private int circleSize = 20; // Kích thước hình tròn (có thể điều chỉnh)

    public CirclePanel(Color backgroundColor) {
        setBackground(backgroundColor); // Sử dụng màu nền mặc định của cell
        setOpaque(true); // Hiển thị màu nền
    }

    public void setCircleColor(Color color) {
        this.circleColor = color;
        repaint();
    }

    public void setCircleText(String text) {
        this.circleText = text;
        repaint();
    }

    public void setCircleTextColor(Color color) {
        this.circleTextColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Vẽ nền mặc định trước

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ hình tròn ở giữa panel
        int x = (getWidth() - circleSize) / 2;
        int y = (getHeight() - circleSize) / 2;

        g2d.setColor(circleColor);
        g2d.fillOval(x, y, circleSize, circleSize);

        // Vẽ text ở giữa hình tròn
        if (circleText != null && !circleText.isEmpty()) {
            g2d.setColor(circleTextColor);
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(circleText);
            int textHeight = fm.getHeight();

            g2d.drawString(circleText,
                    x + (circleSize - textWidth) / 2,
                    y + (circleSize - textHeight) / 2 + fm.getAscent());
        }

        g2d.dispose();
    }
}