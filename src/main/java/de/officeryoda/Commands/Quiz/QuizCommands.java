package de.officeryoda.Commands.Quiz;

import de.officeryoda.Commands.Quiz.structure.Question;
import de.officeryoda.Inquizitor;
import de.officeryoda.dcbcr.CommandData.PrivateBotCommand;
import de.officeryoda.dcbcr.CommandData.PublicBotCommand;
import de.officeryoda.dcbcr.Managment.CommandExecuter;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class QuizCommands implements CommandExecuter {

    Inquizitor inquizitor = Inquizitor.INSTANCE;
    QuizManager quizManager = Inquizitor.INSTANCE.getQuizManager();

    @Override
    public void registerCommands() {
        addPrivateCommand(this::answerCommand, "answer", "ans", "a");
        addPublicCommand(this::quizCommand, "quiz", "q");
        addPublicCommand(this::revealCommand, "reveal", "r");
    }

    private void answerCommand(PrivateBotCommand botCommand) {
        String[] args = botCommand.getArgs();
        if(args.length == 0) {
            botCommand.sendMessage("What is your answer");
            return;
        }

        botCommand.sendMessage("Your answer(**" + args[0] + "**) has been received.");
        quizManager.addAnswer(args[0]);
        //        botCommand.getChannel().sendMessage("Implement Answer logic here").queue();
    }

    private void quizCommand(PublicBotCommand botCommand) {
        //        botCommand.sendMessage("The number is **" + quizManager.setNumber(0, 100) + "**.");
        Question question = quizManager.getCurrentQuiz().nextQuestion();
        botCommand.sendMessage("New Question: " + question.getQuestion());

        //        botCommand.getChannel().sendMessage("Implement Quiz logic here").queue();
    }

    private void revealCommand(PublicBotCommand botCommand) {
        Question question = quizManager.getCurrentQuiz().getCurrentQuestion();

        StringBuilder stringBuilder = new StringBuilder();
        for(String string : quizManager.getAnswers()) {
            stringBuilder.append(string).append("\n");
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder
                .setTitle("Answer reveal")
                .addField("Question", question.getQuestion(), false)
                .addField("Answer", question.getAnswer().toString(), false)
                .addField("Answers", stringBuilder.toString(), false)
                .setColor(Color.GREEN);

        botCommand.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
