package com.vaider.service;

import com.vaider.service.enums.ServiceCommands;
import com.vaider.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.vaider.service.enums.ServiceCommands.CANCEL;
import static com.vaider.service.enums.ServiceCommands.GAME;
import static com.vaider.service.enums.ServiceCommands.HELP;
import static com.vaider.service.enums.ServiceCommands.START;

/**
 * @author ppuchinsky
 */
@Service
@Slf4j
public class GameProcessorImpl implements GameProcessor {

    final GameEngine gameEngine;
    final MessageUtils messageUtils;

    public GameProcessorImpl(GameEngine gameEngine, MessageUtils messageUtils) {
        this.gameEngine = gameEngine;
        this.messageUtils = messageUtils;
    }

    @Override
    public SendMessage processTextMessage(Update update) {

        var text = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        String output;
        ServiceCommands command = ServiceCommands.getCommand(text);
        if (CANCEL.equals(command)) {
            output = gameEngine.cancel(chatId);
        } else if (gameEngine.hasGame(chatId)) {
            output = gameEngine.continueGame(chatId, text);
        } else if (HELP.equals(command)) {
            output = help();
        } else if (START.equals(command)) {
            output = start();
        } else if (GAME.equals(command)) {
            output = gameEngine.start(chatId);
        } else {
            output = help();
        }
        return messageUtils.generateSendMessageWithText(update, output);
    }

    private String help() {
        return """
                Список доступных комманд:
                /cancel - отмена выполнения текущей команды.
                /game - начало игры.""";
    }

    private String start() {
        return "Приветствую! Чтобы посмотреть список доступных команд введите /help";
    }
}
