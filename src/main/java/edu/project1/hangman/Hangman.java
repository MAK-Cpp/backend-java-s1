package edu.project1.hangman;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
public class Hangman {
    private final Player player;
    private final Dictionary dictionary;
    private String hiddenWord;
    private String hiddenWordForUser;
    private GameStatements statement;
    private int countMistakes;
    private static final int GAME_NAME_ROW = 0;
    private static final int COUNT_OF_MISTAKES_ROW = 1;
    private static final int WORD_ROW = 3;
    private static final int GAME_END_ROW = 4;

    public Hangman(Dictionary dictionary, Player player) {
        this.dictionary = dictionary;
        this.player = player;
    }

    public void play() throws HangmanException {
        do {
            statement = GameStatements.START;
            hiddenWord = dictionary.getRandomWord().toLowerCase();
            if (!hiddenWord.matches("[a-zA-Z]+")) {
                throw new HangmanException("dictionary contains not allowed word(s)");
            }
            hiddenWordForUser = "? ".repeat(hiddenWord.length());
            int countGuessed = 0;
            countMistakes = 0;
            out:
            while (countGuessed < hiddenWord.length() && statement != GameStatements.LOSE) {
                print();
                String guessedLetter;
                do {
                    guessedLetter = player.makeGuess(hiddenWordForUser).toLowerCase();
                    if (guessedLetter.equals("concede")) {
                        statement = GameStatements.CONCEDE;
                        break out;
                    }
                    if (guessedLetter.length() != 1) {
                        System.err.println("ERROR: you need to write just one letter");
                    } else if (!Character.isLetter(guessedLetter.charAt(0))) {
                        System.err.println("ERROR: '" + guessedLetter.charAt(0) + "' is not a letter");
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
                    countMistakes++;
                }
            }
            if (countGuessed == hiddenWord.length()) {
                statement = GameStatements.WIN;
            }
            print();
        } while (player.askToPlayAgain());
    }

    /*package-private*/ static StringBuilder createOutput(final GameStatements statement, final int countMistakes, final String hiddenWord, final String hiddenWordForUser) {
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

