// =====================================================================
// StyledButton.java
// Custom JButton with rounded corners, hover effects, and theming.
// Concepts: Class, Inheritance, Constructors, Method Overriding,
//           Data Types, if/else
// =====================================================================

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StyledButton extends JButton {

    // Button style variants
    public enum Style { PRIMARY, SECONDARY, DANGER, SUCCESS }

    private Color normalColor;
    private Color hoverColor;
    private Color pressColor;
    private Color textColor;
    private boolean isHovered  = false;
    private boolean isPressed  = false;
    private int     radius     = 12;

    // --------------------------------------------------------
    // Constructor — builds button based on Style
    // Syllabus: Constructors, switch-case (via if/else for color)
    // --------------------------------------------------------
    public StyledButton(String text, Style style) {
        super(text);

        // Syllabus: switch-case
        switch (style) {
            case PRIMARY:
                normalColor = AppTheme.ACCENT_GOLD;
                hoverColor  = new Color(255, 210, 80);
                pressColor  = new Color(220, 160, 20);
                textColor   = new Color(20, 20, 30);
                break;
            case SECONDARY:
                normalColor = AppTheme.BG_PANEL;
                hoverColor  = AppTheme.OPTION_HOVER;
                pressColor  = AppTheme.BORDER_LINE;
                textColor   = AppTheme.TEXT_PRIMARY;
                break;
            case DANGER:
                normalColor = AppTheme.RED_BAD;
                hoverColor  = new Color(255, 100, 110);
                pressColor  = new Color(200, 50, 60);
                textColor   = Color.WHITE;
                break;
            case SUCCESS:
                normalColor = AppTheme.GREEN_GOOD;
                hoverColor  = new Color(80, 230, 140);
                pressColor  = new Color(30, 180, 90);
                textColor   = new Color(10, 30, 20);
                break;
            default:
                normalColor = AppTheme.BG_PANEL;
                hoverColor  = AppTheme.OPTION_HOVER;
                pressColor  = AppTheme.BORDER_LINE;
                textColor   = AppTheme.TEXT_PRIMARY;
        }

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFont(AppTheme.FONT_BTN);
        setForeground(textColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover & press listeners — Syllabus: Lambda expressions
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { isHovered = true;  repaint(); }
            @Override public void mouseExited(MouseEvent e)   { isHovered = false; isPressed = false; repaint(); }
            @Override public void mousePressed(MouseEvent e)  { isPressed = true;  repaint(); }
            @Override public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); }
        });
    }

    // --------------------------------------------------------
    // paintComponent() — draws the styled button
    // --------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Pick colour based on state — Syllabus: if/else
        Color current;
        if      (isPressed) current = pressColor;
        else if (isHovered) current = hoverColor;
        else                current = normalColor;

        g2.setColor(current);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius * 2, radius * 2);

        // Draw text centred
        g2.setFont(getFont());
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
        int ty = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), tx, ty);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        return new Dimension(d.width + 30, d.height + 12);
    }
}
