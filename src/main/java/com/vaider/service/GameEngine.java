package com.vaider.service;

import com.vaider.service.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ppuchinsky
 */
@Component
public class GameEngine {
    Map<Long, Game> gameMap = new HashMap<>();

    public String start(Long chatId) {
        if (!hasGame(chatId)) {
            Game game = new Game();
            gameMap.put(chatId, game);
            return "Приветики! Я игровой чат бот! Как тебя зовут?";
        } else {
            Game game = gameMap.get(chatId);
            return "%s,ты уже играешь! Для отмены нажми /cancel".formatted(game.getName());
        }
    }

    public String continueGame(Long chatId, String text) {
        Game game = gameMap.get(chatId);
        if (game.getName() == null) {
            game.setName(text);
        }
        if (game.getIsStarted()) {
            try {
                int num = Integer.parseInt(text);
                return game.continueGame(num);
            } catch (Exception e) {
                return "Упс, что то я не понял что ты загадал. Введи номер картинки.";
            }
        } else {
            return "Очень приятно познакомиться, %s!\n".formatted(game.getName()) + game.generate();
        }
    }

    public Boolean hasGame(Long chatId) {
        return gameMap.containsKey(chatId);
    }

    public String cancel(Long chatId) {
        gameMap.remove(chatId);
        return "Игра отменена, для начала игры нажмите /game";
    }
}
