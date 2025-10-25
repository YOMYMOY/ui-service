package ar.edu.unt.dds.k3003.ui_service.bot;

import ar.edu.unt.dds.k3003.ui_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@ConditionalOnProperty(prefix = "bot", name = "enabled", havingValue = "true")
public class BotRunner implements CommandLineRunner {
    @Value("${bot.token}") String token;
    @Value("${bot.username}") String username;

    private final CommandDispatcherService dispatcherService;

    @Autowired
    public BotRunner(CommandDispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    public void run(String... args) throws Exception {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new TelegramBot(
                token,
                username,
                dispatcherService
        ));

        System.out.println("================================================");
        System.out.println("🤖 Bot de Telegram conectado y escuchando!");
        System.out.println("================================================");
    }
}
