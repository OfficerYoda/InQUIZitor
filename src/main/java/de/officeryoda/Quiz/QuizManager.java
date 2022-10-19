package de.officeryoda.Quiz;

import de.officeryoda.Quiz.structure.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizManager {

    Quiz quiz;

    public QuizManager() {
        quiz = new Quiz();

        addQuestions();
    }

    private void addQuestions() {
        quiz.addQuestion("What is the answer?", 42);
        quiz.addQuestion("What is the best number?", 69);
        quiz.addQuestion("What is 2+2*2?", 6);
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public boolean isQuizzing() {
        return quiz.isQuizzing();
    }
}
