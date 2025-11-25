package com.chessMoveGuesser.moveGuesser.exception;

public class PieceNotFoundException extends RuntimeException {
    public PieceNotFoundException() {
        super();
    }

    public PieceNotFoundException(String message) {
        super(message);
    }
}
