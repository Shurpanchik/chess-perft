package ru.pflb.perft;

import java.util.Objects;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Piece {

    public static final Piece OUT = new Piece((byte)-1);
    public static final Piece EMP = new Piece((byte)0);

    public static Piece king(Color color) {
        return new Piece((byte)0, color);
    }

    public final byte code;

    public Piece(byte pieceType, Color color) {
        this((byte)(color.code * 6 + pieceType + 1));
    }

    private Piece(byte code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return code == piece.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
