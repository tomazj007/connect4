package connect4;


/**
 * File: Minimax.java
 * Author: Brian Borowski
 * Date created: April 10, 2012
 * Date last modified: August 6, 2014
 */
public class Minmax_test {
    private final Board board;
    private int column, boardsAnalyzed, maxDepth;
    private boolean redWinFound, blackWinFound;

    public Minmax_test(Board board, int maxDepth) {
        this.board = board;
        this.boardsAnalyzed = 0;
        this.maxDepth = maxDepth;
    }

    public int getBoardsAnalyzed() {
        return boardsAnalyzed;
    }

    public int alphaBeta(int player) {
        redWinFound = blackWinFound = false;
        if (player == Board.MARK_BLACK) {
            evaluateBlackMove(0, 1, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
            if (blackWinFound) {
                return column;
            }
            redWinFound = blackWinFound = false;
            evaluateRedMove(0, 1, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
            if (redWinFound) {
                return column;
            }
            evaluateBlackMove(0, maxDepth, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
        } else {
            evaluateRedMove(0, 1, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
            if (redWinFound) {
                return column;
            }
            redWinFound = blackWinFound = false;
            evaluateBlackMove(0, 1, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
            if (blackWinFound) {
                return column;
            }
            evaluateRedMove(0, maxDepth, -1, Integer.MIN_VALUE + 1,
                    Integer.MAX_VALUE - 1);
        }
        return column;
    }

    private int evaluateRedMove(int depth, int maxDepth, int col, int alpha, int beta) {
        boardsAnalyzed++;
        int min = Integer.MAX_VALUE, score = 0;
        if (col != -1) {
            score = board.getHeuristicScore(Board.MARK_BLACK, col, depth, maxDepth);
            if (board.blackWinFound()) {
                blackWinFound = true;
                return score;
            }
        }
        if (depth == maxDepth) {
            return score;
        }
        for (int c = 0; c < Board.COLUMNS; c++) {
            if (board.isColumnAvailable(c)) {
                board.mark(c, Board.MARK_RED);
                int value = evaluateBlackMove(depth + 1, maxDepth, c, alpha, beta);
                board.unset(c);
                if (value < min) {
                    min = value;
                    if (depth == 0) {
                        column = c;
                    }
                }
                if (value < beta) {
                    beta = value;
                }
                if (alpha >= beta) {
                    return beta;
                }
            }
        }

        if (min == Integer.MAX_VALUE) {
            return 0;
        }
        return min;
    }

    private int evaluateBlackMove(int depth, int maxDepth, int col, int alpha, int beta) {
        boardsAnalyzed++;
        int max = Integer.MIN_VALUE, score = 0;
        if (col != -1) {
            score = board.getHeuristicScore(Board.MARK_RED, col, depth, maxDepth);
            if (board.redWinFound()) {
                redWinFound = true;
                return score;
            }
        }
        if (depth == maxDepth) {
            return score;
        }
        for (int c = 0; c < Board.COLUMNS; c++) {
            if (board.isColumnAvailable(c)) {
                board.mark(c, Board.MARK_BLACK);
                int value = evaluateRedMove(depth + 1, maxDepth, c, alpha, beta);
                board.unset(c);
                if (value > max) {
                    max = value;
                    if (depth == 0) {
                        column = c;
                    }
                }
                if (value > alpha) {
                    alpha = value;
                }
                if (alpha >= beta) {
                    return alpha;
                }
            }
        }
        if (max == Integer.MIN_VALUE) {
            return 0;
        }
        return max;
    }

    public static void main(String[] args) {
        // This section is for testing purposes only, in cases where the
        // computer makes a seemingly bad choice.
        Board board = new Board();
//        board.set(0, Board.MARK_BLACK);
//        board.set(0, Board.MARK_BLACK);
//
//        board.set(1, Board.MARK_BLACK);
//        board.set(1, Board.MARK_RED);
//        board.set(1, Board.MARK_BLACK);
//        board.set(1, Board.MARK_BLACK);
//        board.set(1, Board.MARK_RED);
//        board.set(1, Board.MARK_RED);
//
//        board.set(2, Board.MARK_RED);
//        board.set(2, Board.MARK_RED);
//        board.set(2, Board.MARK_RED);
//        board.set(2, Board.MARK_BLACK);
//        board.set(2, Board.MARK_RED);
//
        board.set(3, Board.MARK_RED);
//        board.set(3, Board.MARK_BLACK);
//        board.set(3, Board.MARK_RED);
//        board.set(3, Board.MARK_BLACK);
//        board.set(3, Board.MARK_BLACK);
//        board.set(3, Board.MARK_BLACK);
//
//        board.set(4, Board.MARK_RED);
//        board.set(4, Board.MARK_RED);
//        board.set(4, Board.MARK_BLACK);
//        board.set(4, Board.MARK_RED);
//
//        board.set(6, Board.MARK_BLACK);
//        board.set(6, Board.MARK_BLACK);
//        board.set(6, Board.MARK_BLACK);
//        board.set(6, Board.MARK_RED);
//        board.set(6, Board.MARK_BLACK);

        board.display();
        Minmax_test minimax = new Minmax_test(board, 8);
        char mark = Board.MARK_BLACK;
        int col = minimax.alphaBeta(mark);
        System.out.println("Place in column: " + col);
        System.out.println("Boards analyzed: " + minimax.getBoardsAnalyzed());
        board.set(col, mark);
        board.display();
    }
}