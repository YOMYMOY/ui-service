package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ProcesadorPdiHelpCommand extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "/pdi";
    }

    @Override
    public String getHelpText() {
        return "Agregar PDI a Hechos";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.reply(chatId, """
                        🖼️ *PDI* — comandos:
                        • `/agregar_pdi <hechoId> | <urlImagen>` — Agrega un PDI a un hecho
                        """);
    }

}
