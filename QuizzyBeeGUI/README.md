# 🐝 QuizzyBee — Interactive Quiz System (Swing GUI)

A fully GUI-based desktop quiz application built with **Core Java + Java Swing**.
No external libraries. No database. No web tech. Pure Java SE.

---

## 📁 Project Structure

```
QuizzyBeeGUI/
│
├── src/
│   ├── QuizzyBeeApp.java     ← Entry point (main method)
│   ├── WelcomeFrame.java     ← Welcome / setup screen
│   ├── QuizFrame.java        ← Quiz questions screen
│   ├── ResultFrame.java      ← Score & results screen
│   ├── AppTheme.java         ← Central design system (colors, fonts)
│   ├── RoundedPanel.java     ← Custom JPanel with rounded corners
│   ├── StyledButton.java     ← Custom JButton with hover effects
│   ├── Question.java         ← MCQ question model
│   ├── QuestionBank.java     ← 20 questions in ArrayList
│   └── ScoreTracker.java     ← Score, grade, file save logic
│
├── run.bat                   ← Windows: compile & run
├── run.sh                    ← Mac/Linux: compile & run
└── README.md
```

---

## 🚀 How to Run

### Option 1 — Windows (Double-click)
1. Make sure **Java JDK** is installed (`javac -version` in CMD)
2. Double-click **`run.bat`**

### Option 2 — Mac / Linux (Terminal)
```bash
chmod +x run.sh
./run.sh
```

### Option 3 — Manual (Any OS)
```bash
cd src
javac *.java
java QuizzyBeeApp
```

### Option 4 — IntelliJ IDEA / Eclipse
1. Open the `src/` folder as a project
2. Set `QuizzyBeeApp` as the Run Configuration main class
3. Click ▶ Run

---

## 🎮 Features

| Screen | Features |
|---|---|
| **Welcome** | Name entry, category picker, question count, shuffle toggle |
| **Quiz** | Progress bar, live score, category badge, radio buttons, colour feedback |
| **Results** | Grade (A+ to F), score, percentage, full answer log, save to .txt |

---

## 📚 Java Syllabus Topics Covered

| Topic | Location |
|---|---|
| Basic Java structure / main() | QuizzyBeeApp.java |
| Data types, variables, operators | All model classes |
| if / else | ScoreTracker, QuizFrame, StyledButton |
| switch-case | ScoreTracker.getMotivation(), StyledButton styles |
| Loops (for, while, for-each) | QuizFrame, QuestionBank, ScoreTracker |
| Arrays | Question.java (String[] options), QuizFrame (JRadioButton[]) |
| ArrayList | QuestionBank, ScoreTracker |
| String methods | WelcomeFrame, QuestionBank |
| Constructors & Methods | All classes |
| Method Overloading | Question (2 constructors), ScoreTracker.recordAnswer() |
| toString() | Question, ScoreTracker |
| Exception Handling | WelcomeFrame, QuizzyBeeApp, ScoreTracker |
| File Handling | ScoreTracker.saveToFile() — FileWriter + BufferedWriter |
| Lambda Expressions | All ActionListeners, Collections.sort() |

---

## Requirements

- Java JDK 8 or higher
- No other dependencies needed
