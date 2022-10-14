package de.officeryoda.Commands.Quiz.structure;

public class Question<T> {

    private final String question;
    private final T answer;

    public Question(String question, T answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public T getAnswer() {
        return answer;
    }
}
