package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SolicitudesHelpCommand extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "/solicitud";
    }

    @Override
    public String getHelpText() {
        return "Solicitudes de borrado";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.reply(chatId, """
                        🧹 *Solicitudes* — comandos:
                        • `/solicitar_borrado <hechoId> | <motivo>` — Crea una solicitud
                        • `/cambiar_solicitud <solicitudId> | <estado>` — Cambia el estado
                        """);
    }

}
