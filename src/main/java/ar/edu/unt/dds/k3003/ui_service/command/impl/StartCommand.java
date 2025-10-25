package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.command.ITelegramCommand;
import ar.edu.unt.dds.k3003.ui_service.service.CommandDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
public class StartCommand extends AbstractCommand {
    private final CommandDispatcherService dispatcherService;

    @Autowired
    public StartCommand(@Lazy CommandDispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @Override
    public String getCommandName() {
        return "/start";
    }

    @Override
    public String getHelpText() {
        return "Muestra el menú de bienvenida";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        String menuAyuda = dispatcherService.getAllCommands().stream()
                .filter(ITelegramCommand::showInMenu)
                .map(command -> String.format("• `%s - %s`", command.getCommandName(), command.getHelpText()))
                .collect(Collectors.joining("\n"));

        bot.reply(chatId, """
                🤖 *¡Bienvenido a MetaMapa!* 📍
                
                Elegí una sección para ver sus comandos:
                """ + "\n" + menuAyuda + "\n\n" +
                "_Tip:_ usá la barra `/` para autocompletar comandos.");
    }
}
