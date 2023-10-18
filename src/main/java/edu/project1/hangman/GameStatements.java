package edu.project1.hangman;

@SuppressWarnings("checkstyle:MultipleStringLiterals")
public enum GameStatements {
    CONCEDE(null, new String[] {
        "  -----------------+",
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
        "===================="
    }),
    LOSE(null, new String[] {
        "  -----------------+",
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
        "===================="
    }),
    WIN(null, new String[]{
        " \\   /   |\\   \\   / ",
        "  \\ /   /  |   \\ /  ",
        "   -    |  \\    -   ",
        "   |   /----|   |   ",
        "   |   |    \\   |   ",
        "   |  /      |  |   ",
        "       +---+        ",
        "       |^u^|        ",
        "       +---+        ",
        "         |          ",
        "       --|--        ",
        "         |          ",
        "        / \\         ",
        "       -   -        ",
        "===================="
    }),
    FOURTH_MISTAKE(LOSE, new String[] {
        "  -----------------+",
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
        "===================="
    }),
    THIRD_MISTAKE(FOURTH_MISTAKE, new String[] {
        "  -----------------+",
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
        "===================="
    }),
    SECOND_MISTAKE(THIRD_MISTAKE, new String[] {
        "  -----------------+",
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
        "===================="
    }),
    FIRST_MISTAKE(SECOND_MISTAKE, new String[] {
        "                    ",
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
        "===================="
    }),
    START(FIRST_MISTAKE, new String[] {
        "                    ",
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
        "===================="
    });
    final private GameStatements nextStatement;
    final private String[] statement;
    public static final int IMAGE_HEIGHT = 15;
    public static final int MAX_COUNT_MISTAKES = 5;

    GameStatements(GameStatements nextStatement, String[] statement) {
        this.nextStatement = nextStatement;
        this.statement = statement;
    }

    String[] getStatement() {
        return statement;
    }

    GameStatements getNextStatement() {
        return nextStatement;
    }
}
