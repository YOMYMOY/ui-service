package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.dto.HechoResponse;
import ar.edu.unt.dds.k3003.ui_service.service.AgregadorMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        return "<nombre>";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        if (rawArgs == null || rawArgs.isBlank()) {
            throw new IllegalArgumentException("Falta `<nombre>` de la colección.");
        }

        String coleccion = rawArgs.trim();

        List<HechoResponse> hechos = agregadorService.listarHechosPorColeccion(coleccion);

        if (hechos == null || hechos.isEmpty()) {
            bot.reply(chatId, "🗂️ *" + safe(coleccion) + "* no tiene hechos cargados.");
            return;
        }

        String listado = hechos.stream()
                .limit(10)
                .map(h -> "• `" + h.getId() + "` · " + safe(h.getTitulo()))
                .collect(Collectors.joining("\n"));

        bot.reply(chatId, "🗂️ *Hechos en* _" + safe(coleccion) + "_\n" + listado +
                (hechos.size() > 10 ? "\n... y " + (hechos.size() - 10) + " más" : ""));
    }
}
