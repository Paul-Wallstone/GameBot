package com.vaider.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * @author ppuchinsky
 */
@Slf4j
@Component
public class TelegramBot extends TelegramWebhookBot {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.uri}")
    String botUri;
    UpdateProcessor updateProcessor;
    private  SetWebhook setWebhook;

    @PostConstruct
    public void init() {
        log.info(botName);
        log.info(botUri);
        updateProcessor.registerBot(this);
        this.setWebhook = SetWebhook.builder()
                .url(botUri)
                .build();
        try {
            this.setWebhook(setWebhook);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Autowired
    public TelegramBot(@Value("${bot.token}") String botToken, UpdateProcessor updateProcessor) {
        super(botToken);
        log.info(botToken);
        this.updateProcessor = updateProcessor;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void sendMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return "/update";
    }

    public SetWebhook getSetWebhook() {
        return setWebhook;
    }
}
