package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation that computes possible moves for a given chess piece and board position.
 *
 * <p>The board size is read from the {@code board.size} property and used to construct a {@link Board}
 * instance which validates positions. Move computation is delegated to a {@link MoveStratergyFactory}
 * that returns the appropriate {@code MoveStratergy} for a {@link Pieces} value.</p>
 *
 * <p>Results are cached in the Caffeine-backed cache named {@code moves} to avoid repeated computation
 * for the same piece + position input.</p>
 */
@Service
public class MoveGuesserServiceImpl implements MoveGuesserService {

    /**
     * Board used to validate bounds for generated positions.
     * Constructed from the configured {@code board.size} property.
     */
    private final Board board;

    /**
     * Factory that maps {@link Pieces} to their respective {@code MoveStratergy} implementations.
     * Injected by Spring; used to obtain the algorithm for computing moves for the requested piece.
     */
    private final MoveStratergyFactory moveStratergyFactory;

    /**
     * Construct the service with the configured board size and a strategy factory.
     *
     * @param size configured board size (both rows and columns)
     * @param moveStratergyFactory factory that provides move strategies by piece type
     */
    @Autowired
    public MoveGuesserServiceImpl(@Value("${board.size}") int size, MoveStratergyFactory moveStratergyFactory) {
        // Create a square board of the provided size
        this.board = new Board(size, size);
        this.moveStratergyFactory = moveStratergyFactory;
    }

    /**
     * Compute possible moves for the given piece at the given position.
     *
     * <p>Results are cached in the {@code moves} cache. The cache key is a concatenation of the piece
     * and the position string: {@code T(java.lang.String).valueOf(#piece) + '_' + #position.toString()}.
     * This produces a stable string key such as {@code KNIGHT_g1}. Using the piece enum's name and the
     * position's {@code toString()} ensures uniqueness per input.</p>
     *
     * @param position starting board position
     * @param piece piece type for which moves should be calculated
     * @return comma-separated DTO strings for each valid target position (e.g. "e4, f6")
     */
    @Cacheable(value = "moves", key = "T(java.lang.String).valueOf(#piece) + '_' + #position.toString()")
    @Override
    public String getPossibleMoves(Position position, Pieces piece) {
        // Obtain the strategy implementation for the requested piece
        MoveStratergy strategy = moveStratergyFactory.getStrategy(piece);

        // Delegate to the strategy to compute candidate positions, passing the board for validation
        List<Position> guessedPositions = strategy.getMoves(position, board);

        // Convert positions to their DTO representation and join into a single string response
        return guessedPositions.stream()
                .map(Position::toDto)
                .collect(Collectors.joining(", "));
    }
}
