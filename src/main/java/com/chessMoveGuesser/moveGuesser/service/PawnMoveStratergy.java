package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("PAWN")
public class PawnMoveStratergy implements MoveStratergy {
    @Override
    public List<Position> getMoves(Position position, Board board) {
        Position newPosition = new Position(position.getRow()+1, position.getColumn());
        if(board.isValid(newPosition)) {
            return List.of(newPosition);
        }
        return List.of();
    }
}
