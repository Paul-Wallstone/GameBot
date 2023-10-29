package com.vaider.controller;


import com.vaider.service.GameProcessor;
import com.vaider.utils.MessageUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author ppuchinsky
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProcessor {
    TelegramBot telegramBot;
    final MessageUtils messageUtils;
    final GameProcessor gameProcessor;



    @Autowired
    public UpdateProcessor(MessageUtils messageUtils, GameProcessor gameProcessor) {
        this.messageUtils = messageUtils;
        this.gameProcessor = gameProcessor;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        if (update.hasMessage()) {
            distributiveMessageByType(update);
        } else {
            log.error("Not supported message type: %s".formatted(update));
        }
    }

    private void distributiveMessageByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processTextMessage(Update update) {
        var sendMessage = gameProcessor.processTextMessage(update);
        sendView(sendMessage);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Unsupported message type!");
        sendView(sendMessage);
    }

    public void sendView(SendMessage sendMessage) {
        telegramBot.sendMessage(sendMessage);
    }
}
