package de.officeryoda;

import de.officeryoda.Commands.Quiz.QuizCommands;
import de.officeryoda.Commands.Quiz.QuizManager;
import de.officeryoda.Listeners.ReadyListener;
import de.officeryoda.api.dcbcr.Commands.Managment.CommandListener;
import de.officeryoda.api.dcbcr.Commands.Managment.CommandRegistrator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Inquizitor {

    private final String TOKEN = "MTAyOTAyMTc3NzI3NzUwNTU5OA.GffIoX.oHfCh4Cd93mcQieFZk0qiMO2UJmowjVLjLeDkc";
    public static final String PREFIX = "?";

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

        JDABuilder builder = JDABuilder.createDefault(TOKEN);

        // JDA setup
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Quiz"));

        // Listener setup
        builder.addEventListeners(new ReadyListener());
        builder.addEventListeners(new CommandListener("?"));

        jda = builder.build().awaitReady();

        quizManager = new QuizManager();

        // register Commands
        CommandRegistrator.registerPingCommand();
        CommandRegistrator.registerClass(new QuizCommands());
    }

    public QuizManager getQuizManager() {
        return quizManager;
    }
}
