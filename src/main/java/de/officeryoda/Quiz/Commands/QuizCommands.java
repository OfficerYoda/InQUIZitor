package de.officeryoda.Quiz.Commands;

import de.officeryoda.Inquizitor;
import de.officeryoda.Quiz.QuizManager;
import de.officeryoda.Quiz.structure.Question;
import de.officeryoda.Quiz.structure.Quiz;
import de.officeryoda.dcbcr.CommandData.PrivateBotCommand;
import de.officeryoda.dcbcr.CommandData.PublicBotCommand;
import de.officeryoda.dcbcr.Managment.CommandExecuter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class QuizCommands implements CommandExecuter {

    QuizManager quizManager = Inquizitor.INSTANCE.getQuizManager();

    @Override
    public void registerCommands() {
        addPrivateCommand(this::answerCommand, "answer", "ans", "a");

        addPublicCommand(this::startCommand, "startQuiz" , "start", "sq");
        addPublicCommand(this::endQuiz, "endQuiz", "end", "eq");
        addPublicCommand(this::questionCommand, "question", "q");
        addPublicCommand(this::solutionCommand, "solution", "s");
    }

    private void startCommand(@NotNull PublicBotCommand botCommand) {
        boolean succeeded = getQuiz().startQuiz();
        if(!succeeded) {
            botCommand.sendMessage("Quiz already running.");
            return;
        }

        botCommand.sendMessage("Started Quiz.");

        String[] args = botCommand.getArgs();
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("false")) {
                return;
            }
        }

        quizManager.getQuiz().shuffleQuestions();
        botCommand.sendMessage("shuffled Questions");
    }

    private void endQuiz(@NotNull PublicBotCommand botCommand) {
        boolean succeeded = getQuiz().endQuiz();
        if(!succeeded) {
            botCommand.sendMessage("No running quiz to end.");
            return;
        }

        botCommand.sendMessage("Ended Quiz.");
        botCommand.sendMessage("Implement Leaderboard here");
    }

    private void answerCommand(@NotNull PrivateBotCommand botCommand) {
        if(!isQuizzing()) {
            botCommand.sendMessage("No Quiz running.");
            return;
        }

        String[] args = botCommand.getArgs();

        if(args.length == 0) {
            botCommand.sendMessage("Please enter your answer.");
            return;
        }

        int answer = 0;

        try {
            answer = Integer.parseInt(args[0]);
        } catch (Exception e) {
            // couldn't parse args[0] to int
            botCommand.sendMessage("**" + args[0] + "** is not a valid number!");
            System.out.println(Integer.parseInt(args[0]));
        }

        botCommand.sendMessage("Set **" + args[0] + "** as your answer.");
        quizManager.getQuiz().getCurrentQuestion().addAnswer(botCommand.getAuthor(), answer); // <--- here is an exception thrown, because crntQurstion is null
    }

    private void questionCommand(@NotNull PublicBotCommand botCommand) {
        if(!isQuizzing()) {
            botCommand.sendMessage("No Quiz running.");
            return;
        }

        Quiz quiz = quizManager.getQuiz();
        if(!quiz.nextQuestion()) { // there is a next question
            botCommand.sendMessage("No more Questions!");
            return;
        }

        Question question = quiz.getCurrentQuestion();
        botCommand.sendMessage("Question: " + question.getQuestion() + "\r\n" +
                "Solution: " + question.getSolution());
    }

    private void solutionCommand(@NotNull PublicBotCommand botCommand) {
        if(!isQuizzing()) {
            botCommand.sendMessage("No Quiz running.");
            return;
        }

        Question question = getQuiz().getCurrentQuestion();
        if(question == null) {
            botCommand.sendMessage("No question to reveal solution for.");
            return;
        }

        Map<User, Integer> answers = question.getAnswers();
        StringBuilder answerString = new StringBuilder();
        for(Map.Entry<User, Integer> answer : answers.entrySet()) {
            answerString.append("**").append(answer.getKey().getName()).append("**:  ").append(answer.getValue()).append("\r\n");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Solution");

        embed.addField("Question", question.getQuestion(), false);
        embed.addField("Solution", question.getSolution() + "", false);
        embed.addField("Answers", answerString.toString(), false);

        botCommand.sendEmbeds(embed.build());
    }

    private boolean isQuizzing() {
        return quizManager.isQuizzing();
    }

    private Quiz getQuiz() {
        return quizManager.getQuiz();
    }
}
