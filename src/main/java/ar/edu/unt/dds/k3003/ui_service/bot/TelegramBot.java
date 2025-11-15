package ar.edu.unt.dds.k3003.ui_service.bot;

import ar.edu.unt.dds.k3003.ui_service.command.ITelegramCommand;
import ar.edu.unt.dds.k3003.ui_service.service.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramBot extends TelegramLongPollingBot {

    private final String username;
    private final CommandDispatcherService dispatcherService;

    public TelegramBot(String token,
                       String username,
                       CommandDispatcherService dispatcherService) {
        super(token);
        this.username = username;
        this.dispatcherService = dispatcherService;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) return;

        long chatId = update.getMessage().getChatId();
        String txt = update.getMessage().getText().trim();

        try {
            String commandName = dispatcherService.getCommandName(txt);
            ITelegramCommand command = dispatcherService.getCommand(commandName);

            if (command != null) {
                String rawArgs = txt.replaceFirst(commandName, "").trim();
                command.execute(update, rawArgs, this);
            } else {
                reply(chatId, "🤔 No entendí ese comando. Probá `/start` para ver el menú.");
            }
        } catch (WebClientResponseException ex) {
            //reply(chatId, "⚠️ *HTTP " + ex.getRawStatusCode() + "*\n" + "```" + safe(ex.getResponseBodyAsString()) + "```");
            error(chatId);
        } catch (IllegalArgumentException ex) {
            //reply(chatId, "⚠️ *Uso inválido:* " + safe(ex.getMessage()));
            error(chatId);
        } catch (Exception ex) {
            //reply(chatId, "❌ *Error:* " + safe(ex.getMessage()));
            //ex.printStackTrace(); //para debuggear
            error(chatId);
        }

    }

    private void error(long chatId){
            reply(chatId, "❌ *Error:* Algo salio mal 😓" );
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("*","\\*")
                .replace("_","\\_")
                .replace("`","\\`")
                .replace("[", "\\[");
    }

    //Métodos de envío usados por los commands
    public void reply(long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .parseMode("Markdown")
                    .build());
        } catch (Exception e) {
            try {
                execute(SendMessage.builder()
                        .chatId(chatId)
                        .text(text)
                        .build());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void sendPhoto(long chatId, String url, String caption) {
        try {
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new org.telegram.telegrambots.meta.api.objects.InputFile(url));
            if (caption != null) photo.setCaption(caption);
            execute(photo);
        } catch (Exception e) {
            reply(chatId, "⚠️ No pude enviar la imagen: " + e.getMessage());
        }
    }

}