package com.chessMoveGuesser.moveGuesser.controller;

import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import com.chessMoveGuesser.moveGuesser.service.MoveGuesserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes an endpoint to retrieve possible chess moves
 * for a given piece at a specified board position.
 *
 * <p>Base path: <code>/api/v1/moveGuesser</code></p>
 * <p>
 * The controller delegates the move calculation to {@link MoveGuesserService}.
 */
@RestController
@RequestMapping("api/v1/moveGuesser")
@RequiredArgsConstructor
public class MoveGuesserController {

    /**
     * Service responsible for computing possible moves.
     * Injected via constructor by Lombok's {@code @RequiredArgsConstructor}.
     */
    private final MoveGuesserService moveGuesserService;

    /**
     * GET endpoint that returns possible moves for a chess piece at a given position.
     * <p>
     * Example request: GET /api/v1/moveGuesser?piece=KNIGHT&pos=g1
     *
     * @param piece the chess piece type as a string (case-insensitive). Expected values map to {@link Pieces}.
     * @param pos   the board position in standard algebraic notation (e.g. "E4", "G1").
     * @return an HTTP 200 response containing the possible moves as a string (format produced by {@link MoveGuesserService}).
     */
    @GetMapping
    public ResponseEntity<String> moveGuesser(@RequestParam String piece,
                                              @RequestParam String pos) {
        // Build a Position object from the provided string.
        Position position = new Position(pos);

        // Convert the piece string to the enum type; trim and normalize case to be safe.
        Pieces parsedPiece = Pieces.fromString(piece.trim().toUpperCase());

        // Delegate to the service and return the result in the response body.
        return ResponseEntity.ok(moveGuesserService.getPossibleMoves(position, parsedPiece));
    }
}
