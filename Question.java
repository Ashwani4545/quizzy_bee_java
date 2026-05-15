// =====================================================================
// Question.java
// Model class for a single MCQ question.
// Concepts: Class, Constructors, Method Overloading, toString(),
//           Arrays, Data Types, Encapsulation
// =====================================================================

public class Question {

    // ---------- Fields (Data Types & Variables) ----------
    private String questionText;
    private String[] options;      // Array of 4 option strings
    private int correctOption;     // 1-based index of correct answer
    private String category;       // e.g. "Science", "Math"

    // --------------------------------------------------------
    // Constructor 1 — full (with category)
    // Syllabus: Constructors
    // --------------------------------------------------------
    public Question(String questionText, String[] options, int correctOption, String category) {
        this.questionText  = questionText;
        this.options       = options;
        this.correctOption = correctOption;
        this.category      = category;
    }

    // --------------------------------------------------------
    // Constructor 2 — without category (Method Overloading)
    // Syllabus: Constructors, Method Overloading
    // --------------------------------------------------------
    public Question(String questionText, String[] options, int correctOption) {
        this(questionText, options, correctOption, "General");
    }

    // --------------------------------------------------------
    // isCorrect() — checks if the user's answer matches
    // Syllabus: Methods, Operators (==)
    // --------------------------------------------------------
    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctOption;
    }

    // --------------------------------------------------------
    // Getters — Encapsulation
    // --------------------------------------------------------
    public String   getQuestionText()  { return questionText;  }
    public String[] getOptions()       { return options;       }
    public int      getCorrectOption() { return correctOption; }
    public String   getCategory()      { return category;      }

    // --------------------------------------------------------
    // getCorrectAnswerText() — returns the correct option text
    // --------------------------------------------------------
    public String getCorrectAnswerText() {
        return options[correctOption - 1];
    }

    // --------------------------------------------------------
    // toString() — overrides Object.toString()
    // Syllabus: toString()
    // --------------------------------------------------------
    @Override
    public String toString() {
        return "[" + category + "] " + questionText + " (Correct: " + correctOption + ")";
    }
}
