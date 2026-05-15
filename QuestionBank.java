// =====================================================================
// QuestionBank.java
// Stores all MCQ questions in an ArrayList.
// Concepts: ArrayList, Arrays, Collections.shuffle(), Lambda sort,
//           Methods, Loops
// =====================================================================

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {

    // Syllabus: Collections (ArrayList)
    private final ArrayList<Question> questions;

    // --------------------------------------------------------
    // Constructor — builds the full question list
    // --------------------------------------------------------
    public QuestionBank() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    // --------------------------------------------------------
    // loadQuestions() — populates the ArrayList with MCQs
    // Syllabus: Arrays (String[]), ArrayList.add(), Methods
    // --------------------------------------------------------
    private void loadQuestions() {

        // ---- Science (5 questions) ----
        questions.add(new Question(
            "What is the chemical symbol for water?",
            new String[]{"H2O2", "HO", "H2O", "H3O"}, 3, "Science"));

        questions.add(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 2, "Science"));

        questions.add(new Question(
            "What gas do plants absorb during photosynthesis?",
            new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"}, 3, "Science"));

        questions.add(new Question(
            "How many bones are in the adult human body?",
            new String[]{"196", "206", "216", "186"}, 2, "Science"));

        questions.add(new Question(
            "What is the speed of light (approx)?",
            new String[]{"3×10⁸ m/s", "3×10⁶ m/s", "3×10¹⁰ m/s", "3×10⁴ m/s"}, 1, "Science"));

        // ---- Math (5 questions) ----
        questions.add(new Question(
            "What is the value of Pi (to 2 decimal places)?",
            new String[]{"3.14", "3.41", "3.12", "3.16"}, 1, "Math"));

        questions.add(new Question(
            "What is the square root of 144?",
            new String[]{"11", "14", "12", "13"}, 3, "Math"));

        questions.add(new Question(
            "How many sides does a heptagon have?",
            new String[]{"5", "6", "8", "7"}, 4, "Math"));

        questions.add(new Question(
            "What is 15% of 200?",
            new String[]{"25", "30", "35", "20"}, 2, "Math"));

        questions.add(new Question(
            "What is the sum of angles in a triangle?",
            new String[]{"90°", "180°", "270°", "360°"}, 2, "Math"));

        // ---- Technology (5 questions) ----
        questions.add(new Question(
            "What does CPU stand for?",
            new String[]{"Central Processing Unit", "Central Program Utility",
                         "Computer Processing Unit", "Core Processing Unit"}, 1, "Tech"));

        questions.add(new Question(
            "Which language is known as the 'language of the web'?",
            new String[]{"Python", "Java", "HTML", "C++"}, 3, "Tech"));

        questions.add(new Question(
            "What does RAM stand for?",
            new String[]{"Read Access Memory", "Random Access Memory",
                         "Rapid Access Module", "Read And Memorize"}, 2, "Tech"));

        questions.add(new Question(
            "Who is known as the father of computers?",
            new String[]{"Alan Turing", "Bill Gates", "Charles Babbage", "Steve Jobs"}, 3, "Tech"));

        questions.add(new Question(
            "Which of these is NOT a programming language?",
            new String[]{"Python", "Java", "HTML", "Cobra"}, 3, "Tech"));

        // ---- General Knowledge (5 questions) ----
        questions.add(new Question(
            "Which is the largest ocean on Earth?",
            new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 4, "GK"));

        questions.add(new Question(
            "How many continents are there on Earth?",
            new String[]{"5", "6", "7", "8"}, 3, "GK"));

        questions.add(new Question(
            "What is the capital of Japan?",
            new String[]{"Beijing", "Seoul", "Bangkok", "Tokyo"}, 4, "GK"));

        questions.add(new Question(
            "Which animal is the fastest on land?",
            new String[]{"Lion", "Horse", "Cheetah", "Leopard"}, 3, "GK"));

        questions.add(new Question(
            "How many players are in a cricket team?",
            new String[]{"9", "10", "11", "12"}, 3, "GK"));
    }

    // --------------------------------------------------------
    // shuffle() — randomises order
    // Syllabus: Collections, Lambda expression
    // --------------------------------------------------------
    public void shuffle() {
        questions.sort((a, b) -> (int)(Math.random() * 3) - 1); // Lambda
        Collections.shuffle(questions);
    }

    // --------------------------------------------------------
    // getByCategory() — filters by category string
    // Syllabus: ArrayList, for-each loop, String methods, if/else
    // --------------------------------------------------------
    public ArrayList<Question> getByCategory(String category) {
        if (category.equals("All")) return new ArrayList<>(questions);
        ArrayList<Question> filtered = new ArrayList<>();
        for (Question q : questions) {                          // for-each loop
            if (q.getCategory().equals(category)) {
                filtered.add(q);
            }
        }
        return filtered;
    }

    // --------------------------------------------------------
    // getSubset() — returns first 'count' from given list
    // Syllabus: ArrayList, for loop
    // --------------------------------------------------------
    public ArrayList<Question> getSubset(ArrayList<Question> source, int count) {
        ArrayList<Question> subset = new ArrayList<>();
        int limit = Math.min(count, source.size());
        for (int i = 0; i < limit; i++) {
            subset.add(source.get(i));
        }
        return subset;
    }

    public ArrayList<Question> getAll()    { return questions; }
    public int size()                       { return questions.size(); }
}
