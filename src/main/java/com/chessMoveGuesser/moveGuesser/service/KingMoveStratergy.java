package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("KING")
public class KingMoveStratergy implements MoveStratergy {
    @Override
    public List<Position> getMoves(Position position , Board board) {
        return List.of();
    }
}
