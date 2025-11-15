package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AgregadorHelpCommand extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "/agregador";
    }

    @Override
    public String getHelpText() {
        return "Listados";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.reply(chatId, """
                        🗂️ *Agregador* — comandos:
                        • `/coleccion <nombre> | <pagina>` — Lista los *hechos* de esa colección
                        """);
    }

}
