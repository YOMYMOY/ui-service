package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FuentesHelpCommand extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "/fuente";
    }

    @Override
    public String getHelpText() {
        return "Hechos";
    }

    @Override
    public boolean showInMenu() {
        return true;
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();
        bot.reply(chatId, """
                        📰 *Fuente* — comandos:
                        
                        *Gestión:*
                        • `/hecho <id>` — Muestra detalle de un hecho
                        • `/agregar_hecho <coleccion> | <titulo> | <descripcion>` — Crea un hecho
                        
                        *Búsqueda:*
                        • `/buscar <palabra_clave> | <tags>` — Busca hechos por palabra clave y tags
                        • `/tags` — Muestra los tags válidos disponibles
                        """);
    }

}
