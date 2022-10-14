package de.officeryoda.Commands.Quiz.structure;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private final List<Question> questions;
    private Question currentQuestion;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void startQuiz() {
        currentQuestion = nextQuestion();
    }

    public <T> void addQuestion(Question<T> question) {
        questions.add(question);
    }

    public boolean hasQuestions() {
        return questions.size() > 0;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public Question nextQuestion() {
        currentQuestion = questions.remove(0);
        return currentQuestion;
    }
}
