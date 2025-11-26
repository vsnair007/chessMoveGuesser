package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Move strategy for the Queen piece.
 *
 * <p>Registered as a Spring component named {@code "QUEEN"} so the {@code MoveStratergyFactory}
 * can look it up by the piece enum name.</p>
 *
 * <p>The Queen combines the movement patterns of the Rook and Bishop: it can slide any number
 * of squares along the eight cardinal and diagonal directions. This implementation iterates
 * outward from the source position in each direction, collects valid positions until the
 * board boundary is reached, and returns a sorted, deduplicated list of targets.</p>
 */
@Component("QUEEN")
public class QueenMoveStratergy implements MoveStratergy {
    /**
     * Compute all legal queen moves from the given position on the provided board.
     *
     * Algorithm summary:
     * - Define the 8 direction vectors (rowDelta, colDelta) for straight and diagonal moves.
     * - For each direction, step outward repeatedly until an invalid position is reached.
     * - Validate each candidate with {@code board.isValid(...)} before adding.
     * - After collecting all moves, remove duplicates and sort the result before returning.
     *
     * @param position current queen position
     * @param board board used to validate bounds for generated positions
     * @return sorted list of valid target positions the queen can move to
     */
    @Override
    public List<Position> getMoves(Position position, Board board) {
        // Direction vectors
        int[][] possibleDirection = {
                {-1, 0},  // up
                {-1, 1},  // up-right
                {0, 1},   // right
                {1, 1},   // down-right
                {1, 0},   // down
                {1, -1},  // down-left
                {0, -1},  // left
                {-1, -1}  // up-left
        };

        // Collect sliding moves from the source position
        List<Position> moves = new ArrayList<>();

        int rCounter;
        int cCounter;
        for (int[] direction : possibleDirection) {
            // start stepping from the current position for this direction
            rCounter = position.getRow();
            cCounter = position.getColumn();

            // slide outward until the position goes off the board
            while (true) {
                rCounter += direction[0];
                cCounter += direction[1];
                Position newPosition = new Position(rCounter, cCounter);

                // stop sliding in this direction when out of bounds
                if (!board.isValid(newPosition)) {
                    break;
                }

                // valid candidate position; add to the list
                moves.add(newPosition);
            }
        }

        // Remove duplicates (if any) and sort positions before returning
        return moves.stream()
                .distinct()
                .sorted()
                .toList();
    }
}
