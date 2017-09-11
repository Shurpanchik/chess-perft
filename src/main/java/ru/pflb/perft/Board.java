package ru.pflb.perft;

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
    private static final byte[] QUEEN_OFFSETS ={+11, +10, +9, +1, -1, -9, -10, -11};

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
        List<Move> moves = new ArrayList<>();

        // проходим по всем слонам цвета ходящей стороны
        for (int i = 0; i < bishopPos[sideToMove.code].length && bishopPos[sideToMove.code][i] != 0; i++) {
            byte from = bishopPos[sideToMove.code][i];
            // для каждого слона проходим по всем направлениям
            for (byte offset : BISHOP_OFFSETS) {

                byte to = (byte) (from + offset);
                for (; mailbox120[to] == EMP ; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_BISHOP : B_BISHOP));
                }
                // генерируем взятие, если наткнулись на чужую фигуру
                if (mailbox120[to].getColor() == getOpponentColor()) {
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_BISHOP : B_BISHOP, mailbox120[to]));
                }
            }
        }

        return moves;
    }

    public List<Move> genRookMoves() {
        List<Move> moves = new ArrayList<>();

        // проходим по всем слонам цвета ходящей стороны
        for (int i = 0; i < rookPos[sideToMove.code].length && rookPos[sideToMove.code][i] != 0; i++) {
            byte from = rookPos[sideToMove.code][i];
            // для каждого слона проходим по всем направлениям
            for (byte offset : ROOK_OFFSETS) {

                byte to = (byte) (from + offset);
                for (; mailbox120[to] == EMP ; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_ROOK : B_ROOK));
                }
                // генерируем взятие, если наткнулись на чужую фигуру
                if (mailbox120[to].getColor() == getOpponentColor()) {
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_ROOK : B_ROOK, mailbox120[to]));
                }
            }
        }

        return moves;
    }

    public List<Move> genQueenMoves() {
        List<Move> moves = new ArrayList<>();

        // проходим по всем ферзям цвета ходящей стороны
        for (int i = 0; i < queenPos[sideToMove.code].length && queenPos[sideToMove.code][i] != 0; i++) {
            byte from = queenPos[sideToMove.code][i];
            // для каждого ферзя проходим по всем направлениям
            for (byte offset : QUEEN_OFFSETS) {

                byte to = (byte) (from + offset);
                for (; mailbox120[to] == EMP ; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_QUEEN : B_QUEEN));
                }
                // генерируем взятие, если наткнулись на чужую фигуру
                if (mailbox120[to].getColor() == getOpponentColor()) {
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_QUEEN : B_QUEEN, mailbox120[to]));
                }
            }
        }

        return moves;
    }

    public List<Move> genKnightMoves() {

        List<Move> moves = new ArrayList<>();
        // проходим по всем коням цвета ходящей стороны
        for (int i = 0; i < knightPos[sideToMove.code].length && knightPos[sideToMove.code][i] != 0; i++) {
            byte from = knightPos[sideToMove.code][i];
            // для каждого коня проходим по всем направлениям
            for (byte offset : KNIGHT_OFFSETS) {

                byte to = (byte) (from + offset);
                for (; mailbox120[to] == EMP ; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KNIGHT : B_KNIGHT));
                }
                // генерируем взятие, если наткнулись на чужую фигуру
                if (mailbox120[to].getColor() == getOpponentColor()) {
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KNIGHT : B_KNIGHT, mailbox120[to]));
                }
            }
        }
        return moves;
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

    /**
     * @param kingColor цвет короля, которому детектируется шах
     * Алгоритм:
     * Запоминаем позицию короля, делаем все возможные ходы за противоположную сторону
     * Если есть ход на позицию короля, то это шах
     */
    public boolean isCheck(Color kingColor) {
        // TODO - реализовать курсанту

        byte pos = kingPos[kingColor.code];

        List<Move>list = genAllMoves();

        for (Move move:list) {
            if(move.to.getValue() == kingPos[kingColor.code] && move.piece.getColor()!=kingColor ){
                return true;
            }
        }

        return false;
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
                makeQuietMove(move, bishopPos,WHITE);
                break;
            case B_BISHOP:
                makeQuietMove(move, bishopPos,BLACK);
                break;
            case W_ROOK:
                makeQuietMove(move,rookPos,WHITE);
                break;
            case B_ROOK:
                makeQuietMove(move,rookPos,BLACK);
                break;
            case W_QUEEN:
                makeQuietMove(move,queenPos,WHITE);
                break;
            case B_QUEEN:
                makeQuietMove(move,queenPos,BLACK);
                break;
            case W_KNIGHT:
                makeQuietMove(move,knightPos,WHITE);
                break;
            case B_KNIGHT:
                makeQuietMove(move,knightPos,BLACK);
                break;
        }

        if (move.isCapture()) {
            switch (move.capture.get()) {
                case W_BISHOP:
                    makeTakeMove(move, bishopPos,WHITE);
                    break;
                case B_BISHOP:
                    makeTakeMove(move, bishopPos,BLACK);
                    break;
                case W_ROOK:
                    makeTakeMove(move,rookPos,WHITE);
                    break;
                case B_ROOK:
                    makeTakeMove(move,rookPos,BLACK);
                    break;
                case W_QUEEN:
                    makeTakeMove(move,queenPos,WHITE);
                    break;
                case B_QUEEN:
                    makeTakeMove(move,queenPos,BLACK);
                    break;
                case W_KNIGHT:
                    makeTakeMove(move,knightPos,WHITE);
                    break;
                case B_KNIGHT:
                    makeTakeMove(move,knightPos,BLACK);
                    break;
            }
        }
    }

    /**
     * @param move
     * @param pos - массив
     * @param color - цвет фигуры
     * метод деланья тихого хода
     */
    private void makeQuietMove(Move move, byte pos[][], Color color ){
        for (int i = 0; i < pos[color.code].length; i++) {
            if (pos[color.code][i] == val(move.from)) {
                pos[color.code][i] = val(move.to);
                break;
            }
        }
    }
    /**
     * @param move
     * @param pos - массив
     * @param color - цвет фигуры
     * метод взятия фигурами
     */
    private void makeTakeMove(Move move, byte pos[][], Color color){
        for (int i = 0; i < pos[color.code].length; i++) {
            if (pos[color.code][i] == val(move.to)) {
                // сдвигаем все остальные значения на единицу, заполняя выбывшую фигуру, пока не встретим 0
                // таким образом массив будет всегда содержать нулевые значения в конце
                for (int j = i + 1;pos[color.code][j] != 0 && j < pos[color.code].length; j++) {
                    pos[color.code][j - 1] = pos[color.code][j];
                }
                break;
            }
        }
    }

    public void takeBack(Move move) {
        mailbox120[val(move.from)] = move.piece;

        if (move.isCapture()) {
            mailbox120[val(move.to)] = move.capture.get();

            switch (move.capture.get()) {
                case W_BISHOP:
                    takeBackCapture(move, bishopPos,WHITE);
                    break;
                case B_BISHOP:
                    takeBackCapture(move, bishopPos,BLACK);
                    break;
                case W_ROOK:
                    takeBackCapture(move,rookPos,WHITE);
                    break;
                case B_ROOK:
                    takeBackCapture(move,rookPos,BLACK);
                    break;
                case W_QUEEN:
                    takeBackCapture(move,queenPos,WHITE);
                    break;
                case B_QUEEN:
                    takeBackCapture(move,queenPos,BLACK);
                    break;
                case W_KNIGHT:
                    takeBackCapture(move,knightPos,WHITE);
                    break;
                case B_KNIGHT:
                    takeBackCapture(move,knightPos,BLACK);
                    break;
            }

        } else {
            mailbox120[val(move.to)] = move.isCapture() ? move.capture.get() : EMP;
        }
    }

    private void takeBackCapture(Move move, byte pos[][], Color color){
        for (int i = 0; i < pos[color.code].length; i++) {
            if (pos[color.code][i] == 0) {
                // выставляем вернувшуюся фигуру в первое ненулевое окно
                pos[color.code][i] = val(move.to);
                break;
            }
        }
    }


    public Color getOpponentColor() {
        return sideToMove == WHITE ? BLACK : WHITE;
    }
}
