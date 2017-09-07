package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.Optional;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Move {

    public final Square from, to;

    public final Piece piece;

    public final Optional<Piece> capture;

    public Move(Square from, Square to, Piece piece) {
        this(from, to, piece, null);
    }

    public Move(Square from, Square to, Piece piece, Piece capture) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capture = Optional.ofNullable(capture);
    }

    public boolean isCapture() {
        return capture.map(p -> true).orElse(false);
    }

    public String toString() {
        throw new NotImplementedException();
    }
}
