package ar.edu.unt.dds.k3003.ui_service.command;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ITelegramCommand {
    String getCommandName();
    String getHelpText();
    void execute(Update update, String rawArgs, TelegramBot bot);
    boolean showInMenu();
}
