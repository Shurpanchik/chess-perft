package ru.pflb.perft;

import java.util.List;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Perft {

    public static int calculate(Board board, int depth) {

        if (depth == 0) {
            return 1;
        }

        List<Move> moves = board.genAllMoves();
        int positions = 0;
        for (Move move : moves) {
            int start = board.getFiguresCount();
            board.makeMove(move);
            int finish = board.getFiguresCount();
            if (start < finish) {
                System.out.println(board);
                throw new RuntimeException("Kill yourself");
            }
            if (board.isCheck(board.getOpponentColor())) {
                board.takeBack(move);
                continue;
            }

            positions += calculate(board, depth - 1);

            board.takeBack(move);

            finish = board.getFiguresCount();
            if (start != finish) {
                System.out.println(board);
                throw new RuntimeException("Kill yourself");
            }
        }

        return positions;
    }
}
