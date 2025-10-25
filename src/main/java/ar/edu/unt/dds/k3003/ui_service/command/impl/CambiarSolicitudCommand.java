package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudEstadoUpdateRequest;
import ar.edu.unt.dds.k3003.ui_service.dto.SolicitudResponse;
import ar.edu.unt.dds.k3003.ui_service.service.SolicitudMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class CambiarSolicitudCommand extends AbstractCommand {
    private static final List<String> ESTADOS_VALIDOS = List.of("CREADA", "VALIDADA", "EN_DISCUCION", "ACEPTADA", "RECHAZADA");

    private final SolicitudMicroserviceService solicitudService;

    @Autowired
    public CambiarSolicitudCommand(SolicitudMicroserviceService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @Override
    public String getCommandName() {
        return "/cambiar_solicitud";
    }

    @Override
    public String getHelpText() {
        return "<solicitudId> | <estado>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> parts = splitPipe(rawArgs, 2);

        String id = parts.get(0);
        String estadoStr = parts.get(1);

        if (id.isBlank() || estadoStr.isBlank()) {
            throw new IllegalArgumentException("Usá `/cambiar_solicitud <solicitudId> | <estado>`");
        }

        String estado = estadoStr.toUpperCase();
        if (!ESTADOS_VALIDOS.contains(estado)) {
            String estadosDisponibles = String.join(", ", ESTADOS_VALIDOS);
            throw new IllegalArgumentException(
                    "Estado `" + estadoStr + "` no válido.\n" +
                            "Estados posibles: " + estadosDisponibles
            );
        }

        SolicitudEstadoUpdateRequest request = new SolicitudEstadoUpdateRequest(id, estado);

        SolicitudResponse response = solicitudService.cambiarEstadoSolicitud(request);

        String idR = (response != null && response.getId() != null) ? String.valueOf(response.getId()) : null;

        if (idR != null) {
            bot.reply(chatId, "🔁 *Solicitud* `" + idR + "` → estado: `" + response.getEstado() + "`");
        } else {
            bot.reply(chatId, "⚠️ Estado de solicitud actualizado, pero no recibí un `id`.");
        }
    }
}
