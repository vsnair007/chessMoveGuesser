package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;

import java.util.List;

public interface MoveStratergy {
    public List<Position> getMoves(Position position, Board board);
}
