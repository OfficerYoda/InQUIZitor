package de.officeryoda.Quiz.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {

    List<Question> questions;
    Question currentQuestion;
    boolean quizzing;

    public Quiz() {
        questions = new ArrayList<>();
        quizzing = false;
    }

    public boolean startQuiz() {
        if(quizzing) return false;
        quizzing = true;

        return true;
    }

    public boolean endQuiz() {
        if(!quizzing) return false;
        quizzing = false;

        return true;
    }

    public boolean nextQuestion() {
        if(questions.size() == 0) {
            endQuiz();
            return false;
        }
        currentQuestion = questions.remove(0);
        return true;
    }

    public void addQuestion(Question question) {
        if(!quizzing)
            questions.add(question);
    }

    public void addQuestion(String question, int solution) {
        addQuestion(new Question(question, solution));
    }

    public void shuffleQuestions() {
        Collections.shuffle(questions);
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean isQuizzing() {
        return quizzing;
    }
}
