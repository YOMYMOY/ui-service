package ar.edu.unt.dds.k3003.ui_service.service;

import ar.edu.unt.dds.k3003.ui_service.command.ITelegramCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommandDispatcherService {
    private final Map<String, ITelegramCommand> commandMap;

    @Autowired
    public CommandDispatcherService(List<ITelegramCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(ITelegramCommand::getCommandName, Function.identity()));
    }

    public ITelegramCommand getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    public String getCommandName(String text) {
        if (text == null || !text.startsWith("/")) {
            return null;
        }
        return commandMap.keySet().stream()
                .filter(text::startsWith)
                .max(String.CASE_INSENSITIVE_ORDER)
                .orElse(null);
    }

    public Collection<ITelegramCommand> getAllCommands() {
        return commandMap.values().stream().toList();
    }
}
