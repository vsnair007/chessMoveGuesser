package com.chessMoveGuesser.moveGuesser.model;

import lombok.Data;

@Data
public class Position {
    private int row;
    private int column;

    public Position(String position) {
        position = position.trim().toUpperCase();
        this.row = position.charAt(1) - '0';
        this.column = position.charAt(0) - 'A' + 1;
    }
}
