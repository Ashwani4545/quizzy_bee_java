// =====================================================================
// ResultFrame.java
// Final results screen: score, percentage, grade, answer log,
// save to file, and play again / exit buttons.
// Concepts: JFrame, JLabel, JTextArea, JScrollPane, switch-case,
//           if/else, File Handling (via ScoreTracker), String format,
//           ActionListener (lambda)
// =====================================================================

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
// (no event imports needed here)

public class ResultFrame extends JFrame {

    private final ScoreTracker score;

    // --------------------------------------------------------
    // Constructor
    // --------------------------------------------------------
    public ResultFrame(ScoreTracker score) {
        this.score = score;
        setupFrame();
        buildUI();
        setVisible(true);
    }

    // --------------------------------------------------------
    // setupFrame()
    // --------------------------------------------------------
    private void setupFrame() {
        setTitle("QuizzyBee 🐝 — Results");
        setSize(AppTheme.FRAME_W, AppTheme.FRAME_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(AppTheme.BG_DARK);
    }

    // --------------------------------------------------------
    // buildUI()
    // --------------------------------------------------------
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppTheme.BG_DARK);

        root.add(buildHeader(),     BorderLayout.NORTH);
        root.add(buildScoreCard(),  BorderLayout.CENTER);
        root.add(buildButtons(),    BorderLayout.SOUTH);

        setContentPane(root);
    }

    // --------------------------------------------------------
    // buildHeader() — "Quiz Complete" banner
    // --------------------------------------------------------
    private JPanel buildHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppTheme.BG_CARD);
        panel.setBorder(new EmptyBorder(18, 28, 18, 28));

        JLabel trophy = new JLabel("🏆", SwingConstants.CENTER);
        trophy.setFont(new Font("SansSerif", Font.PLAIN, 38));
        trophy.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Quiz Complete!", SwingConstants.CENTER);
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.ACCENT_GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("Player: " + score.getPlayerName(), SwingConstants.CENTER);
        name.setFont(AppTheme.FONT_BODY);
        name.setForeground(AppTheme.TEXT_MUTED);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(trophy);
        panel.add(Box.createVerticalStrut(4));
        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(name);
        return panel;
    }

    // --------------------------------------------------------
    // buildScoreCard() — main results display
    // --------------------------------------------------------
    private JPanel buildScoreCard() {
        JPanel wrap = new JPanel(new BorderLayout(16, 0));
        wrap.setBackground(AppTheme.BG_DARK);
        wrap.setBorder(new EmptyBorder(16, 32, 8, 32));

        // ---- LEFT: big grade circle + score stats ----
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.setPreferredSize(new Dimension(240, 0));

        // Grade display
        String grade = score.getGrade();
        Color  gradeCol = AppTheme.gradeColor(grade);

        JLabel gradeLabel = new JLabel(grade, SwingConstants.CENTER);
        gradeLabel.setFont(AppTheme.FONT_GRADE);
        gradeLabel.setForeground(gradeCol);
        gradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String scoreText = score.getCorrectAnswers() + " / " + score.getTotalQuestions();
        JLabel scoreLbl = new JLabel(scoreText, SwingConstants.CENTER);
        scoreLbl.setFont(AppTheme.FONT_SCORE);
        scoreLbl.setForeground(AppTheme.TEXT_PRIMARY);
        scoreLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        String pctText = String.format("%.1f%%", score.getPercentage());
        JLabel pctLbl = new JLabel(pctText, SwingConstants.CENTER);
        pctLbl.setFont(AppTheme.FONT_HEADING);
        pctLbl.setForeground(AppTheme.TEXT_MUTED);
        pctLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Motivational message — uses ScoreTracker.getMotivation() which has switch-case
        JLabel motiveLbl = new JLabel(
            "<html><div style='text-align:center;width:180px'>"
            + score.getMotivation() + "</div></html>",
            SwingConstants.CENTER
        );
        motiveLbl.setFont(AppTheme.FONT_SMALL);
        motiveLbl.setForeground(gradeCol);
        motiveLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        left.add(Box.createVerticalGlue());
        left.add(gradeLabel);
        left.add(Box.createVerticalStrut(4));
        left.add(scoreLbl);
        left.add(Box.createVerticalStrut(4));
        left.add(pctLbl);
        left.add(Box.createVerticalStrut(10));
        left.add(motiveLbl);
        left.add(Box.createVerticalGlue());

        // ---- RIGHT: scrollable answer log ----
        RoundedPanel logCard = new RoundedPanel(12, AppTheme.BG_CARD, AppTheme.BORDER_LINE);
        logCard.setLayout(new BorderLayout());
        logCard.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel logTitle = new JLabel("Answer Log");
        logTitle.setFont(AppTheme.FONT_BODY);
        logTitle.setForeground(AppTheme.TEXT_MUTED);
        logTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        // Build log text — Syllabus: ArrayList, for-each loop
        StringBuilder sb = new StringBuilder();
        for (String entry : score.getAnswerLog()) {
            sb.append(entry).append("\n\n");
        }

        JTextArea logArea = new JTextArea(sb.toString());
        logArea.setFont(AppTheme.FONT_MONO);
        logArea.setForeground(AppTheme.TEXT_PRIMARY);
        logArea.setBackground(AppTheme.BG_CARD);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBorder(null);

        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBackground(AppTheme.BG_CARD);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.BG_CARD);

        logCard.add(logTitle, BorderLayout.NORTH);
        logCard.add(scroll,   BorderLayout.CENTER);

        wrap.add(left,    BorderLayout.WEST);
        wrap.add(logCard, BorderLayout.CENTER);
        return wrap;
    }

    // --------------------------------------------------------
    // buildButtons() — Save, Play Again, Exit
    // --------------------------------------------------------
    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 14));
        panel.setBackground(AppTheme.BG_DARK);

        // Save button — Syllabus: File Handling, Lambda
        StyledButton saveBtn = new StyledButton("💾  Save Result", StyledButton.Style.SECONDARY);
        saveBtn.setPreferredSize(new Dimension(160, 42));
        saveBtn.addActionListener(e -> {
            String filename = score.saveToFile();
            // Syllabus: String methods (.startsWith for error check)
            if (filename.startsWith("ERROR")) {
                JOptionPane.showMessageDialog(this,
                    "Could not save file:\n" + filename,
                    "Save Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Result saved to:\n" + filename,
                    "Saved ✅", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Play Again — Syllabus: Lambda
        StyledButton playBtn = new StyledButton("🔄  Play Again", StyledButton.Style.SUCCESS);
        playBtn.setPreferredSize(new Dimension(160, 42));
        playBtn.addActionListener(e -> {
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
            dispose();
        });

        // Exit — Syllabus: Lambda
        StyledButton exitBtn = new StyledButton("✕  Exit", StyledButton.Style.DANGER);
        exitBtn.setPreferredSize(new Dimension(120, 42));
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(saveBtn);
        panel.add(playBtn);
        panel.add(exitBtn);
        return panel;
    }
}
