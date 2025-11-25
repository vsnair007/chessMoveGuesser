package com.chessMoveGuesser.moveGuesser.model;

import lombok.Data;

@Data
public class Board {
    private final int rows;
    private final int columns;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public boolean isValid(Position position) {
        return position.getRow() >= 1 &&
                position.getRow() <= rows &&
                position.getColumn() >= 1 &&
                position.getColumn() <= columns;
    }
}
