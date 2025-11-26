package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Move strategy for the Pawn piece.
 *
 * <p>Registered as a Spring component with the name {@code "PAWN"} so it can be looked up by the
 * {@code MoveStratergyFactory} using the piece enum name.</p>
 *
 * <p>Behavior:
 * - Produces a single forward move by incrementing the row by 1 (assumes pawns move in the +row direction).
 * - Validates the target position against the provided {@link Board}.
 * - Returns a singleton list containing the valid target or an empty list if the move is out of bounds.
 * </p>
 *
 * <p>Limitations: does not implement captures, two-square initial move, promotion, or en-passant.</p>
 */
@Component("PAWN")
public class PawnMoveStratergy implements MoveStratergy {
    /**
     * Compute pawn moves from the given position on the provided board.
     *
     * @param position current pawn position
     * @param board board used to validate bounds for generated positions
     * @return list of valid target positions (at most one in this implementation)
     */
    @Override
    public List<Position> getMoves(Position position, Board board) {
        // Single forward step: increment the row by 1, keep the same column.
        Position newPosition = new Position(position.getRow() + 1, position.getColumn());

        // If the computed position is within the board bounds, return it as a singleton list.
        if (board.isValid(newPosition)) {
            return List.of(newPosition);
        }

        // Otherwise no legal forward move is available.
        return List.of();
    }
}
