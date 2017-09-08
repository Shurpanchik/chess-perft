package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

import static ru.pflb.perft.Piece.EMP;
import static ru.pflb.perft.Piece.OUT;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;
import static ru.pflb.perft.Piece.*;
import static ru.pflb.perft.Value.val;

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
        String[] fenParts = fen.split("\\s");

        sideToMove = fenParts[1].startsWith("w") ? WHITE : BLACK;

        for (byte square = 98, fenIndex = 0; fenIndex < fenParts[0].length(); fenIndex++, square--) {
            char c = fenParts[0].charAt(fenIndex);
            switch (c) {
                case 'K':
                    mailbox120[square] = W_KING;
                    kingPos[WHITE.code] = square;
                    break;
                case 'R':
                    mailbox120[square] = W_ROOK;
                    for (int i = 0; i < rookPos[WHITE.code].length; i++) {
                        if (rookPos[WHITE.code][i] == 0) {
                            rookPos[WHITE.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'B':
                    mailbox120[square] = W_BISHOP;
                    for (int i = 0; i < bishopPos[WHITE.code].length; i++) {
                        if (bishopPos[WHITE.code][i] == 0) {
                            bishopPos[WHITE.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'Q':
                    mailbox120[square] = W_QUEEN;
                    for (int i = 0; i < queenPos[WHITE.code].length; i++) {
                        if (queenPos[WHITE.code][i] == 0) {
                            queenPos[WHITE.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'k':
                    mailbox120[square] = B_KING;
                    kingPos[BLACK.code] = square;
                    break;
                case 'r':
                    mailbox120[square] = B_ROOK;
                    for (int i = 0; i < rookPos[BLACK.code].length; i++) {
                        if (rookPos[BLACK.code][i] == 0) {
                            rookPos[BLACK.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'b':
                    mailbox120[square] = B_BISHOP;
                    for (int i = 0; i < bishopPos[BLACK.code].length; i++) {
                        if (bishopPos[BLACK.code][i] == 0) {
                            bishopPos[BLACK.code][i] = square;
                            break;
                        }
                    }
                    break;
                case 'q':
                    mailbox120[square] = B_QUEEN;
                    for (int i = 0; i < queenPos[BLACK.code].length; i++) {
                        if (queenPos[BLACK.code][i] == 0) {
                            queenPos[BLACK.code][i] = square;
                            break;
                        }
                    }
                    break;
                case '/':
                    square -= 1;
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                    square -= c - '1';
                    break;
                default:
                    throw new IllegalStateException("Недопустимый символ - " + c);
            }
        }
    }

    public List<Move> genKingMoves() {
        List<Move> moves = new ArrayList<>();
        for (byte offset : KING_OFFSETS) {
            byte from = kingPos[sideToMove.code];
            byte to = (byte) (from + offset);
            Piece toPiece = mailbox120[to];
            if ((EMP).equals(toPiece)) {
                moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KING : B_KING));
            } else if ((OUT).equals(toPiece)) {
                continue;
            } else {
                // взятие
                moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KING : B_KING, mailbox120[to]));
            }
        }
        return moves;
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
        mailbox120[val(move.from)] = EMP;
        mailbox120[val(move.to)] = move.piece;

        switch (move.piece) {
            case W_KING:
                kingPos[WHITE.code] = val(move.to);
                break;
            case B_KING:
                kingPos[BLACK.code] = val(move.to);
                break;
            case W_BISHOP:
                for (int i = 0; i < bishopPos[WHITE.code].length; i++) {
                    if (bishopPos[WHITE.code][i] == val(move.from)) {
                        bishopPos[WHITE.code][i] = val(move.to);
                        break;
                    }
                }
                break;
            // TODO - для остальных фигур сгенерировать тихие ходы
        }

        if (move.isCapture()) {
            switch (move.capture.get()) {
                case W_BISHOP:
                    for (int i = 0; i < bishopPos[WHITE.code].length; i++) {
                        if (bishopPos[WHITE.code][i] == val(move.to)) {
                            // сдвигаем все остальные значения на единицу, заполняя выбывшую фигуру, пока не встретим 0
                            // таким образом массив будет всегда содержать нулевые значения в конце
                            for (int j = i + 1; bishopPos[WHITE.code][j] != 0 && j < bishopPos[WHITE.code].length; j++) {
                                bishopPos[WHITE.code][j - 1] = bishopPos[WHITE.code][j];
                            }
                            break;
                        }
                    }
                // TODO - для остальных фигур сгенерировать взятия
            }
        }
    }

    public void takeBack(Move move) {
        mailbox120[val(move.from)] = move.piece;

        if (move.isCapture()) {
            mailbox120[val(move.to)] = move.capture.get();

            switch (move.capture.get()) {
                case W_BISHOP:
                    for (int i = 0; i < bishopPos[WHITE.code].length; i++) {
                        if (bishopPos[WHITE.code][i] == 0) {
                            // выставляем вернувшуюся фигуру в первое ненулевое окно
                            bishopPos[WHITE.code][i] = val(move.to);
                            break;
                        }
                    }
                    // TODO - для остальных фигур сгенерировать возврат фигуры
            }

        } else {
            mailbox120[val(move.to)] = move.isCapture() ? move.capture.get() : EMP;
        }
    }


}
