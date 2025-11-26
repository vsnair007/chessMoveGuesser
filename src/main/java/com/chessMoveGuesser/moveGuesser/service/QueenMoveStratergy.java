package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("QUEEN")
public class QueenMoveStratergy implements MoveStratergy {
    @Override
    public List<Position> getMoves(Position position, Board board) {
        int[][] possibleDirection = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        List<Position> moves = new ArrayList<>();

        int rCounter = 0;
        int cCounter = 0;
        for (int[] direction : possibleDirection) {
            rCounter = position.getRow();
            cCounter = position.getColumn();

            while (true) {
                rCounter += direction[0];
                cCounter += direction[1];
                Position newPosition = new Position(rCounter, cCounter);
                if (!board.isValid(newPosition)) {
                    break;
                }
                moves.add(newPosition);
            }
        }

        return moves.stream().distinct().sorted().toList();

    }
}
