package com.vaider.service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ppuchinsky
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Game {

    String emoji = "\uD83E\uDD21;\uD83D\uDCA9;\uD83D\uDC7B;\uD83D\uDC80;☠️;\uD83D\uDC7D;\uD83D\uDC7E;\uD83E\uDD16;\uD83C\uDF83;\uD83D\uDC84;\uD83D\uDC8B;\uD83E\uDDB7;\uD83D\uDC40;\uD83E\uDD7C;\uD83E\uDDBA;\uD83D\uDC5A;\uD83E\uDDF6;\uD83E\uDDF5;\uD83E\uDEA1;\uD83E\uDDE5;\uD83E\uDE73;\uD83E\uDE74;\uD83E\uDD7B;\uD83D\uDC58;\uD83D\uDC5E;\uD83D\uDC62;\uD83C\uDFA9;\uD83E\uDDE3;\uD83D\uDC51;\uD83E\uDE96;⛑;\uD83D\uDCBC;\uD83C\uDF92;\uD83E\uDDF3;\uD83D\uDC53;\uD83D\uDD76;\uD83E\uDD7D;\uD83C\uDF02;\uD83D\uDC34;\uD83D\uDD78;\uD83E\uDD82;\uD83D\uDC1F;\uD83D\uDC2C;\uD83D\uDC33;\uD83D\uDC0B;\uD83E\uDD88;\uD83E\uDDAD;\uD83D\uDC0A;\uD83D\uDC05;\uD83D\uDC18;\uD83D\uDC01;\uD83D\uDC00;\uD83C\uDF35;\uD83D\uDC32;\uD83E\uDEB5;\uD83C\uDF34;\uD83C\uDF33;\uD83C\uDF8D;\uD83C\uDF40;\uD83E\uDEBA;\uD83C\uDF44;\uD83C\uDF39;\uD83C\uDF37;\uD83C\uDF3C;\uD83C\uDF38;\uD83C\uDF3A;\uD83C\uDF0E;☄️;\uD83D\uDD25;\uD83C\uDF2A;\uD83C\uDF08;\uD83C\uDF27;☀️;❄️;☔️;☂️;\uD83C\uDF2B;\uD83C\uDF4F;\uD83C\uDF4E;\uD83E\uDD65;\uD83E\uDD5D;\uD83C\uDF45;\uD83E\uDEDB;\uD83E\uDD55;\uD83C\uDF3D;\uD83E\uDED1;\uD83E\uDD54;\uD83E\uDDC5;\uD83E\uDDC4;\uD83E\uDED2;\uD83E\uDED5;\uD83E\uDD6B;\uD83C\uDF71;\uD83E\uDD60;\uD83E\uDD6E;\uD83E\uDDC1;\uD83E\uDD67;\uD83C\uDF66;\uD83C\uDF68;\uD83E\uDDCA;☕️;\uD83E\uDD5B;\uD83C\uDF7C;\uD83E\uDED6;\uD83C\uDF30;\uD83C\uDF6E;\uD83C\uDF82;\uD83C\uDF70;⚽️;\uD83E\uDD4F;\uD83C\uDFB1;\uD83E\uDE80;\uD83C\uDFD3;\uD83C\uDFF8;⛳️;\uD83C\uDFD2;\uD83C\uDFC6;\uD83C\uDFC5;\uD83D\uDE97;\uD83D\uDE91;\uD83D\uDEFA;\uD83D\uDEA8;\uD83D\uDEA6;\uD83D\uDEF3;⛱;\uD83C\uDFD6;\uD83C\uDFDD;\uD83D\uDCC0;\uD83E\uDE99;\uD83D\uDCB0;\uD83E\uDE93;\uD83E\uDDE8;\uD83D\uDD11";
    String name;
    List<String> entities = new ArrayList<>(3);
    Pair<Integer, Integer> score = Pair.of(0, 0);
    Integer guessNumber;
    Integer attempts;
    Boolean isStarted = false;

    public String generate() {
        entities.clear();
        isStarted = true;
        attempts = 0;
        Random random = new Random();
        guessNumber = random.nextInt(3) + 1;
        List<String> split = List.of(emoji.split(";"));
        while (entities.size() < 3) {
            String emoji = split.get(random.nextInt(split.size()));
            if (!entities.contains(emoji)) {
                entities.add(emoji);
            }
        }

        return "Угадывай, что я загадал:\n" + IntStream.range(0, entities.size())
                .mapToObj(i -> (i + 1) + " - " + entities.get(i))
                .collect(Collectors.joining(" ")) + "\n Нужно ввести цифру: ";
    }

    public String continueGame(Integer num) {
        if (num < 4 && num > 0) {
            if (guessNumber.equals(num) && attempts < 3) {
                Integer left = score.getLeft() + 1;
                score = Pair.of(left, score.getRight());
                String generate = generate();
                return """
                        Победа! Ну вот так всегда...
                        Наш счет - [%s-%s : Я-%s]
                        Играем дальше? Если надоело нажми /cancel.
                                                
                        %s
                        """.formatted(name, score.getLeft(), score.getRight(), generate);
            }
            if (!guessNumber.equals(num) && attempts >= 1) {
                Integer right = score.getRight() + 1;
                score = Pair.of(score.getLeft(), right);
                String generate = generate();
                return """
                        Я победил! Хо-хо-хо!!!
                        Наш счет - [%s-%s :Я-%s]
                        Играем дальше? Если надоело нажми /cancel.
                                                
                        %s
                        """.formatted(name, score.getLeft(), score.getRight(), generate);
            }
            if (!guessNumber.equals(num)) {
                attempts++;
                return "Нет, не это загадал";
            }


        }
        return "Упс, такое я точно не загадывал попробуй еще раз";
    }
}
