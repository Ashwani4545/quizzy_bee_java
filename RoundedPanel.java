// =====================================================================
// RoundedPanel.java
// Custom JPanel with rounded corners and optional border.
// Concepts: Class, Inheritance (extends JPanel), Constructors,
//           Method Overriding (paintComponent), Data Types
// =====================================================================

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int radius;
    private Color bg;
    private Color borderColor;
    private final boolean hasBorder;

    // --------------------------------------------------------
    // Constructor 1 — with border color
    // Syllabus: Constructors, Method Overloading
    // --------------------------------------------------------
    public RoundedPanel(int radius, Color bg, Color borderColor) {
        this.radius      = radius;
        this.bg          = bg;
        this.borderColor = borderColor;
        this.hasBorder   = true;
        setOpaque(false);
    }

    // --------------------------------------------------------
    // Constructor 2 — no border
    // Syllabus: Method Overloading
    // --------------------------------------------------------
    public RoundedPanel(int radius, Color bg) {
        this(radius, bg, null);
        this.hasBorder = false;
    }

    // --------------------------------------------------------
    // paintComponent() — draws rounded rectangle background
    // Syllabus: Method Overriding
    // --------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Draw border if requested
        if (hasBorder && borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);
        }

        g2.dispose();
        super.paintComponent(g);
    }
}
