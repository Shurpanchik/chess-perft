package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Board {

    private Piece[] mailbox120 = {
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 0-9
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 10-19
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 20-29
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 30-39
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 40-49
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 50-59
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 60-69
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 70-79
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 80-89
            OUT, EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP, OUT, // 90-99
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, // 100-109
            OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT, OUT  // 110-119
    };

    public Board(String fen) {
        // TODO
    }

    public List<Move> genKingMoves() {
        // TODO
        throw new NotImplementedException();
    }

    public List<Move> genBishopMoves() {
        // TODO
        throw new NotImplementedException();
    }

    public List<Move> genRookMoves() {
        // TODO
        throw new NotImplementedException();
    }

    public List<Move> genQueenMoves() {
        // TODO
        throw new NotImplementedException();
    }

    public List<Move> genKnightMoves() {
        // TODO
        throw new NotImplementedException();
    }

    public List<Move> genAllMoves() {
        List<Move> moves = new ArrayList<Move>();
        moves.addAll(genKingMoves());
        moves.addAll(genBishopMoves());
        moves.addAll(genRookMoves());
        moves.addAll(genQueenMoves());
        moves.addAll(genKnightMoves());
        return moves;
    }

    public boolean isCheck(Color sideToMove) {
        // TODO - реализовать курсанту
        throw new NotImplementedException();
    }

    public void makeMove(Move move) {
        // TODO
        throw new NotImplementedException();
    }

    public void takeBack(Move move) {
        // TODO
        throw new NotImplementedException();
    }


}
