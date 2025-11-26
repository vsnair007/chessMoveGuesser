package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("KING")
public class KingMoveStratergy implements MoveStratergy {
    @Override
    public List<Position> getMoves(Position position, Board board) {
        int[][] possibleDirection = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        return Arrays.stream(possibleDirection)
                .map(step -> new Position(position.getRow() + step[0], position.getColumn() + step[1]))
                .filter(board::isValid)
                .distinct()
                .sorted()
                .toList();
    }
}
