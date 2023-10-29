package com.vaider.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author ppuchinsky
 */
public interface GameProcessor {
    SendMessage processTextMessage(Update update);
}
