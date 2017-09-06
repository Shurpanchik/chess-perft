package ru.pflb.perft;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Piece {

    public static final Piece OUT = new Piece((byte)-1);
    public static final Piece EMP = new Piece((byte)0);

    public final byte code;

    public Piece(byte pieceType, Color color) {
        this((byte)(color.code * 6 + pieceType + 1));
    }

    private Piece(byte code) {
        this.code = code;
    }


}
