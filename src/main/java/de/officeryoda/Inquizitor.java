package de.officeryoda;

import de.officeryoda.Commands.Quiz.QuizCommands;
import de.officeryoda.Commands.Quiz.QuizManager;
import de.officeryoda.Listeners.ReadyListener;
import de.officeryoda.dcbcr.Managment.CommandRegistrator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Inquizitor {

    public static Inquizitor INSTANCE;

    private JDA jda;
    private QuizManager quizManager;

    public static void main(String[] args) {
        try {
            new Inquizitor();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Inquizitor() throws InterruptedException {
        INSTANCE = this;

        JDABuilder builder = JDABuilder.createDefault(TOKENS.TOKEN);

        // JDA setup
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Quiz"));

        // Listener setup
        builder.addEventListeners(new ReadyListener());

        jda = builder.build().awaitReady();

        quizManager = new QuizManager();

        // register Commands
        CommandRegistrator commandRegistrator = new CommandRegistrator(builder, TOKENS.PREFIX);
        commandRegistrator.registerPingCommand();
        commandRegistrator.registerClass(new QuizCommands());
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }
}
