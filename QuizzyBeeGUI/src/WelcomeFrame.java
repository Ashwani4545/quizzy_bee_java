// =====================================================================
// WelcomeFrame.java
// The welcome / setup screen. Collects player name, category,
// question count, and shuffle preference.
// Concepts: JFrame, JPanel, JLabel, JTextField, JComboBox,
//           JSpinner, JCheckBox, ActionListener (lambda),
//           Exception Handling, String methods
// =====================================================================

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class WelcomeFrame extends JFrame {

    // ── Widgets ──────────────────────────────────────────────────────
    private JTextField  nameField;
    private JComboBox<String> categoryBox;
    private JSpinner    countSpinner;
    private JCheckBox   shuffleCheck;
    private JLabel      errorLabel;

    // Shared data store
    private QuestionBank bank;

    // --------------------------------------------------------
    // Constructor — builds and shows the Welcome Screen
    // --------------------------------------------------------
    public WelcomeFrame() {
        bank = new QuestionBank();
        setupFrame();
        buildUI();
        setVisible(true);
    }

    // --------------------------------------------------------
    // setupFrame() — configure JFrame properties
    // --------------------------------------------------------
    private void setupFrame() {
        setTitle("QuizzyBee 🐝 — Interactive Quiz System");
        setSize(AppTheme.FRAME_W, AppTheme.FRAME_H);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(AppTheme.BG_DARK);
    }

    // --------------------------------------------------------
    // buildUI() — assembles all panels and components
    // --------------------------------------------------------
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(AppTheme.BG_DARK);
        root.setBorder(new EmptyBorder(30, 50, 30, 50));

        // ---- TOP: Logo + title ----
        root.add(buildHeader(), BorderLayout.NORTH);

        // ---- CENTER: input form ----
        root.add(buildForm(), BorderLayout.CENTER);

        // ---- BOTTOM: start button ----
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    // --------------------------------------------------------
    // buildHeader() — bee emoji, title, subtitle
    // --------------------------------------------------------
    private JPanel buildHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 28, 0));

        JLabel bee = new JLabel("🐝", SwingConstants.CENTER);
        bee.setFont(new Font("SansSerif", Font.PLAIN, 56));
        bee.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("QuizzyBee", SwingConstants.CENTER);
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.ACCENT_GOLD);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Interactive Quiz System", SwingConstants.CENTER);
        sub.setFont(AppTheme.FONT_SUBTITLE);
        sub.setForeground(AppTheme.TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(bee);
        panel.add(Box.createVerticalStrut(6));
        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sub);
        return panel;
    }

    // --------------------------------------------------------
    // buildForm() — input fields in a card panel
    // --------------------------------------------------------
    private JPanel buildForm() {
        RoundedPanel card = new RoundedPanel(16, AppTheme.BG_CARD, AppTheme.BORDER_LINE);
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(28, 36, 28, 36));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 0, 8, 0);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Player Name ---
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        card.add(makeLabel("Your Name"), gbc);

        gbc.gridy = 1;
        nameField = new JTextField();
        styleTextField(nameField, "Enter your name…");
        card.add(nameField, gbc);

        // --- Category ---
        gbc.gridy = 2;
        card.add(makeLabel("Category"), gbc);

        gbc.gridy = 3;
        String[] cats = {"All", "Science", "Math", "Tech", "GK"};
        categoryBox = new JComboBox<>(cats);
        styleComboBox(categoryBox);
        card.add(categoryBox, gbc);

        // --- 2-column row: count + shuffle ---
        gbc.gridy = 4;
        card.add(makeLabel("Number of Questions"), gbc);

        gbc.gridy = 5;
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setOpaque(false);

        SpinnerNumberModel spinModel = new SpinnerNumberModel(10, 5, 20, 1);
        countSpinner = new JSpinner(spinModel);
        styleSpinner(countSpinner);
        row.add(countSpinner);

        row.add(Box.createHorizontalStrut(28));

        shuffleCheck = new JCheckBox("Shuffle Questions");
        shuffleCheck.setFont(AppTheme.FONT_BODY);
        shuffleCheck.setForeground(AppTheme.TEXT_PRIMARY);
        shuffleCheck.setOpaque(false);
        shuffleCheck.setFocusPainted(false);
        shuffleCheck.setSelected(true);
        row.add(shuffleCheck);

        card.add(row, gbc);

        // --- Error label ---
        gbc.gridy = 6;
        errorLabel = new JLabel(" ");
        errorLabel.setFont(AppTheme.FONT_SMALL);
        errorLabel.setForeground(AppTheme.RED_BAD);
        card.add(errorLabel, gbc);

        // Wrap card in a panel so it's centred
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(card, BorderLayout.CENTER);
        return wrap;
    }

    // --------------------------------------------------------
    // buildFooter() — Start Quiz button
    // --------------------------------------------------------
    private JPanel buildFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));

        StyledButton startBtn = new StyledButton("  Start Quiz  →", StyledButton.Style.PRIMARY);
        startBtn.setPreferredSize(new Dimension(200, 46));

        // Lambda ActionListener — Syllabus: Lambda expressions
        startBtn.addActionListener(e -> handleStart());

        panel.add(startBtn);
        return panel;
    }

    // --------------------------------------------------------
    // handleStart() — validates input, opens QuizFrame
    // Syllabus: Exception Handling, String methods, if/else
    // --------------------------------------------------------
    private void handleStart() {
        // Validate name — Syllabus: String methods (.trim, .isEmpty)
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            errorLabel.setText("⚠  Please enter your name.");
            nameField.requestFocus();
            return;
        }

        // Validate count — Syllabus: Exception Handling (try-catch)
        int count;
        try {
            count = (Integer) countSpinner.getValue();
        } catch (Exception ex) {
            errorLabel.setText("⚠  Invalid question count.");
            return;
        }

        errorLabel.setText(" ");

        String category = (String) categoryBox.getSelectedItem();
        boolean doShuffle = shuffleCheck.isSelected();

        // Build question list — Syllabus: ArrayList
        if (doShuffle) bank.shuffle();
        ArrayList<Question> pool = bank.getByCategory(category);
        ArrayList<Question> quizQ = bank.getSubset(pool, count);

        if (quizQ.isEmpty()) {
            errorLabel.setText("⚠  No questions available for selected category.");
            return;
        }

        // Open quiz window and close welcome
        new QuizFrame(name, quizQ);
        dispose();
    }

    // ── Style helpers ────────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(AppTheme.FONT_SMALL);
        lbl.setForeground(AppTheme.TEXT_MUTED);
        return lbl;
    }

    private void styleTextField(JTextField tf, String placeholder) {
        tf.setFont(AppTheme.FONT_BODY);
        tf.setForeground(AppTheme.TEXT_PRIMARY);
        tf.setBackground(AppTheme.BG_PANEL);
        tf.setCaretColor(AppTheme.ACCENT_GOLD);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
    }

    private void styleComboBox(JComboBox<String> cb) {
        cb.setFont(AppTheme.FONT_BODY);
        cb.setForeground(AppTheme.TEXT_PRIMARY);
        cb.setBackground(AppTheme.BG_PANEL);
        cb.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1));
    }

    private void styleSpinner(JSpinner sp) {
        sp.setFont(AppTheme.FONT_BODY);
        sp.setForeground(AppTheme.TEXT_PRIMARY);
        sp.setBackground(AppTheme.BG_PANEL);
        sp.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_LINE, 1));
        sp.setPreferredSize(new Dimension(90, 34));
        JComponent editor = sp.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(AppTheme.BG_PANEL);
            tf.setForeground(AppTheme.TEXT_PRIMARY);
            tf.setFont(AppTheme.FONT_BODY);
        }
    }
}
