package com.chessMoveGuesser.moveGuesser.model;

import com.chessMoveGuesser.moveGuesser.exception.PieceNotFoundException;

public enum Pieces {
    KING,
    QUEEN,
    PAWN;

    public static Pieces fromString(String value) {
        try {
            return Pieces.valueOf(value.trim().toUpperCase());
        } catch (Exception ex) {
            throw new PieceNotFoundException("Invalid piece: " + value);
        }
    }
}
