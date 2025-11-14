package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoDTO;
import ar.edu.unt.dds.k3003.ui_service.dto.PageDTO;
import ar.edu.unt.dds.k3003.ui_service.service.SearchMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuscarHechosCommand extends AbstractCommand {
    private final SearchMicroserviceService searchService;

    @Autowired
    public BuscarHechosCommand(SearchMicroserviceService searchService) {
        this.searchService = searchService;
    }

    @Override
    public String getCommandName() {
        return "/buscar";
    }

    @Override
    public String getHelpText() {
        return "<palabra_clave> | [tag_opciona]";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> parts = splitPipe(rawArgs, 1);

        String palabra = parts.get(0);
        String tags = (parts.size() > 1 && !parts.get(1).isBlank()) ? parts.get(1) : null;

        PageDTO<HechoDTO> pagina = searchService.buscarHechos(palabra, tags, 0);

        if (pagina == null || pagina.getTotalElements() == 0) {
            bot.reply(chatId, "No se encontraron resultados para `" + safe(palabra) + "`.");
            return;
        }

        String listado = pagina.getContent().stream()
                .map(h -> "• `" + safe(h.getHechoId()) + "` . " + safe(h.getNombre()) + "\n" +
                        " _Colección: " + safe(h.getColeccion()) + "_")
                .collect(Collectors.joining("\n\n"));

        String footer = String.format("Página %d de %d (%d resultados).",
                pagina.getNumber() + 1,
                pagina.getTotalPages(),
                pagina.getTotalElements());

        bot.reply(chatId, "*Resultados de búsqueda:*\n\r" + listado + "\n\n" + footer);
    }
}
