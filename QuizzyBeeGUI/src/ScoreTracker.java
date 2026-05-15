// =====================================================================
// ScoreTracker.java
// Tracks score, logs answers, calculates grade, saves to file.
// Concepts: ArrayList, Methods, if/else, File Handling, Exception
//           Handling, toString(), String formatting
// =====================================================================

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class ScoreTracker {

    private String playerName;
    private int totalQuestions;
    private int correctAnswers;

    // Syllabus: Collections (ArrayList) — answer log
    private ArrayList<String> answerLog;

    // --------------------------------------------------------
    // Constructor
    // --------------------------------------------------------
    public ScoreTracker(String playerName, int totalQuestions) {
        this.playerName     = playerName;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = 0;
        this.answerLog      = new ArrayList<>();
    }

    // --------------------------------------------------------
    // recordAnswer() — logs one answer result
    // Syllabus: if/else, ArrayList, Operators
    // --------------------------------------------------------
    public void recordAnswer(int qNum, boolean correct, String qText, String chosen, String right) {
        if (correct) {
            correctAnswers++;
            answerLog.add("Q" + qNum + " ✔  " + qText + "\n       Your answer: " + chosen);
        } else {
            answerLog.add("Q" + qNum + " ✘  " + qText
                + "\n       Your answer: " + chosen
                + "  |  Correct: " + right);
        }
    }

    // --------------------------------------------------------
    // Overloaded recordAnswer() — without answer texts
    // Syllabus: Method Overloading
    // --------------------------------------------------------
    public void recordAnswer(int qNum, boolean correct, String qText) {
        recordAnswer(qNum, correct, qText, "—", "—");
    }

    // --------------------------------------------------------
    // getPercentage() — computes % score
    // Syllabus: Data types (double), Operators
    // --------------------------------------------------------
    public double getPercentage() {
        if (totalQuestions == 0) return 0.0;
        return ((double) correctAnswers / totalQuestions) * 100.0;
    }

    // --------------------------------------------------------
    // getGrade() — letter grade via if/else chain
    // Syllabus: if/else
    // --------------------------------------------------------
    public String getGrade() {
        double pct = getPercentage();
        if      (pct >= 90) return "A+";
        else if (pct >= 80) return "A";
        else if (pct >= 70) return "B";
        else if (pct >= 60) return "C";
        else if (pct >= 50) return "D";
        else                return "F";
    }

    // --------------------------------------------------------
    // getMotivation() — switch-case on grade
    // Syllabus: switch-case
    // --------------------------------------------------------
    public String getMotivation() {
        switch (getGrade()) {
            case "A+": return "Outstanding! You're a QuizzyBee champion! 🏆";
            case "A":  return "Excellent work! Almost perfect! 🎉";
            case "B":  return "Great job! Keep it up! 👍";
            case "C":  return "Good effort! A bit more practice needed. 🙂";
            case "D":  return "You passed! Review the topics again. 📖";
            default:   return "Don't give up! Practice makes perfect. 💪";
        }
    }

    // --------------------------------------------------------
    // saveToFile() — writes report to .txt
    // Syllabus: File Handling (FileWriter), Exception Handling
    // --------------------------------------------------------
    public String saveToFile() {
        String filename = playerName.replaceAll("\\s+", "_") + "_QuizzyBee_Result.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("========================================\n");
            bw.write("   QuizzyBee - Score Report\n");
            bw.write("========================================\n");
            bw.write("Player  : " + playerName + "\n");
            bw.write("Score   : " + correctAnswers + "/" + totalQuestions + "\n");
            bw.write(String.format("Percent : %.1f%%\n", getPercentage()));
            bw.write("Grade   : " + getGrade() + "\n");
            bw.write("Message : " + getMotivation() + "\n");
            bw.write("\n--- Answer Log ---\n");

            // while loop — Syllabus: Loops (while)
            int i = 0;
            while (i < answerLog.size()) {
                bw.write(answerLog.get(i) + "\n");
                i++;
            }

            bw.write("========================================\n");
            bw.close();
            return filename;

        } catch (IOException e) {
            // Syllabus: Exception Handling (try-catch)
            return "ERROR: " + e.getMessage();
        }
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------
    public String            getPlayerName()    { return playerName;     }
    public int               getCorrectAnswers() { return correctAnswers; }
    public int               getTotalQuestions() { return totalQuestions; }
    public ArrayList<String> getAnswerLog()      { return answerLog;      }

    // --------------------------------------------------------
    // toString()
    // Syllabus: toString()
    // --------------------------------------------------------
    @Override
    public String toString() {
        return "[ScoreTracker] " + playerName + " | "
             + correctAnswers + "/" + totalQuestions + " | "
             + String.format("%.1f", getPercentage()) + "% | Grade: " + getGrade();
    }
}
