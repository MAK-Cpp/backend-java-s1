package edu.project1.hangman;

import java.util.HashMap;

@SuppressWarnings("checkstyle:MultipleStringLiterals")
public final class ConsoleFont {
    private static final HashMap<Character, String[]> FONT = new HashMap<>();
    public static final int LETTER_HEIGHT = 3;

    static {
        String[] values = new String[]
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

    private ConsoleFont() {
    }

    public static String getPartOfLetter(char letter, int part) {
//        System.out.println(letter);
//        System.out.println(FONT.get(letter)[0] + '\n' + FONT.get(letter)[1] + '\n' + FONT.get(letter)[2]);
        return FONT.get(letter)[part];
    }
}
