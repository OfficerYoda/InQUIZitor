package de.officeryoda.Commands.Quiz;

import de.officeryoda.Commands.Quiz.structure.Question;
import de.officeryoda.Commands.Quiz.structure.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizManager {

    private final Random random;

    private int number = 0;
    private List<String> answers;

    private Quiz currentQuiz;

    public QuizManager() {
        random = new Random();
        answers = new ArrayList<>();
        currentQuiz = new Quiz();

        addQuestions();
    }

    private void addQuestions() {
        currentQuiz.addQuestion(new Question<String>("What is the rank of yoda?", "Grandmaster"));
        currentQuiz.addQuestion(new Question<String>("What is the name of this bot?", "InQUIZitor"));
        currentQuiz.addQuestion(new Question<Integer>("What is the best number?", 69));
        currentQuiz.startQuiz();
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public List<String> getAnswers() {
        return answers;
    }
}
