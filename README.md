# 🐝 QuizzyBee — Interactive Java Quiz System

> A dark-themed, multi-category multiple-choice quiz application built with Java Swing. Designed to demonstrate core Java programming concepts in a polished, interactive GUI.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots & UI Flow](#screenshots--ui-flow)
- [Project Structure](#project-structure)
- [Class Reference](#class-reference)
- [Java Concepts Demonstrated](#java-concepts-demonstrated)
- [Quiz Categories & Questions](#quiz-categories--questions)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Customization Guide](#customization-guide)
- [Contributing](#contributing)

---

## Overview

**QuizzyBee** is a fully functional desktop quiz game built entirely in Java using the `javax.swing` GUI toolkit. It features a sleek dark theme, category filtering, randomized question ordering, live score tracking, instant visual feedback, and a detailed results screen with question-by-question review.

The project is intentionally structured to serve as a learning resource — every class and method is annotated with the specific Java syllabus concept it demonstrates.

---

## Features

- **Dark, polished UI** — custom color theme (`AppTheme`), rounded card panels, and styled buttons throughout
- **4 Quiz Categories** — Science, Math, Technology, and General Knowledge (or "All" for a mixed quiz)
- **Configurable question count** — choose anywhere from 5 to 20 questions per session
- **Shuffle mode** — randomizes question order on every run
- **Live progress bar** — updates with each answered question
- **Live score counter** — updates in real time in the top bar
- **Color-coded feedback** — correct answers flash green, wrong answers flash red, with a 1.5-second delay before auto-advancing
- **Detailed results screen** — shows final score, percentage, performance badge, and a full per-question answer review
- **Result export** — saves quiz results to a `.txt` file (`as_QuizzyBee_Result.txt`)
- **Cross-platform** — runs on any system with JDK 8+

---

## Screenshots & UI Flow

```
┌─────────────────────┐
│   Welcome Screen    │  ← Enter name, pick category, set count, enable shuffle
│   (WelcomeFrame)    │
└────────┬────────────┘
         │ Start Quiz
         ▼
┌─────────────────────┐
│    Quiz Screen      │  ← One question at a time, radio buttons, progress bar
│    (QuizFrame)      │
└────────┬────────────┘
         │ After last question
         ▼
┌─────────────────────┐
│   Results Screen    │  ← Score, badge, per-question review, save to file
│   (ResultFrame)     │
└─────────────────────┘
```

---

## Project Structure

```
quizzy_bee_java/
│
├── QuizzyBeeApp.java       # Entry point — launches WelcomeFrame on EDT
├── WelcomeFrame.java       # Setup screen: name, category, count, shuffle
├── QuizFrame.java          # Main quiz screen: questions, options, feedback
├── ResultFrame.java        # Results screen: score, review, file export
│
├── Question.java           # Data model for a single MCQ question
├── QuestionBank.java       # Stores all 20 questions; supports filter & shuffle
├── ScoreTracker.java       # Tracks correct/incorrect answers and player data
│
├── AppTheme.java           # Centralized color palette, fonts, and dimensions
├── StyledButton.java       # Custom JButton with PRIMARY / SECONDARY styles
├── RoundedPanel.java       # Custom JPanel with rounded corners and border
│
├── as_QuizzyBee_Result.txt # Auto-generated result file after each quiz session
│
└── QuizzyBeeGUI/           # IDE project folder (NetBeans / IntelliJ)
    └── ...
```

---

## Class Reference

### `QuizzyBeeApp.java`
The application entry point. Sets the cross-platform Look & Feel, applies dark UI manager overrides (background, text colors for dialogs), and launches `WelcomeFrame` on the Swing Event Dispatch Thread (EDT) using `SwingUtilities.invokeLater()`.

---

### `WelcomeFrame.java`
The setup screen where the player configures their quiz session.

**Key widgets:**
- `JTextField` — player name input with validation
- `JComboBox<String>` — category selector (All / Science / Math / Tech / GK)
- `JSpinner` — question count (5–20, default 10)
- `JCheckBox` — toggle for shuffle mode
- `JLabel` — inline error display
- `StyledButton` — "Start Quiz →" launches `QuizFrame` and disposes itself

**Validation logic:** trims whitespace from the name field, confirms a non-empty name, catches spinner cast exceptions, and checks that at least one question exists in the selected category before proceeding.

---

### `QuizFrame.java`
The main quiz screen. Displays questions one at a time and manages the full quiz flow.

**Key widgets:**
- `JLabel` (question number, score, feedback, category badge)
- `JProgressBar` — fills as questions are answered
- `JRadioButton[]` (array of 4) inside a `ButtonGroup`
- `JPanel` (feedback flash panel) — shown after each answer
- `javax.swing.Timer` — waits 1.5 seconds then auto-advances to the next question

**Answer flow:**
1. Player selects a radio button → `selectedOption` is set and the chosen option is highlighted in gold
2. Player clicks "Submit Answer"
3. Correct answer is highlighted green; if wrong, selected answer is highlighted red
4. Feedback label appears ("✅ Correct!" or "❌ Wrong! Correct: …")
5. All options are disabled, preventing re-selection
6. After 1.5 seconds the timer fires → advances to next question or opens `ResultFrame`

---

### `ResultFrame.java`
Displays the final quiz results after all questions are answered.

**Sections:**
- Score summary (e.g., "8 / 10") with a large display
- Percentage and performance badge (Excellent / Good / Keep Practicing / etc.)
- Scrollable per-question review table showing: question text, player's answer, correct answer, and a ✅/❌ indicator
- "Save Results" button — writes the full summary to `as_QuizzyBee_Result.txt`
- "Play Again" button — relaunches `WelcomeFrame`

---

### `Question.java`
Plain data model (POJO) for a single multiple-choice question.

| Field | Type | Description |
|---|---|---|
| `questionText` | `String` | The question prompt |
| `options` | `String[]` | Array of 4 answer choices |
| `correctOption` | `int` | 1-indexed correct answer (1–4) |
| `category` | `String` | "Science", "Math", "Tech", or "GK" |

**Key methods:** `isCorrect(int selected)`, `getCorrectAnswerText()`, `getOptions()`, `getCategory()`

---

### `QuestionBank.java`
Stores all 20 built-in MCQ questions in an `ArrayList<Question>`. Supports category filtering and subset selection.

**Key methods:**

| Method | Description |
|---|---|
| `loadQuestions()` | Populates the bank with 5 questions per category |
| `shuffle()` | Randomizes question order using `Collections.shuffle()` and a lambda sort |
| `getByCategory(String)` | Returns a filtered `ArrayList` for the chosen category |
| `getSubset(ArrayList, int)` | Returns the first `count` questions from a given list |
| `getAll()` | Returns the full unfiltered list |

---

### `ScoreTracker.java`
Tracks the player's performance throughout a quiz session.

**Stored data:**
- Player name
- Total questions
- Correct/incorrect counts
- Per-question record: question text, player's answer, correct answer, and result

**Key methods:** `recordAnswer(...)`, `getCorrectAnswers()`, `getPercentage()`, `getReviewData()`

---

### `AppTheme.java`
Central design system. Defines all colors, fonts, and frame dimensions as `public static final` constants. Any UI component imports these for a consistent look.

**Color constants include:** `BG_DARK`, `BG_CARD`, `BG_PANEL`, `ACCENT_GOLD`, `TEXT_PRIMARY`, `TEXT_MUTED`, `GREEN_GOOD`, `RED_BAD`, `BORDER_LINE`, and per-category badge colors (`CAT_SCIENCE`, `CAT_MATH`, etc.)

**Font constants include:** `FONT_TITLE`, `FONT_SUBTITLE`, `FONT_HEADING`, `FONT_BODY`, `FONT_OPTION`, `FONT_SMALL`, `FONT_QUESTION`

---

### `StyledButton.java`
A custom `JButton` subclass with a `Style` enum (`PRIMARY`, `SECONDARY`). Handles hover and press states via mouse listeners and custom `paintComponent()` rendering for a polished feel.

---

### `RoundedPanel.java`
A custom `JPanel` subclass that overrides `paintComponent()` to draw a rounded rectangle background with an optional border. Used for the question card and form card to give the UI a modern card-based layout.

---

## Java Concepts Demonstrated

This project is annotated throughout to highlight syllabus concepts. Here's a summary of what's used and where:

| Concept | Where Used |
|---|---|
| `main()` method & program entry point | `QuizzyBeeApp.java` |
| Classes & objects | All files |
| Constructors | `Question`, `QuestionBank`, `ScoreTracker`, all Frames |
| Instance variables & encapsulation | `Question.java`, `ScoreTracker.java` |
| `String` methods (`.trim()`, `.isEmpty()`, `.equals()`) | `WelcomeFrame.java` |
| Arrays (`String[]`, `JRadioButton[]`) | `Question.java`, `QuizFrame.java` |
| `ArrayList` & `Collections` | `QuestionBank.java`, `QuizFrame.java` |
| `for` loop & `for-each` loop | `QuizFrame.java`, `QuestionBank.java` |
| `if / else` branching | `QuizFrame.java`, `WelcomeFrame.java` |
| Exception handling (`try-catch`) | `QuizzyBeeApp.java`, `WelcomeFrame.java`, `ResultFrame.java` |
| Lambda expressions | `QuizzyBeeApp.java` (EDT), `QuizFrame.java` (ActionListeners, Timer), `QuestionBank.java` (sort) |
| `javax.swing.Timer` | `QuizFrame.java` |
| `JFrame`, `JPanel`, `JLabel` | All Frame classes |
| `JTextField`, `JComboBox`, `JSpinner`, `JCheckBox` | `WelcomeFrame.java` |
| `JRadioButton` & `ButtonGroup` | `QuizFrame.java` |
| `JProgressBar` | `QuizFrame.java` |
| File I/O (`FileWriter`, `BufferedWriter`) | `ResultFrame.java` |
| Inheritance (`extends JFrame`, `extends JPanel`, `extends JButton`) | All Frame/UI classes |
| `enum` | `StyledButton.java` (`Style` enum) |
| Custom painting (`paintComponent`) | `StyledButton.java`, `RoundedPanel.java` |
| `SwingUtilities.invokeLater()` | `QuizzyBeeApp.java` |
| `GridBagLayout`, `BorderLayout`, `BoxLayout`, `FlowLayout` | Various Frame classes |

---

## Quiz Categories & Questions

The bank contains **20 questions** across 4 categories (5 each):

**🔬 Science**
- Chemical symbol for water
- The Red Planet
- Gas absorbed during photosynthesis
- Number of bones in the adult human body
- Speed of light

**➕ Math**
- Value of Pi (2 decimal places)
- Square root of 144
- Sides of a heptagon
- 15% of 200
- Sum of angles in a triangle

**💻 Technology**
- What CPU stands for
- The language of the web
- What RAM stands for
- Father of computers
- Which of these is NOT a programming language

**🌍 General Knowledge**
- Largest ocean on Earth
- Number of continents
- Capital of Japan
- Fastest land animal
- Players in a cricket team

---

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, NetBeans, Eclipse) or command-line `javac`/`java`

### Clone the Repository

```bash
git clone https://github.com/Ashwani4545/quizzy_bee_java.git
cd quizzy_bee_java
```

### Compile (Command Line)

Compile all `.java` files from the project root:

```bash
javac *.java
```

### Run

```bash
java QuizzyBeeApp
```

### Run with an IDE

1. Open the project folder in IntelliJ IDEA, NetBeans, or Eclipse.
2. Set `QuizzyBeeApp` as the main class.
3. Click **Run**.

> **Note:** Pre-compiled `.class` files are included in the repo, so you can also run `java QuizzyBeeApp` directly without recompiling.

---

## How to Play

1. **Welcome Screen** — Enter your name, choose a category (or "All"), set the number of questions (5–20), and decide whether to shuffle.
2. **Start Quiz** — Click "Start Quiz →". The quiz screen opens.
3. **Answer Questions** — Select one of the four radio button options (A, B, C, D).
4. **Submit** — Click "Submit Answer". The correct answer highlights green; your wrong answer (if any) highlights red. Feedback appears for 1.5 seconds.
5. **Auto-advance** — The next question loads automatically after the feedback delay.
6. **Results** — After the last question, the Results screen shows your score, percentage, a performance badge, and a full review of every question.
7. **Save or Play Again** — Save results to a `.txt` file or click "Play Again" to return to the Welcome Screen.

---

## Customization Guide

### Adding New Questions

Open `QuestionBank.java` and add a new entry inside `loadQuestions()`:

```java
questions.add(new Question(
    "Your question text here?",
    new String[]{"Option A", "Option B", "Option C", "Option D"},
    2,          // correct option index (1–4)
    "Science"   // category: "Science", "Math", "Tech", or "GK"
));
```

### Adding a New Category

1. Add questions with the new category string in `QuestionBank.java`.
2. Add the category name to the `cats` array in `WelcomeFrame.java`.
3. Add a color constant for the badge in `AppTheme.java` and update the `categoryColor()` method.

### Changing the Theme

All colors are defined as constants in `AppTheme.java`. Modify `BG_DARK`, `BG_CARD`, `ACCENT_GOLD`, etc. to change the entire app's appearance from a single file.

### Adjusting Question Count Limits

In `WelcomeFrame.java`, change the `SpinnerNumberModel` parameters:

```java
// SpinnerNumberModel(initial, min, max, step)
SpinnerNumberModel spinModel = new SpinnerNumberModel(10, 5, 20, 1);
```

---

## Contributing

Contributions, bug reports, and feature requests are welcome!

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add: description of change"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

*Built with ☕ Java and 🐝 enthusiasm.*
