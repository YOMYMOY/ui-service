package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudResponse;
import ar.edu.unt.dds.k3003.ui_service.service.SolicitudMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class SolicitarBorradoCommand extends AbstractCommand {
    private final SolicitudMicroserviceService solicitudService;

    @Autowired
    public SolicitarBorradoCommand(SolicitudMicroserviceService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @Override
    public String getCommandName() {
        return "/solicitar_borrado";
    }

    @Override
    public String getHelpText() {
        return "<hechoId> | <motivo>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> parts = splitPipe(rawArgs, 2);

        String hechoId = parts.get(0);
        String descripcion = parts.get(1);

        if (hechoId.isBlank() || descripcion.isBlank()) {
            throw new IllegalArgumentException("Usá `/solicitar_borrado <hechoId> | <descripcion>`");
        }

        SolicitudRequest request = new SolicitudRequest(hechoId, descripcion, "CREADA");

        SolicitudResponse response = solicitudService.crearSolicitud(request);

        String id = (response != null && response.getId() != null) ? String.valueOf(response.getId()) : null;
        String estado = (response != null && response.getEstado() != null) ? response.getEstado() : "pendiente";

        if (id != null) {
            bot.reply(chatId, "📬 *Solicitud creada*: `" + id + "`· estado `" + estado + "`");
        } else {
            bot.reply(chatId, "⚠️ Solicitud creada, pero no recibí un `id`.");
        }
    }
}
