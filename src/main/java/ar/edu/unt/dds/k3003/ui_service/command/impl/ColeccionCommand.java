package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import ar.edu.unt.dds.k3003.ui_service.service.AgregadorMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColeccionCommand extends AbstractCommand {
    private final AgregadorMicroservicioService agregadorService;

    @Autowired
    public ColeccionCommand(AgregadorMicroservicioService agregadorService) {
        this.agregadorService = agregadorService;
    }

    @Override
    public String getCommandName() {
        return "/coleccion";
    }

    @Override
    public String getHelpText() {
        return "<nombre> | <pagina>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        if (rawArgs == null || rawArgs.isBlank()) {
            throw new IllegalArgumentException("Falta `<nombre>` de la colección.");
        }

        //List<String> parts = splitPipe(rawArgs, 1);
        List<String> parts = Arrays.stream(rawArgs.split("\\|"))
            .map(String::trim)
            .filter(s -> !s.isBlank())
            .collect(Collectors.toList());

        String coleccion = parts.get(0);
        String pagina = (parts.size() > 1 && !parts.get(1).isBlank()) ? parts.get(1) : "0";
        try {
            Integer nroPagina = Integer.parseInt(pagina);
        } catch (NumberFormatException e) {
            //System.err.println("Error: El String no es un número válido. " + e.getMessage());
            bot.reply(chatId, "🗂️ *" + safe(coleccion) + "* pagina invalida.");
            return;
        }
        List<HechoResponse> hechos = agregadorService.listarHechosPorColeccion(coleccion, Integer.parseInt(pagina));

        if (hechos == null || hechos.isEmpty()) {
            bot.reply(chatId, "🗂️ *" + safe(coleccion) + "* no tiene hechos cargados.");
            return;
        }

        String listado = hechos.stream()
                .map(h -> "• `" + h.getId() + "` · " + safe(h.getTitulo()))
                .collect(Collectors.joining("\n"));

        List<HechoResponse> hechosSigPag = agregadorService.listarHechosPorColeccion(coleccion, Integer.parseInt(pagina) + 1);

        int paginaSiguiente = Integer.parseInt(pagina) + 1;
        bot.reply(chatId, "🗂️ *Hechos en* _" + safe(coleccion) + "_\n" + listado +
               (!hechosSigPag.isEmpty() ? "\n " + "siguiente pagina: /coleccion "+ safe(coleccion) + " | " + paginaSiguiente : ""));
    }
}
