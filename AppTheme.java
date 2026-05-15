// =====================================================================
// AppTheme.java
// Central design system — all colors, fonts, dimensions used across
// all Swing frames. Keep everything consistent from one place.
// Concepts: Class, Static fields, Data Types
// =====================================================================

import java.awt.*;

public class AppTheme {

    // ── Colour Palette ────────────────────────────────────────────────
    public static final Color BG_DARK      = new Color(13,  17,  33);   // near-black navy
    public static final Color BG_CARD      = new Color(22,  30,  54);   // card panels
    public static final Color BG_PANEL     = new Color(28,  38,  66);   // slightly lighter
    public static final Color ACCENT_GOLD  = new Color(255, 196,  58);  // bee-gold
    public static final Color ACCENT_AMBER = new Color(255, 152,  0);   // warm amber
    public static final Color GREEN_GOOD   = new Color( 46, 213, 115);  // correct answer
    public static final Color RED_BAD      = new Color(255,  71,  87);  // wrong answer
    public static final Color TEXT_PRIMARY = new Color(240, 245, 255);  // off-white
    public static final Color TEXT_MUTED   = new Color(140, 155, 190);  // secondary text
    public static final Color BORDER_LINE  = new Color( 45,  58,  95);  // subtle border
    public static final Color OPTION_HOVER = new Color( 38,  50,  88);  // radio hover bg
    public static final Color OPTION_SEL   = new Color( 50,  72, 120);  // selected radio bg

    // ── Category Badge Colours ────────────────────────────────────────
    public static final Color CAT_SCIENCE  = new Color( 33, 150, 243);  // blue
    public static final Color CAT_MATH     = new Color(156,  39, 176);  // purple
    public static final Color CAT_TECH     = new Color(  0, 188, 212);  // cyan
    public static final Color CAT_GK       = new Color( 76, 175,  80);  // green
    public static final Color CAT_GENERAL  = new Color(255, 152,   0);  // amber

    // ── Fonts ─────────────────────────────────────────────────────────
    public static final Font FONT_TITLE    = new Font("SansSerif", Font.BOLD,   32);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.PLAIN,  16);
    public static final Font FONT_HEADING  = new Font("SansSerif", Font.BOLD,   20);
    public static final Font FONT_BODY     = new Font("SansSerif", Font.PLAIN,  14);
    public static final Font FONT_SMALL    = new Font("SansSerif", Font.PLAIN,  12);
    public static final Font FONT_MONO     = new Font("Monospaced", Font.PLAIN, 13);
    public static final Font FONT_QUESTION = new Font("SansSerif", Font.BOLD,   17);
    public static final Font FONT_OPTION   = new Font("SansSerif", Font.PLAIN,  15);
    public static final Font FONT_SCORE    = new Font("SansSerif", Font.BOLD,   48);
    public static final Font FONT_GRADE    = new Font("SansSerif", Font.BOLD,   72);
    public static final Font FONT_BTN      = new Font("SansSerif", Font.BOLD,   14);

    // ── Dimensions ────────────────────────────────────────────────────
    public static final int  FRAME_W       = 860;
    public static final int  FRAME_H       = 620;
    public static final int  CORNER_RADIUS = 16;

    // ── Helper — category colour lookup ──────────────────────────────
    public static Color categoryColor(String cat) {
        return switch (cat) {
            case "Science" -> CAT_SCIENCE;
            case "Math"    -> CAT_MATH;
            case "Tech"    -> CAT_TECH;
            case "GK"      -> CAT_GK;
            default        -> CAT_GENERAL;
        };
    }

    // ── Helper — grade colour ─────────────────────────────────────────
    public static Color gradeColor(String grade) {
        return switch (grade) {
            case "A+", "A" -> GREEN_GOOD;
            case "B"        -> new Color(100, 220, 120);
            case "C"        -> ACCENT_GOLD;
            case "D"        -> ACCENT_AMBER;
            default          -> RED_BAD;
        };
    }

    // Private constructor — utility class, not meant to be instantiated
    private AppTheme() {}
}
