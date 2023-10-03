package edu.hw1.tasks;

public final class Task8 {
    private Task8() {
    }

    private static boolean checkHorseFreeTurn(final int[][] board, int x, int y) {
        return (0 > x || x >= board.length) || (0 > y || y >= board.length) || (board[x][y] == 0);
    }

    public static boolean knightBoardCapture(final int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                if (!(checkHorseFreeTurn(board, i + 1, j + 2)
                    && checkHorseFreeTurn(board, i - 1, j + 2)
                    && checkHorseFreeTurn(board, i + 2, j + 1)
                    && checkHorseFreeTurn(board, i + 2, j - 1))) {
                    return false;
                }
            }
        }
        return true;
    }
}
