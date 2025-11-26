package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Move strategy for the King piece.
 *
 * <p>Exposes a Spring component named {@code "KING"} so the factory can look it up by piece name.
 * The King can move one square in any of the eight surrounding directions; this class computes
 * those candidate positions, validates them against the provided {@link Board}, removes duplicates,
 * sorts them, and returns the final list.</p>
 */
@Component("KING")
public class KingMoveStratergy implements MoveStratergy {

    /**
     * Compute all legal king moves from the given position on the provided board.
     *
     * <p>Algorithm overview:
     * - Define the 8 possible one-step direction deltas (rowDelta, colDelta).
     * - For each delta produce a candidate {@link Position} by adding the deltas to the source.
     * - Filter out positions outside the board using {@code board.isValid(...)}.
     * - Remove duplicates, sort the results (relies on {@code Position}'s {@code Comparable} implementation),
     *   and collect to a list.</p>
     *
     * @param position current position of the king
     * @param board board used to validate bounds for generated positions
     * @return list of valid target positions the king can move to
     */
    @Override
    public List<Position> getMoves(Position position, Board board) {
        // 8 direction vectors representing one-step moves around the king:
        // {rowDelta, columnDelta}
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

        return Arrays.stream(possibleDirection)
                // Convert direction delta to an absolute Position
                .map(step -> new Position(position.getRow() + step[0], position.getColumn() + step[1]))
                // Keep only positions that are within the board bounds
                .filter(board::isValid)
                // Remove duplicate positions if any (requires proper equals/hashCode on Position)
                .distinct()
                // Sort positions (relies on Position implementing Comparable)
                .sorted()
                // Collect to a List
                .toList();
    }
}
