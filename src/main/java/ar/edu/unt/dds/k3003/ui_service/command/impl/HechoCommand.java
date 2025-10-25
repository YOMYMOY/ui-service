package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import ar.edu.unt.dds.k3003.ui_service.service.FuentesMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HechoCommand extends AbstractCommand {
    private final FuentesMicroservicioService fuentesService;

    @Autowired
    public HechoCommand(FuentesMicroservicioService fuentesService) {
        this.fuentesService = fuentesService;
    }

    @Override
    public String getCommandName() {
        return "/hecho";
    }

    @Override
    public String getHelpText() {
        return "<id>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        if (rawArgs == null || rawArgs.isBlank()) {
            throw new IllegalArgumentException("Falta `<id>` del hecho.");
        }

        String hechoId = rawArgs.trim();

        HechoResponse hecho = fuentesService.obtenerDetalleHecho(hechoId);

        if (hecho == null) {
            bot.reply(chatId, "🔎 No encontré el hecho `" + hechoId + "`.");
            return;
        }

        bot.reply(chatId, "📰 *" + safe(hecho.getTitulo()) + "*\n" +
                "Estado: " + estadoEmoji(nullTo(hecho.getEstado(), "activo")) + " `" +
                hecho.getEstado() + "`\n" +
                "Descripción: " + safe(nullTo(hecho.getDescripcion(), "-")));
    }
}
