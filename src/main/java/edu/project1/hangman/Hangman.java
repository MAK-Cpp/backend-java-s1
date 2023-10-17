package edu.project1.hangman;

import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hangman {
    private final Player player;
    private final Dictionary dictionary;
    private int statement;
    private String hiddenWord;
    private String hiddenWordForUser;
    private boolean concede;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MAX_MISTAKES = 5;
    private static final int LETTER_HEIGHT = 3;
    private static final int GAME_NAME_ROW = 0;
    private static final int COUNT_OF_MISTAKES_ROW = 1;
    private static final int WORD_ROW = 3;
    private static final int GAME_END_ROW = 4;
    private static final HashMap<Character, String[]> FONT = new HashMap<>();
    @SuppressWarnings("checkstyle:MultipleStringLiterals") private static final String[][] PICTURES = new String[][]
        {{"                    ",
            "                    ",
            "                    ",
            "                    ",
            "  +---+             ",
            "  |^_^|             ",
            "  +---+             ",
            "    |               ",
            "  --|--             ",
            "    |               ",
            "   / \\              ",
            "  -   -             ",
            " |-----|            ",
            " |     |            ",
            "===================="},
            {"                    ",
                "                   |",
                "                   |",
                "                   |",
                "  +---+            |",
                "  |o_o|            |",
                "  +---+            |",
                "    |              |",
                "  --|--            |",
                "    |              |",
                "   / \\             |",
                "  -   -           -|",
                " |-----|        -/ |",
                " |     |       /   |",
                "===================="},
            {"  -----------------+",
                "               \\   |",
                "                -\\ |",
                "                  -|",
                "  +---+            |",
                "  |0_0|            |",
                "  +---+            |",
                "    |              |",
                "  --|--            |",
                "    |              |",
                "   / \\             |",
                "  -   -           -|",
                " |-----|        -/ |",
                " |     |       /   |",
                "===================="},
            {"  -----------------+",
                "    |          \\   |",
                "    |           -\\ |",
                "    |             -|",
                "  +---+            |",
                "  |@ @|            |",
                "  +---+            |",
                "    |              |",
                "  --|--            |",
                "    |              |",
                "   / \\             |",
                "  -   -           -|",
                " |-----|        -/ |",
                " |     |       /   |",
                "===================="},
            {"  -----------------+",
                "    |          \\   |",
                "    |           -\\ |",
                "    |             -|",
                "  +---+            |",
                "  |* *|            |",
                "  +---+            |",
                "  / | \\            |",
                "   \\|/             |",
                "    |              |",
                "   / \\             |",
                "  -   -           -|",
                "                -/ |",
                "               /   |",
                "===================="},
            {"  -----------------+",
                "    |          \\   |",
                "    |           -\\ |",
                "    |             -|",
                "  +---+            |",
                "  |X X|            |",
                "  +---+            |",
                "    |              |",
                "   /|\\             |",
                "  / | \\            |",
                "   / \\             |",
                "  /   \\           -|",
                "                -/ |",
                "               /   |",
                "===================="}
        };

    static {
        @SuppressWarnings("checkstyle:MultipleStringLiterals") String[] values = new String[]
            {
                "╔═╗",
                "╠═╣",
                "╩ ╩",
                "╔╗ ",
                "╠╩╗",
                "╚═╝",
                "╔═╗",
                "║  ",
                "╚═╝",
                "╔╦╗",
                " ║║",
                "═╩╝",
                "╔═╗",
                "║╣ ",
                "╚═╝",
                "╔═╗",
                "╠╣ ",
                "╚  ",
                "╔═╗",
                "║ ╦",
                "╚═╝",
                "╦ ╦",
                "╠═╣",
                "╩ ╩",
                "╦",
                "║",
                "╩",
                " ╦",
                " ║",
                "╚╝",
                "╦╔═",
                "╠╩╗",
                "╩ ╩",
                "╦  ",
                "║  ",
                "╩═╝",
                "╔╦╗",
                "║║║",
                "╩ ╩",
                "╔╗╔",
                "║║║",
                "╝╚╝",
                "╔═╗",
                "║ ║",
                "╚═╝",
                "╔═╗",
                "╠═╝",
                "╩  ",
                "╔═╗ ",
                "║═╬╗",
                "╚═╝╚",
                "╦═╗",
                "╠╦╝",
                "╩╚═",
                "╔═╗",
                "╚═╗",
                "╚═╝",
                "╔╦╗",
                " ║ ",
                " ╩ ",
                "╦ ╦",
                "║ ║",
                "╚═╝",
                "╦  ╦",
                "╚╗╔╝",
                " ╚╝ ",
                "╦ ╦",
                "║║║",
                "╚╩╝",
                " ╦ ╦",
                "╔╩╦╝",
                "╩ ╩ ",
                "╦ ╦",
                "╚╦╝",
                " ╩ ",
                "╔═╗",
                "╔═╝",
                "╚═╝",
                "╔═╗",
                " ╔╝",
                " o ",
                "═╗",
                " ║",
                " ║",
                " ═╗",
                "╔═╝",
                "╚══",
                "╔═╗",
                " ╠║",
                "╚═╝",
                "╦  ",
                "╚╬╝",
                " ╩ ",
                "╔═ ",
                "╚═╗",
                "══╝",
                "╔═╗",
                "╠═╗",
                "╚═╝",
                "╔═╗",
                " ═╣",
                "  ╩",
                "╔═╗",
                "╠═╣",
                "╚═╝",
                "╔═╗",
                "╚═╣",
                "╚═╝",
                "╔═╗",
                "║o║",
                "╚═╝",
                "   ",
                "   ",
                "   ",
                "o",
                " ",
                "o",
                "╦",
                "║",
                "o"
            };
        Character[] letters =
            new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', ' ', ':', '!'};
        for (int i = 0; i < letters.length; i++) {
            FONT.put(
                letters[i],
                new String[] {
                    values[LETTER_HEIGHT * i], values[LETTER_HEIGHT * i + 1], values[LETTER_HEIGHT * i + 2]
                }
            );
        }
    }

    public Hangman(Dictionary dictionary, Player player) {
        this.dictionary = dictionary;
        this.player = player;
        concede = false;
    }

    public void play() {
        hiddenWord = dictionary.getRandomWord().toLowerCase();
        hiddenWordForUser = "? ".repeat(hiddenWord.length());
        while (hiddenWordForUser.contains("?") && statement < MAX_MISTAKES) {
            print();
            char guessedLetter = player.makeGuess(hiddenWordForUser);
            if (guessedLetter == '\0') {
                concede = true;
                break;
            }
            if (hiddenWord.contains(Character.toString(guessedLetter))) {
                StringBuilder newHiddenWordForUser = new StringBuilder();
                for (int i = 0; i < hiddenWord.length(); i++) {
                    if (hiddenWord.charAt(i) == guessedLetter) {
                        newHiddenWordForUser.append(guessedLetter);
                    } else {
                        newHiddenWordForUser.append(hiddenWordForUser.charAt(i << 1));
                    }
                    newHiddenWordForUser.append(' ');
                }
                hiddenWordForUser = newHiddenWordForUser.toString();
            } else {
                statement++;
            }
        }
        print();
    }

    private void print() {
        for (int i = 0, lettersRow = 0; i < PICTURES[statement].length; i++, lettersRow = i / LETTER_HEIGHT) {
            StringBuilder toLog = new StringBuilder();
            for (int j = 0; j < PICTURES[statement][i].length(); j++) {
                toLog.append(PICTURES[statement][i].charAt(j));
            }
            toLog.append("\t");
            StringBuilder information = switch (lettersRow) {
                case GAME_NAME_ROW -> new StringBuilder("hangman");
                case COUNT_OF_MISTAKES_ROW ->
                    new StringBuilder("count of mistakes: ").append(statement).append(" of ").append(MAX_MISTAKES);
                case WORD_ROW -> {
                    StringBuilder ans = new StringBuilder("word: ");
                    if (statement < MAX_MISTAKES && !concede) {
                        ans.append(hiddenWordForUser);
                    } else {
                        for (Character ansLetter : hiddenWord.toCharArray()) {
                            ans.append(ansLetter).append(" ");
                        }
                    }
                    yield ans;
                }
                case GAME_END_ROW -> {
                    if (statement == MAX_MISTAKES) {
                        yield new StringBuilder("you lose!");
                    } else if (concede) {
                        yield new StringBuilder("you conceded!");
                    } else if (!hiddenWordForUser.contains("?")) {
                        yield new StringBuilder("you win!");
                    } else {
                        yield new StringBuilder();
                    }
                }
                default -> new StringBuilder();
            };
            for (Character letter : information.toString().toCharArray()) {
                toLog.append(FONT.get(letter)[i % LETTER_HEIGHT]).append(' ');
            }
            LOGGER.info(toLog.toString());
        }
    }
}

