package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.service.CommandDispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
public class AllHelpCommand extends AbstractCommand {

    private final CommandDispatcherService dispatcherService;

    @Autowired
    public AllHelpCommand(@Lazy CommandDispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @Override
    public String getCommandName() {
        return "/todas";
    }

    @Override
    public String getHelpText() {
        return "Ver todos los comandos de acción";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        String menuComandosAccion = dispatcherService.getAllCommands().stream()
                .filter(command -> !command.showInMenu())
                .map(command -> {
                    String uso = command.getHelpText();
                    if (uso != null && !uso.isBlank()) {
                        return String.format("• `%s %s`", command.getCommandName(), uso);
                    } else {
                        return String.format("• `%s`", command.getCommandName());
                    }
                })
                .collect(Collectors.joining("\n"));

        bot.reply(chatId, """
                📚 *Todos los comandos de acción:*
                
                """ + menuComandosAccion + "\n\n");
    }
}
