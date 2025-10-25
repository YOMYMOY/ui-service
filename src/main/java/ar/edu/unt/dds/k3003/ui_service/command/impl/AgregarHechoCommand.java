package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import ar.edu.unt.dds.k3003.ui_service.service.FuentesMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class AgregarHechoCommand extends AbstractCommand {
    private final FuentesMicroservicioService fuentesService;

    @Autowired
    public AgregarHechoCommand(FuentesMicroservicioService fuentesService) {
        this.fuentesService = fuentesService;
    }

    @Override
    public String getCommandName() {
        return "/agregar_hecho";
    }

    @Override
    public String getHelpText() {
        return "<coleccion> | <titulo> | <descripcion>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> parts = splitPipe(rawArgs, 3);

        String coleccionId = parts.get(0);
        String titulo = parts.get(1);
        String descripcion = parts.get(2);

        if (coleccionId.isBlank() || titulo.isBlank()) {
            throw new IllegalArgumentException("Usá: /agregar_hecho <coleccion> | <titulo> | <descripcion>");
        }

        HechoRequest request = new HechoRequest(coleccionId, titulo, descripcion);

        HechoResponse response = fuentesService.agregarHecho(request);

        String id = (response != null && response.getId() != null) ? String.valueOf(response.getId()) : null;

        if (id != null) {
            bot.reply(chatId, "✅ *Hecho creado*: `" + id + "`\n_Sugerencia:_ probá `/hecho " + id + "`");
        } else {
            bot.reply(chatId, "⚠️ Hecho creado, pero no recibí un `id`.");
        }
    }
}
