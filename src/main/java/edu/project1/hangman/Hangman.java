package edu.project1.hangman;

import java.util.Arrays;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class Hangman {
    private final Player player;
    private final Dictionary dictionary;
    private String hiddenWord;
    private String hiddenWordForUser;
    private GameStatements statement;
    private int countMistakes;
    private final boolean[] isLetterUsed = new boolean[ALPHABET_LENGTH];
    private static final int ALPHABET_LENGTH = 26;
    private static final int GAME_NAME_ROW = 0;
    private static final int COUNT_OF_MISTAKES_ROW = 1;
    private static final int WORD_ROW = 3;
    private static final int GAME_END_ROW = 4;

    public Hangman(final CorrectDictionary dictionary, final Player player) {
        this.dictionary = dictionary;
        this.player = player;
    }

    /*package-private*/
    static String getLengthErrorMessage(final String guessedLetter) {
        return "ERROR: you need to write just one letter, len('" + guessedLetter + "') = " + guessedLetter.length();
    }

    /*package-private*/
    static String getNotALetterErrorMessage(final String guessedLetter) {
        return "ERROR: '" + guessedLetter.charAt(0) + "' is not a letter";
    }

    /*package-private*/
    static String getRepeatedLetterErrorMessage(final String guessedLetter) {
        return "ERROR: '" + guessedLetter.charAt(0) + "' was already guessed, guess new one";
    }

    private String getGuessedLetter() {
        String guessedLetter;
        while (true) {
            guessedLetter = player.makeGuess(hiddenWordForUser).toLowerCase();
            if (guessedLetter.equals("concede")) {
                statement = GameStatements.CONCEDE;
            } else if (guessedLetter.length() != 1) {
                System.err.println(getLengthErrorMessage(guessedLetter));
                continue;
            } else if (!Character.isLetter(guessedLetter.charAt(0))) {
                System.err.println(getNotALetterErrorMessage(guessedLetter));
                continue;
            } else if (isLetterUsed[guessedLetter.charAt(0) - 'a']) {
                System.err.println(getRepeatedLetterErrorMessage(guessedLetter));
                continue;
            }
            isLetterUsed[guessedLetter.charAt(0) - 'a'] = true;
            return guessedLetter;
        }
    }

    private void openGuessedLetter(final String guessedLetter) {
        StringBuilder newHiddenWordForUser = new StringBuilder(hiddenWordForUser.length());
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == guessedLetter.charAt(0)) {
                newHiddenWordForUser.append(guessedLetter);
            } else {
                newHiddenWordForUser.append(hiddenWordForUser.charAt(i << 1));
            }
            newHiddenWordForUser.append(' ');
        }
        hiddenWordForUser = newHiddenWordForUser.toString();
    }

    private void playMatch() {
        int countGuessed = 0;
        while (countGuessed < hiddenWord.length() && statement != GameStatements.LOSE) {
            print();
            String guessedLetter = getGuessedLetter();
            if (statement == GameStatements.CONCEDE) {
                break;
            }
            if (hiddenWord.contains(guessedLetter)) {
                openGuessedLetter(guessedLetter);
                countGuessed += (int) hiddenWord.chars().filter(ch -> ch == guessedLetter.charAt(0)).count();
            } else {
                statement = statement.getNextStatement();
                countMistakes++;
            }
        }
        if (countGuessed == hiddenWord.length()) {
            statement = GameStatements.WIN;
        }
        print();
    }

    public void play() throws HangmanException {
        do {
            statement = GameStatements.START;
            hiddenWord = dictionary.getRandomWord();
            hiddenWordForUser = "? ".repeat(hiddenWord.length());
            countMistakes = 0;
            Arrays.fill(isLetterUsed, false);
            playMatch();
        } while (player.askToPlayAgain());
    }

    /*package-private*/
    static StringBuilder createOutput(
        final GameStatements statement,
        final int countMistakes,
        final String hiddenWord,
        final String hiddenWordForUser
    ) {
        String[] currentStatement = statement.getStatement();
        StringBuilder toLog = new StringBuilder();
        for (int i = 0; i < currentStatement.length; i++) {
            for (int j = 0; j < currentStatement[i].length(); j++) {
                toLog.append(currentStatement[i].charAt(j));
            }
            toLog.append("\t");
            StringBuilder information = switch (i / ConsoleFont.LETTER_HEIGHT) {
                case GAME_NAME_ROW -> new StringBuilder("hangman");
                case COUNT_OF_MISTAKES_ROW ->
                    new StringBuilder("count of mistakes: ").append(countMistakes).append(" of ")
                        .append(GameStatements.MAX_COUNT_MISTAKES);
                case WORD_ROW -> {
                    StringBuilder ans = new StringBuilder("word: ");
                    if (statement != GameStatements.LOSE && statement != GameStatements.CONCEDE) {
                        ans.append(hiddenWordForUser);
                    } else {
                        for (Character ansLetter : hiddenWord.toCharArray()) {
                            ans.append(ansLetter).append(" ");
                        }
                    }
                    yield ans;
                }
                case GAME_END_ROW -> switch (statement) {
                    case GameStatements.LOSE -> new StringBuilder("you lose!");
                    case GameStatements.CONCEDE -> new StringBuilder("you conceded!");
                    case GameStatements.WIN -> new StringBuilder("you win!");
                    default -> new StringBuilder();
                };
                default -> new StringBuilder();
            };
            for (Character letter : information.toString().toCharArray()) {
                toLog.append(ConsoleFont.getPartOfLetter(letter, i % ConsoleFont.LETTER_HEIGHT)).append(' ');
            }
            toLog.append("\n");
        }
        return toLog;
    }

    private void print() {
        System.out.print(createOutput(statement, countMistakes, hiddenWord, hiddenWordForUser));
    }
}

