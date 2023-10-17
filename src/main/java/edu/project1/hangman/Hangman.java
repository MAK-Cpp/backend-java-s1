package edu.project1.hangman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hangman {
    private final Player player;
    private final Dictionary dictionary;
    private String hiddenWord;
    private String hiddenWordForUser;
    private boolean concede;
    private GameStatements statement;
    private int counMistakes;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int GAME_NAME_ROW = 0;
    private static final int COUNT_OF_MISTAKES_ROW = 1;
    private static final int WORD_ROW = 3;
    private static final int GAME_END_ROW = 4;

    public Hangman(Dictionary dictionary, Player player) {
        this.dictionary = dictionary;
        this.player = player;
        concede = false;
    }

    public void play() {
        statement = GameStatements.START;
        concede = false;
        hiddenWord = dictionary.getRandomWord().toLowerCase();
        hiddenWordForUser = "? ".repeat(hiddenWord.length());
        int countGuessed = 0;
        counMistakes = 0;
        out:
        while (countGuessed < hiddenWord.length() && statement != GameStatements.LOSE) {
            print();
            String guessedLetter;
            do {
                guessedLetter = player.makeGuess(hiddenWordForUser);
                if (guessedLetter.equals("concede")) {
                    concede = true;
                    break out;
                }
                if (guessedLetter.length() != 1) {
                    LOGGER.error("ERROR: you need to write just one letter");
                } else if (!Character.isLetter(guessedLetter.charAt(0))) {
                    LOGGER.error("ERROR: '" + guessedLetter.charAt(0) + "' is not a letter");
                }
            } while (guessedLetter.length() != 1 || !Character.isLetter(guessedLetter.charAt(0)));
            if (hiddenWordForUser.contains(guessedLetter)) {
                continue;
            }
            if (hiddenWord.contains(guessedLetter)) {
                StringBuilder newHiddenWordForUser = new StringBuilder();
                for (int i = 0; i < hiddenWord.length(); i++) {
                    if (hiddenWord.charAt(i) == guessedLetter.charAt(0)) {
                        countGuessed++;
                        newHiddenWordForUser.append(guessedLetter);
                    } else {
                        newHiddenWordForUser.append(hiddenWordForUser.charAt(i << 1));
                    }
                    newHiddenWordForUser.append(' ');
                }
                hiddenWordForUser = newHiddenWordForUser.toString();
            } else {
                statement = statement.getNextStatement();
                counMistakes++;
            }
        }
        if (countGuessed == hiddenWord.length()) {
            statement = GameStatements.WIN;
        }
        print();
    }

    private void print() {
        String[] currentStatement = statement.getStatement();
        for (int i = 0; i < currentStatement.length; i++) {
            StringBuilder toLog = new StringBuilder();
            for (int j = 0; j < currentStatement[i].length(); j++) {
                toLog.append(currentStatement[i].charAt(j));
            }
            toLog.append("\t");
            StringBuilder information = switch (i / ConsoleFont.LETTER_HEIGHT) {
                case GAME_NAME_ROW -> new StringBuilder("hangman");
                case COUNT_OF_MISTAKES_ROW ->
                    new StringBuilder("count of mistakes: ").append(counMistakes).append(" of ")
                        .append(GameStatements.MAX_COUNT_MISTAKES);
                case WORD_ROW -> {
                    StringBuilder ans = new StringBuilder("word: ");
                    if (statement != GameStatements.LOSE && !concede) {
                        ans.append(hiddenWordForUser);
                    } else {
                        for (Character ansLetter : hiddenWord.toCharArray()) {
                            ans.append(ansLetter).append(" ");
                        }
                    }
                    yield ans;
                }
                case GAME_END_ROW -> {
                    if (statement == GameStatements.LOSE) {
                        yield new StringBuilder("you lose!");
                    } else if (concede) {
                        yield new StringBuilder("you conceded!");
                    } else if (statement == GameStatements.WIN) {
                        yield new StringBuilder("you win!");
                    } else {
                        yield new StringBuilder();
                    }
                }
                default -> new StringBuilder();
            };
            for (Character letter : information.toString().toCharArray()) {
                toLog.append(ConsoleFont.getPartOfLetter(letter, i % ConsoleFont.LETTER_HEIGHT)).append(' ');
            }
            LOGGER.info(toLog.toString());
        }
    }
}

