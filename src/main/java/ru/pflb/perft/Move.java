package ru.pflb.perft;

import ru.pflb.perft.exception.NotImplementedException;

import java.util.Optional;

/**
 * @author <a href="mailto:8445322@gmail.com">Ivan Bonkin</a>.
 */
public class Move {

    private final Square from, to;

    private final Piece piece;

    private final Optional<Piece> capture;

    public Move(Square from, Square to, Piece piece) {
        this(from, to, piece, null);
    }

    public Move(Square from, Square to, Piece piece, Piece capture) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capture = Optional.ofNullable(capture);
    }

    public String toString() {
        throw new NotImplementedException();
    }
}
