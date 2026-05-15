// =====================================================================
// QuizFrame.java
// Main quiz screen: shows one question at a time, radio buttons,
// progress bar, live score, and feedback flash panel.
// Concepts: JFrame, JRadioButton, ButtonGroup, JProgressBar,
//           JLabel, ActionListener (lambda), for loop, if/else,
//           ArrayList, Arrays, Timer, Exception Handling
// =====================================================================

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class QuizFrame extends JFrame {

    // ── Quiz state ────────────────────────────────────────────────────
    private final ArrayList<Question> questions;  // Syllabus: ArrayList
    private final ScoreTracker        score;
    private int                 currentIndex = 0;
    private int                 selectedOption = -1;  // -1 = none selected

    // ── Widgets ───────────────────────────────────────────────────────
    private JLabel qNumberLabel;
    private JLabel categoryBadge;
    private JLabel questionLabel;
    private JRadioButton[] optionBtns;    // Syllabus: Arrays
    private ButtonGroup btnGroup;
    private JProgressBar progressBar;
    private JLabel scoreLabel;
    private JLabel feedbackLabel;
    private StyledButton submitBtn;
    private JPanel feedbackPanel;
    private JPanel optionsPanel;
    private Timer feedbackTimer; // javax.swing.Timer

    // --------------------------------------------------------
    // Constructor
    // --------------------------------------------------------
    public QuizFrame(String playerName, ArrayList<Question> questions) {
        this.questions = questions;
        this.score = new ScoreTracker(playerName, questions.size());
        setupFrame();
        buildUI();
        loadQuestion(0);
        setVisible(true);
    }

    // --------------------------------------------------------
    // setupFrame()
    // --------------------------------------------------------
    private void setupFrame() {
        setTitle("QuizzyBee 🐝 — Quiz");
        setSize(AppTheme.FRAME_W, AppTheme.FRAME_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(AppTheme.BG_DARK);
    }

    // --------------------------------------------------------
    // buildUI() — assembles the quiz layout
    // --------------------------------------------------------
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(AppTheme.BG_DARK);

        root.add(buildTopBar(),BorderLayout.NORTH);
        root.add(buildCenter(),BorderLayout.CENTER);
        root.add(buildBottomBar(),BorderLayout.SOUTH);

        setContentPane(root);
    }

    // --------------------------------------------------------
    // buildTopBar() — progress bar + score counter
    // --------------------------------------------------------
    private JPanel buildTopBar() {
        JPanel panel = new JPanel(new BorderLayout(16, 0));
        panel.setBackground(AppTheme.BG_CARD);
        panel.setBorder(new EmptyBorder(14, 28, 14, 28));

        // Left: question counter
        qNumberLabel = new JLabel("Q1 / " + questions.size());
        qNumberLabel.setFont(AppTheme.FONT_BODY);
        qNumberLabel.setForeground(AppTheme.TEXT_MUTED);

        // Center: progress bar
        progressBar = new JProgressBar(0, questions.size());
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setBackground(AppTheme.BG_PANEL);
        progressBar.setForeground(AppTheme.ACCENT_GOLD);
        progressBar.setBorder(null);
        progressBar.setPreferredSize(new Dimension(400, 8));

        // Right: live score
        scoreLabel = new JLabel("Score: 0 / " + questions.size());
        scoreLabel.setFont(AppTheme.FONT_BODY);
        scoreLabel.setForeground(AppTheme.ACCENT_GOLD);

        panel.add(qNumberLabel,  BorderLayout.WEST);
        panel.add(progressBar,   BorderLayout.CENTER);
        panel.add(scoreLabel,    BorderLayout.EAST);
        return panel;
    }

    // --------------------------------------------------------
    // buildCenter() — question card + options
    // --------------------------------------------------------
    private JPanel buildCenter() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(AppTheme.BG_DARK);
        wrap.setBorder(new EmptyBorder(20, 36, 10, 36));

        // Question card
        RoundedPanel qCard = new RoundedPanel(16, AppTheme.BG_CARD, AppTheme.BORDER_LINE);
        qCard.setLayout(new BorderLayout());
        qCard.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Category badge + question text header row
        JPanel qHeader = new JPanel(new BorderLayout(12, 0));
        qHeader.setOpaque(false);

        categoryBadge = new JLabel(" Science ");
        categoryBadge.setFont(AppTheme.FONT_SMALL);
        categoryBadge.setForeground(Color.WHITE);
        categoryBadge.setOpaque(true);
        categoryBadge.setBackground(AppTheme.CAT_SCIENCE);
        categoryBadge.setBorder(new EmptyBorder(3, 10, 3, 10));

        JPanel badgeWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        badgeWrap.setOpaque(false);
        badgeWrap.add(categoryBadge);

        questionLabel = new JLabel("<html><body style='width:520px'>Question text here</body></html>");
        questionLabel.setFont(AppTheme.FONT_QUESTION);
        questionLabel.setForeground(AppTheme.TEXT_PRIMARY);

        qCard.add(badgeWrap,    BorderLayout.NORTH);
        qCard.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        qCard.add(questionLabel, BorderLayout.SOUTH);

        // Options panel
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(new EmptyBorder(12, 0, 0, 0));

        optionBtns = new JRadioButton[4];   // Syllabus: Arrays
        btnGroup   = new ButtonGroup();

        // for loop — Syllabus: Loops (for)
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            optionBtns[i] = buildOptionButton();
            btnGroup.add(optionBtns[i]);

            // Lambda ActionListener — Syllabus: Lambda
            optionBtns[i].addActionListener(e -> {
                selectedOption = idx + 1;
                highlightSelected(idx);
            });

            optionsPanel.add(optionBtns[i]);
            optionsPanel.add(Box.createVerticalStrut(8));
        }

        // Feedback panel (hidden until answer submitted)
        feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setOpaque(false);
        feedbackPanel.setBorder(new EmptyBorder(8, 0, 0, 0));

        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(AppTheme.FONT_HEADING);
        feedbackLabel.setOpaque(true);
        feedbackLabel.setBackground(AppTheme.BG_DARK);
        feedbackLabel.setForeground(AppTheme.TEXT_MUTED);
        feedbackLabel.setBorder(new EmptyBorder(10, 16, 10, 16));
        feedbackLabel.setVisible(false);
        feedbackPanel.add(feedbackLabel);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(AppTheme.BG_DARK);
        center.add(qCard);
        center.add(optionsPanel);
        center.add(feedbackPanel);

        wrap.add(center, BorderLayout.CENTER);
        return wrap;
    }

    // --------------------------------------------------------
    // buildBottomBar() — Submit button
    // --------------------------------------------------------
    private JPanel buildBottomBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
        panel.setBackground(AppTheme.BG_DARK);

        submitBtn = new StyledButton("Submit Answer", StyledButton.Style.PRIMARY);
        submitBtn.setPreferredSize(new Dimension(200, 44));

        // Lambda — Syllabus: Lambda expressions
        submitBtn.addActionListener(e -> handleSubmit());

        panel.add(submitBtn);
        return panel;
    }

    // --------------------------------------------------------
    // buildOptionButton() — creates a styled radio button row
    // --------------------------------------------------------
    private JRadioButton buildOptionButton() {
        JRadioButton rb = new JRadioButton();
        rb.setFont(AppTheme.FONT_OPTION);
        rb.setForeground(AppTheme.TEXT_PRIMARY);
        rb.setBackground(AppTheme.BG_PANEL);
        rb.setOpaque(true);
        rb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1),
            new EmptyBorder(10, 14, 10, 14)
        ));
        rb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setFocusPainted(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return rb;
    }

    // --------------------------------------------------------
    // loadQuestion() — populates widgets for question at index
    // Syllabus: ArrayList, Arrays, String methods
    // --------------------------------------------------------
    private void loadQuestion(int index) {
        selectedOption = -1;
        btnGroup.clearSelection();

        Question q = questions.get(index);   // fetch questions from ArrayList

        // Update labels
        qNumberLabel.setText("Q" + (index + 1) + " / " + questions.size());
        progressBar.setValue(index);
        scoreLabel.setText("Score: " + score.getCorrectAnswers() + " / " + questions.size());

        // Category badge
        categoryBadge.setText("  " + q.getCategory() + "  ");
        categoryBadge.setBackground(AppTheme.categoryColor(q.getCategory()));

        // Question text
        questionLabel.setText("<html><body style='width:520px'>" + q.getQuestionText() + "</body></html>");

        // Options — for loop — Syllabus: Loops
        String[] opts = q.getOptions();
        String[] prefixes = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            optionBtns[i].setText("  " + prefixes[i] + ".  " + opts[i]);
            optionBtns[i].setBackground(AppTheme.BG_PANEL);
            optionBtns[i].setForeground(AppTheme.TEXT_PRIMARY);
            optionBtns[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1),
                new EmptyBorder(10, 14, 10, 14)
            ));
            optionBtns[i].setEnabled(true);
        }

        // Reset feedback
        feedbackLabel.setVisible(false);
        submitBtn.setEnabled(true);
    }

    // --------------------------------------------------------
    // highlightSelected() — visually highlights chosen option
    // --------------------------------------------------------
    private void highlightSelected(int idx) {
        for (int i = 0; i < 4; i++) {
            // Syllabus: if/else
            if (i == idx) {
                optionBtns[i].setBackground(AppTheme.OPTION_SEL);
                optionBtns[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppTheme.ACCENT_GOLD, 2),
                    new EmptyBorder(10, 14, 10, 14)
                ));
            } else {
                optionBtns[i].setBackground(AppTheme.BG_PANEL);
                optionBtns[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1),
                    new EmptyBorder(10, 14, 10, 14)
                ));
            }
        }
    }

    // --------------------------------------------------------
    // handleSubmit() — checks answer, shows feedback, advances
    // Syllabus: if/else, Exception Handling, ArrayList
    // --------------------------------------------------------
    private void handleSubmit() {
        // Validate selection — Syllabus: if/else
        if (selectedOption == -1) {
            feedbackLabel.setText("  ⚠  Please select an answer first!  ");
            feedbackLabel.setBackground(new Color(80, 50, 20));
            feedbackLabel.setForeground(AppTheme.ACCENT_GOLD);
            feedbackLabel.setVisible(true);
            return;
        }

        Question q = questions.get(currentIndex);
        boolean correct = q.isCorrect(selectedOption);

        // Disable all options
        for (JRadioButton rb : optionBtns) rb.setEnabled(false);
        submitBtn.setEnabled(false);

        // Color correct answer GREEN, wrong answer RED
        int correctIdx = q.getCorrectOption() - 1;
        int selectedIdx = selectedOption - 1;

        optionBtns[correctIdx].setBackground(new Color(30, 80, 50));
        optionBtns[correctIdx].setForeground(AppTheme.GREEN_GOOD);
        optionBtns[correctIdx].setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.GREEN_GOOD, 2),
            new EmptyBorder(10, 14, 10, 14)
        ));

        if (!correct) {
            optionBtns[selectedIdx].setBackground(new Color(80, 20, 20));
            optionBtns[selectedIdx].setForeground(AppTheme.RED_BAD);
            optionBtns[selectedIdx].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.RED_BAD, 2),
                new EmptyBorder(10, 14, 10, 14)
            ));
        }

        // Feedback message — Syllabus: if/else
        if (correct) {
            feedbackLabel.setText("  ✅  Correct! Well done!  ");
            feedbackLabel.setBackground(new Color(20, 60, 35));
            feedbackLabel.setForeground(AppTheme.GREEN_GOOD);
        } else {
            feedbackLabel.setText("  ❌  Wrong! Correct: " + q.getCorrectAnswerText() + "  ");
            feedbackLabel.setBackground(new Color(70, 15, 15));
            feedbackLabel.setForeground(AppTheme.RED_BAD);
        }
        feedbackLabel.setVisible(true);

        // Record in tracker — Syllabus: Methods
        score.recordAnswer(
            currentIndex + 1, correct,
            q.getQuestionText(),
            q.getOptions()[selectedIdx],
            q.getCorrectAnswerText()
        );

        // After 1.5 seconds, advance to next question
        // Syllabus: Timer (javax.swing), Lambda
        feedbackTimer = new Timer(1500, e -> advanceQuestion());
        feedbackTimer.setRepeats(false);
        feedbackTimer.start();
    }

    // --------------------------------------------------------
    // advanceQuestion() — moves to next or opens result
    // Syllabus: if/else, ArrayList, Methods
    // --------------------------------------------------------
    private void advanceQuestion() {
        currentIndex++;
        if (currentIndex < questions.size()) {   // Syllabus: if/else, Operators
            loadQuestion(currentIndex);
        } else {
            // Quiz complete — open ResultFrame
            progressBar.setValue(questions.size());
            // Ensure the ResultFrame is shown. Previously the new instance was created
            // but not displayed which caused it to be ignored.
            ResultFrame resultFrame = new ResultFrame(score);
            resultFrame.setVisible(true);
            dispose();
        }
    }
}
