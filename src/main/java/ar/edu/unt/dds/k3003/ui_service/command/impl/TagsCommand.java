package ar.edu.unt.dds.k3003.ui_service.command.impl;

import ar.edu.unt.dds.k3003.ui_service.bot.TelegramBot;
import ar.edu.unt.dds.k3003.ui_service.command.AbstractCommand;
import ar.edu.unt.dds.k3003.ui_service.service.SearchMicroserviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagsCommand extends AbstractCommand {
    private final SearchMicroserviceService searchService;

    @Autowired
    public TagsCommand(SearchMicroserviceService searchService) {
        this.searchService = searchService;
    }

    @Override
    public String getCommandName() {
        return "/tags";
    }

    @Override
    public String getHelpText() {
        return "Muestra todos los tags disponibles";
    }

    @Override
    public void execute(Update update, String rawArgs, TelegramBot bot) {
        long chatId = update.getMessage().getChatId();

        List<String> tags = searchService.listarTags();

        if (tags == null || tags.isEmpty()) {
            bot.reply(chatId, "No hay tags cargados en el sistema.");
            return;
        }

        String listaTags = tags.stream()
                .map(tag -> "`" + safe(tag) + "`")
                .collect(Collectors.joining(", "));

        bot.reply(chatId, "*Tags disponibles:*\n" + listaTags);
    }
}
