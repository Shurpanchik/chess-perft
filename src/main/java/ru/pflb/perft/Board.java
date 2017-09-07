package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static ru.pflb.perft.Piece.EMP;
import static ru.pflb.perft.Piece.OUT;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Board {

    private static final byte[] KING_OFFSETS = {+11, +10, +9, +1, -1, -9, -10, -11};
    private static final byte[] BISHOP_OFFSETS = {+11, +9, -9, -11};
    private static final byte[] ROOK_OFFSETS = {+10, +1, -1, -10};
    private static final byte[] KNIGHT_OFFSETS = {+21, +19, +12, +8, -8, -12, -19, -21};

    private byte kingPos[] = {0,0};
    private byte rookPos[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private byte bishopPos[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private byte knightPos[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private byte queenPos[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private final Color sideToMove;

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
        throw new NotImplementedException();
    }

    public List<Move> genKingMoves() {
        List<Move> moves = new ArrayList<>();
        for (byte offset : KING_OFFSETS) {
            byte from = kingPos[sideToMove.code];
            byte to = (byte) (from + offset);
            Piece toPiece = mailbox120[to];
            if ((EMP).equals(toPiece)) {
                moves.add(new Move(from, to, Piece.king(sideToMove)))
            } else if ((OUT).equals(toPiece)) {
                continue;
            } else {
                // взятие
            }
        }
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
