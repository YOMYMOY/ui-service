package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.PdiRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.PdiResponse;
import ar.edu.unt.dds.k3003.ui_service.service.ProcesadorPdiMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class AgregarPdiCommand extends AbstractCommand {
    private final ProcesadorPdiMicroservicioService pdiService;

    @Autowired
    public AgregarPdiCommand(ProcesadorPdiMicroservicioService pdiService) {
        this.pdiService = pdiService;
    }

    @Override
    public String getCommandName() {
        return "/agregar_pdi";
    }

    @Override
    public String getHelpText() {
        return "<hechoId> | <urlImagen>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> parts = splitPipe(rawArgs, 2);

        String hechoId = parts.get(0);
        String urlImagen = parts.get(1);

        if (hechoId.isBlank() || urlImagen.isBlank()) {
            throw new IllegalArgumentException("Usá `/agregar_pdi <hechoId> | <urlImagen>`");
        }

        PdiRequest request = new PdiRequest(hechoId, urlImagen);

        PdiResponse response = pdiService.agregarPdi(request);

        String id = (response != null && response.getId() != null) ? String.valueOf(response.getId()) : null;

        if (id != null) {
            bot.reply(chatId, "🖼️ *PDI creado*: `" + id + "`");
        } else {
            bot.reply(chatId, "⚠️ PDI creado, pero no recibí un `id`.");
        }

        if (looksLikeUrl(urlImagen)) {
            bot.sendPhoto(chatId, urlImagen, "🖼️ PDI");
        }
    }
}
