package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoDTO;
import ar.edu.unt.dds.k3003.ui_service.dto.PageDTO;
import ar.edu.unt.dds.k3003.ui_service.service.SearchMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;
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
        return "<palabra> | [tag1] | [tag2]... | [pagina]";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        //List<String> parts = splitPipe(rawArgs, 1);
        List<String> parts = Arrays.stream(rawArgs.split("\\|"))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());

        if (rawArgs == null || rawArgs.isBlank()) {
            throw new IllegalArgumentException("\uD83D\uDD11 Falta `<palabra_clave>` para buscar los hechos.");
        }

        String palabra = parts.get(0);
        List<String> tags = new ArrayList<>();
        int page = 0;



        if (parts.size() > 1) {
            List<String> argsOpcionales = new ArrayList<>(parts.subList(1, parts.size()));
            String ultimoArg = argsOpcionales.get(argsOpcionales.size() - 1);

            try {
                int pageNum = Integer.parseInt(ultimoArg);
                page = Math.max(0, pageNum - 1);
                argsOpcionales.remove(argsOpcionales.size() - 1);
            } catch (NumberFormatException e) {

            }

            tags = argsOpcionales.stream()
                    .filter(t -> !t.isBlank())
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }

        PageDTO<HechoDTO> pagina = searchService.buscarHechos(palabra, tags.isEmpty() ? null : tags, page);


        if (pagina == null || pagina.getTotalElements() == 0) {
            bot.reply(chatId, "❓ No se encontraron resultados para `" + safe(palabra) + "`.");
            return;
        }

        String listado = pagina.getContent().stream()
                .map(h -> "• `" + safe(h.getHechoId()) + "` : " + safe(h.getNombre()) + "\n" +
                        " _Colección: " + safe(h.getColeccion()) + "_")
                .collect(Collectors.joining("\n\n"));

        String footer = String.format("Página %d de %d (%d resultados).",
                pagina.getNumber() + 1,
                pagina.getTotalPages(),
                pagina.getTotalElements());

        String paginacion = "";
        String tagString = tags.isEmpty() ? "" : " | " + String.join(" | ", tags.stream().map(tag -> safe(tag)).collect(Collectors.toList()));

        if (!pagina.isFirst()) {
            paginacion += "⬅\uFE0F Para pág. anterior: `/buscar " + safe(palabra) + tagString + " | " + (pagina.getNumber()) + "`\n";
        }
        if (pagina.getNumber() < pagina.getTotalPages() - 1) {
            paginacion += "➡\uFE0F Para pág. siguiente: `/buscar " + safe(palabra) + tagString + " | " + (pagina.getNumber() + 2) + "`\n";
        }

        bot.reply(chatId, "\uD83D\uDD0E *Resultados de búsqueda:*\n\r" + listado + "\n\n" + footer + "\n\n" + paginacion);
    }
}
