package de.officeryoda.Quiz.structure;

import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;
import java.util.Map;

public class Question {

    final String question;
    final int solution;

    Map<User, Integer> answers;

    public Question(String question, int solution) {
        this.question = question;
        this.solution = solution;
        answers = new HashMap<>();
    }

    public String getQuestion() {
        return question;
    }

    public int getSolution() {
        return solution;
    }

    public void addAnswer(User user, int answer) {
        answers.put(user, answer);
    }

    public Map<User, Integer> getAnswers() {
        return answers;
    }
}
