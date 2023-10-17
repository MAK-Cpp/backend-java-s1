package edu.project1.hangman;

import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


 // ╔═╗ ╔╗  ╔═╗ ╔╦╗ ╔═╗ ╔═╗ ╔═╗ ╦ ╦ ╦  ╦ ╦╔═ ╦   ╔╦╗ ╔╗╔ ╔═╗ ╔═╗ ╔═╗  ╦═╗ ╔═╗ ╔╦╗ ╦ ╦ ╦  ╦ ╦ ╦  ╦ ╦ ╦ ╦ ╔═╗ ╔═╗       ╗  ═╗ ╔═╗ ╦   ╔═  ╔═╗ ╔═╗ ╔═╗ ╔═╗ ╔═╗
 // ╠═╣ ╠╩╗ ║    ║║ ║╣  ╠╣  ║ ╦ ╠═╣ ║  ║ ╠╩╗ ║   ║║║ ║║║ ║ ║ ╠═╝ ║═╬╗ ╠╦╝ ╚═╗  ║  ║ ║ ╚╗╔╝ ║║║ ╔╩╦╝ ╚╦╝ ╔═╝  ╔╝    ╬╬ ║ ╔═╝  ╠║ ╚╬╝ ╚═╗ ╠═╗  ═╣ ╠═╣ ╚═╣ ║═║ o
 // ╩ ╩ ╚═╝ ╚═╝ ═╩╝ ╚═╝ ╚   ╚═╝ ╩ ╩ ╩ ╚╝ ╩ ╩ ╩═╝ ╩ ╩ ╝╚╝ ╚═╝ ╩   ╚═╝╚ ╩╚═ ╚═╝  ╩  ╚═╝  ╚╝  ╚╩╝ ╩ ╩   ╩  ╚═╝  o  ── ╬╬ ║ ╚══ ╚═╝  ╩  ══╝ ╚═╝   ╩ ╚═╝ ╚═╝ ╚═╝ o

public class Hangman {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Player player;
    private final Dictionary dictionary;
    private int statement;
    private String hiddenWord;
    private String hiddenWordForUser;
    private static final int LETTER_HEIGHT = 3;
    private static final HashMap<Character, String[]> FONT = new HashMap<>();
    @SuppressWarnings("checkstyle:MultipleStringLiterals") private static final String[][] PICTURES = new String[][]
        {{"                    ",
            "                    ",
            "                    ",
            "                    ",
            "  +---+             ",
            "  |* *|             ",
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
                "  |* *|            |",
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
                "  |* *|            |",
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
                "  |o o|            |",
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
                "o"
            };
        Character[] letters =
            new Character[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', ' ', ':'};
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
    }

    public void play() {
        hiddenWord = dictionary.getRandomWord().toLowerCase();
        hiddenWordForUser = "? ".repeat(hiddenWord.length());
        while (hiddenWordForUser.contains("?") && statement < 5) {
            print();
            char guessedLetter = player.makeGuess(hiddenWordForUser);
            if (hiddenWord.contains(Character.toString(guessedLetter))) {
                StringBuilder newHiddenWordForUser = new StringBuilder();
                for (int i = 0; i < hiddenWord.length(); i++) {
                    if (hiddenWord.charAt(i) == guessedLetter) {
                        newHiddenWordForUser.append(guessedLetter);
                    } else {
                        newHiddenWordForUser.append(hiddenWordForUser.charAt(2 * i));
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
        for (int i = 0; i < PICTURES[statement].length; i++) {
            StringBuilder toLog = new StringBuilder();
            for (int j = 0; j < PICTURES[statement][i].length(); j++) {
                toLog.append(PICTURES[statement][i].charAt(j));
            }
            toLog.append("\t");
            StringBuilder information = new StringBuilder();
            if (i < LETTER_HEIGHT) {
                information = new StringBuilder("hangman");
            } else if (i < 2 * LETTER_HEIGHT) {
                information = new StringBuilder("count of mistakes: " + statement + " of 5");
            } else if (i < 3 * LETTER_HEIGHT) {

            } else if (i < 4 * LETTER_HEIGHT) {
                information.append("word: ");
                if (statement < 5) {
                    information.append(hiddenWordForUser);
                } else {
                    for (Character ansLetter: hiddenWord.toCharArray()) {
                        information.append(ansLetter).append(" ");
                    }
                }
            } else {
                if (statement == 5 && hiddenWordForUser.contains("?")) {
                    information = new StringBuilder("you lose");
                } else if (!hiddenWordForUser.contains("?")) {
                    information = new StringBuilder("you win");
                }
            }
            for (Character letter : information.toString().toCharArray()) {
                toLog.append(FONT.get(letter)[i % LETTER_HEIGHT]).append(' ');
            }
            LOGGER.info(toLog.toString());
        }
    }
}

