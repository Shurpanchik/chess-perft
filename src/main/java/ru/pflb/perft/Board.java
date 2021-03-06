package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static ru.pflb.perft.Piece.EMP;
import static ru.pflb.perft.Piece.OUT;

import static ru.pflb.perft.Color.BLACK;
import static ru.pflb.perft.Color.WHITE;
import static ru.pflb.perft.Piece.*;
import static ru.pflb.perft.Square.*;
import static ru.pflb.perft.Value.val;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Board {

    private static final byte[] KING_OFFSETS = {+11, +10, +9, +1, -1, -9, -10, -11};
    private static final byte[] BISHOP_OFFSETS = {+11, +9, -9, -11};
    private static final byte[] ROOK_OFFSETS = {+10, +1, -1, -10};
    private static final byte[] KNIGHT_OFFSETS = {+21, +19, +12, +8, -8, -12, -19, -21};

    private byte kingPos[] = {0, 0};
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
    private Color sideToMove;

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
                case 'N':
                    mailbox120[square] = W_KNIGHT;
                    for (int i = 0; i < knightPos[WHITE.code].length; i++) {
                        if (knightPos[WHITE.code][i] == 0) {
                            knightPos[WHITE.code][i] = square;
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
                case 'n':
                    mailbox120[square] = B_KNIGHT;
                    for (int i = 0; i < knightPos[BLACK.code].length; i++) {
                        if (knightPos[BLACK.code][i] == 0) {
                            knightPos[BLACK.code][i] = square;
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
            } else if (mailbox120[to].getColor() == getOpponentColor()) {
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
                for (; mailbox120[to] == EMP; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_BISHOP : B_BISHOP));
                }
                if (mailbox120[to] == OUT) continue;
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
                for (; mailbox120[to] == EMP; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_ROOK : B_ROOK));
                }

                if (mailbox120[to] == OUT) continue;
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
            for (byte offset : KING_OFFSETS) {

                byte to = (byte) (from + offset);
                for (; mailbox120[to] == EMP; to += offset) {
                    // генерируем все ходы по пустым клеткам
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_QUEEN : B_QUEEN));
                }

                if (mailbox120[to] == OUT) continue;
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

            for (byte offset : KNIGHT_OFFSETS) {
                byte to = (byte) (from + offset);
                Piece toPiece = mailbox120[to];
                if ((EMP).equals(toPiece)) {
                    moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KNIGHT : B_KNIGHT));
                } else if ((OUT).equals(toPiece)) {
                    continue;
                } else {
                    // взятие
                    if (mailbox120[to].getColor() == getOpponentColor()) {
                        moves.add(new Move(new Square(from), new Square(to), sideToMove == WHITE ? W_KNIGHT : B_KNIGHT, mailbox120[to]));
                    }
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

        byte posKing = kingPos[kingColor.code];
        // Проверяем что короли не бьют друг друга
        int deltaKing = kingPos[sideToMove.code]-kingPos[getOpponentColor().code];
        if( deltaKing == 11 || deltaKing == -11 || deltaKing== 10 || deltaKing == -10 ||
                deltaKing == 9||deltaKing == -9||deltaKing== 1 || deltaKing == -1)
        {return true;}

        // Проверяем что ладья не может бить короля
        for (int i = 0; i < rookPos[sideToMove.code].length && rookPos[sideToMove.code][i] != 0; i++) {
            // не совпадают по вертикали и горизонтали (деление и остаток от деления, то точно не бьют)
            if(rookPos[sideToMove.code][i]%10 != posKing%10 && rookPos[sideToMove.code][i]/10 != posKing/10 ){
                continue;
            }
            else {
                // то проверяем явной генерацией ходов
                // вычисляем offset для ладьи
               byte offset =findRookOffset(rookPos,i,posKing);

                if(isCheckGeneration(rookPos,i,posKing,offset)){return true;}
            }
        }
        // Проверяем что слон не может бить короля
        for (int i = 0; i < bishopPos[sideToMove.code].length && bishopPos[sideToMove.code][i] != 0; i++) {
            // если слон и король стоят  на одной диагонали, то проверяем явной генерацией ходов
            if(((bishopPos[sideToMove.code][i]%10 - bishopPos[sideToMove.code][i]/10 ) == (posKing%10-posKing/10 ))||
                    ((bishopPos[sideToMove.code][i]%10 + bishopPos[sideToMove.code][i]/10 ) == (posKing%10+posKing/10 ))){
                // то проверяем явной генерацией ходов
                byte offset =findBishopOffset(bishopPos,i,posKing);
                if( isCheckGeneration(bishopPos,i,posKing,offset)){return true;}
            }
            else {
                continue;
            }
        }
        // проверяем, что ферзь не бьет короля
        for (int i = 0; i < queenPos[sideToMove.code].length && queenPos[sideToMove.code][i] != 0; i++) {
            // если ферзь и король стоят  на одной диагонали или горизонтали, то проверяем явной генерацией ходов

            // проверка диагоналей
            if(((queenPos[sideToMove.code][i]%10 - queenPos[sideToMove.code][i]/10 ) == (posKing%10-posKing/10 ))||
                    ((queenPos[sideToMove.code][i]%10 + queenPos[sideToMove.code][i]/10 ) == (posKing%10+posKing/10)
                    )){
                byte offset = findBishopOffset(queenPos,i,posKing);
                if( isCheckGeneration(queenPos,i,posKing,offset)){return true;}
            }
            else if(queenPos[sideToMove.code][i]%10 != posKing%10 || queenPos[sideToMove.code][i]/10 != posKing/10){
                byte offset = findRookOffset(queenPos,i,posKing);
                if( isCheckGeneration(queenPos,i,posKing,offset)){return true;}
            }
            else {
                continue;
            }
        }
        List<Move>list = genKnightMoves();


        for (Move move:list) {
            if(move.to.getValue() == posKing ){
                return true;
            }
        }
        return false;
    }

    private boolean isCheckGeneration(byte pos[][], int i, byte posKing,byte offset ){
        // то проверяем явной генерацией ходов
        byte from = pos[sideToMove.code][i];
        // для каждой ладьи проходим по всем направлениям

            byte to = (byte) (from + offset);

                for (;to>0 && to<119 && mailbox120[to] == EMP; to += offset) {
                    // если встали на позицию короля
                    if (to == posKing) {
                        return true;
                    }
                }
                // если встали на позицию короля
                if (to>0 && to<119 && to == posKing) {
                    return true;
                }
        return false;
    }

    private byte findRookOffset(byte pos[][],int i, byte posKing){
        byte offset ;
        if(Math.abs(posKing-pos[sideToMove.code][i])<10) {
            if (posKing>pos[sideToMove.code][i]){
                offset = 1;
            }
            else {
                offset = -1;
            }
        }else {
            if (posKing>pos[sideToMove.code][i]){
                offset = 10;
            }
            else {
                offset = -10;
            }
        }
        return offset;
    }
    private byte findBishopOffset(byte pos[][],int i, byte posKing){
        byte offset ;
        // король выше или ниже слона
        // если король ниже по доске
        if(posKing>pos[sideToMove.code][i]) {
            // справа или слева
            if (posKing%10>pos[sideToMove.code][i]%10){
                offset = 11;
            }
            else {
                offset = 9;
            }
        }else {
            if (posKing%10>pos[sideToMove.code][i]%10){
                offset = -9;
            }
            else {
                offset = -11;
            }
        }
        return offset;
    }
    private void makeNonKingMove(Move move, byte[][] piecePos, Color color) {
        for (int i = 0; i < piecePos[color.code].length; i++) {
            if (piecePos[color.code][i] == val(move.from)) {
                piecePos[color.code][i] = val(move.to);
                break;
            }
        }
    }

    private void makeCapture(Move move, byte[][] piecePos, Color color) {
        for (int i = 0; i < piecePos[color.code].length; i++) {
            if (piecePos[color.code][i] == val(move.to)) {
                // сдвигаем все остальные значения на единицу, заполняя выбывшую фигуру, пока не встретим 0
                // таким образом массив будет всегда содержать нулевые значения в конце
                for (int j = i + 1;j < piecePos[color.code].length; j++) {
                    piecePos[color.code][j - 1] = piecePos[color.code][j];
                }
                break;
            }
        }
    }

    private void takeBackCapture(Move move, byte[][] piecePos, Color color) {
        for (int i = 0; i < piecePos[color.code].length; i++) {
            if (piecePos[color.code][i] == 0) {
                // выставляем вернувшуюся фигуру в первое ненулевое окно
                piecePos[color.code][i] = val(move.to);
                break;
            }
        }
    }

    private void takeBackFigure(Move move, byte[][] piecePos,Color color){
        for(int i =0;i<piecePos.length;i++){
            if(piecePos[color.code][i]==val(move.to)){
                piecePos[color.code][i] = val(move.from);
            }
        }
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
                makeNonKingMove(move, bishopPos, WHITE);
                break;
            case W_ROOK:
                makeNonKingMove(move, rookPos, WHITE);
                break;
            case W_QUEEN:
                makeNonKingMove(move, queenPos, WHITE);
                break;
            case W_KNIGHT:
                makeNonKingMove(move, knightPos, WHITE);
                break;
            case B_BISHOP:
                makeNonKingMove(move, bishopPos, BLACK);
                break;
            case B_ROOK:
                makeNonKingMove(move, rookPos, BLACK);
                break;
            case B_QUEEN:
                makeNonKingMove(move, queenPos, BLACK);
                break;
            case B_KNIGHT:
                makeNonKingMove(move, knightPos, BLACK);
                break;
            default:
                throw new IllegalStateException("Неправильоне состояние");
        }

        if (move.isCapture()) {
            switch (move.capture.get()) {
                case W_BISHOP:
                    makeCapture(move, bishopPos, WHITE);
                    break;
                case W_ROOK:
                    makeCapture(move, rookPos, WHITE);
                    break;
                case W_QUEEN:
                    makeCapture(move, queenPos, WHITE);
                    break;
                case W_KNIGHT:
                    makeCapture(move, knightPos, WHITE);
                    break;
                case B_BISHOP:
                    makeCapture(move, bishopPos, BLACK);
                    break;
                case B_ROOK:
                    makeCapture(move, rookPos, BLACK);
                    break;
                case B_QUEEN:
                    makeCapture(move, queenPos, BLACK);
                    break;
                case B_KNIGHT:
                    makeCapture(move, knightPos, BLACK);
                    break;
                case W_KING:
                case B_KING:
                    break;
                default:
                    System.err.println(this);
                    throw new IllegalStateException("Неправильоне взятие");
            }
        }

        // обновляем очередь хода
        this.sideToMove = this.sideToMove == WHITE ? BLACK : WHITE;
    }

    public void takeBack(Move move) {
        mailbox120[val(move.from)] = move.piece;
        switch (move.piece) {
            case W_KING:
                kingPos[WHITE.code] = val(move.from);
                break;
            case B_KING:
                kingPos[BLACK.code] = val(move.from);
                break;
            case W_BISHOP:
                takeBackFigure(move, bishopPos, WHITE);
                break;
            case W_ROOK:
                takeBackFigure(move, rookPos, WHITE);
                break;
            case W_QUEEN:
                takeBackFigure(move, queenPos, WHITE);
                break;
            case W_KNIGHT:
                takeBackFigure(move, knightPos, WHITE);
                break;
            case B_BISHOP:
                takeBackFigure(move, bishopPos, BLACK);
                break;
            case B_ROOK:
                takeBackFigure(move, rookPos, BLACK);
                break;
            case B_QUEEN:
                takeBackFigure(move, queenPos, BLACK);
                break;
            case B_KNIGHT:
                takeBackFigure(move, knightPos, BLACK);
                break;
            default:
                throw new IllegalStateException("Неправильоне состояние");
        }
        if (move.isCapture()) {
            mailbox120[val(move.to)] = move.capture.get();

            switch (move.capture.get()) {
                case W_BISHOP:
                    takeBackCapture(move, bishopPos, WHITE);
                    break;
                case W_ROOK:
                    takeBackCapture(move, rookPos, WHITE);
                    break;
                case W_QUEEN:
                    takeBackCapture(move, queenPos, WHITE);
                    break;
                case W_KNIGHT:
                    takeBackCapture(move, knightPos, WHITE);
                    break;
                case B_BISHOP:
                    takeBackCapture(move, bishopPos, BLACK);
                    break;
                case B_ROOK:
                    takeBackCapture(move, rookPos, BLACK);
                    break;
                case B_QUEEN:
                    takeBackCapture(move, queenPos, BLACK);
                    break;
                case B_KNIGHT:
                    takeBackCapture(move, knightPos, BLACK);
                    break;
                default:
                    System.out.println(kingPos[sideToMove.code]-kingPos[getOpponentColor().code]);
                    System.out.println(move.capture);
                    throw new IllegalStateException("Неправильоне взятие");
            }

        } else {
            mailbox120[val(move.to)] = move.isCapture() ? move.capture.get() : EMP;
        }
        // возвращаем очередь хода
        this.sideToMove = this.sideToMove == WHITE ? BLACK : WHITE;
    }


    public Color getOpponentColor() {
        return sideToMove == WHITE ? BLACK : WHITE;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int s = A8.value; s >= H8.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A7.value; s >= H7.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A6.value; s >= H6.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A5.value; s >= H5.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A4.value; s >= H4.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A3.value; s >= H3.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A2.value; s >= H2.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }
        sb.append("\n");
        for (int s = A1.value; s >= H1.value; s--) {
            sb.append(mailbox120[s]).append(" ");
        }

        return sb.toString();
    }

    public int getFiguresCount() {
        return (int) Arrays.stream(mailbox120).filter(f -> f != EMP && f != OUT).count();
    }
}
