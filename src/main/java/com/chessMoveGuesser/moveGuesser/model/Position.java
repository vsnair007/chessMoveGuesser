package com.chessMoveGuesser.moveGuesser.model;

import lombok.Data;

@Data
public class Position implements Comparable<Position> {
    private int row;
    private int column;

    public Position(String position) {
        position = position.trim().toUpperCase();
        this.row = Character.getNumericValue(position.charAt(1));
        this.column = position.charAt(0) - 'A' + 1;
    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }


    @Override
    public int compareTo(Position o) {
        int temp = this.getRow() - o.getRow();
        if (temp != 0) return temp;
        return this.getColumn() - o.getColumn();
    }

    public String toDto() {
        char colChar = (char) ('A' + column - 1);
        int rowNum = row;
        return "" + colChar + rowNum;
    }
}
