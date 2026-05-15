// =====================================================================
// QuizzyBeeApp.java
// Entry point of the QuizzyBee GUI application.
// Launches the Swing UI on the Event Dispatch Thread (EDT).
// Concepts: main() method, SwingUtilities.invokeLater(), Lambda
// =====================================================================

import javax.swing.*;
import javax.swing.UIManager;

public class QuizzyBeeApp {

    // --------------------------------------------------------
    // main() — program entry point
    // Syllabus: Basic Java structure (main method)
    // --------------------------------------------------------
    public static void main(String[] args) {

        // Apply system look-and-feel for native UI decorations
        // Syllabus: Exception Handling (try-catch)
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            // Dark UI manager overrides for native components
            UIManager.put("OptionPane.background",          AppTheme.BG_CARD);
            UIManager.put("Panel.background",               AppTheme.BG_CARD);
            UIManager.put("OptionPane.messageForeground",   AppTheme.TEXT_PRIMARY);
            UIManager.put("Button.background",              AppTheme.BG_PANEL);
            UIManager.put("Button.foreground",              AppTheme.TEXT_PRIMARY);
        } catch (Exception e) {
            // Silently fall back to default L&F
            System.err.println("L&F warning: " + e.getMessage());
        }

        // Launch UI on the Event Dispatch Thread
        // Syllabus: Lambda expression
        SwingUtilities.invokeLater(() -> new WelcomeFrame());
    }
}
