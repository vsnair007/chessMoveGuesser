// java
package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Pieces;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory that provides a {@link MoveStratergy} implementation for a given {@link Pieces} value.
 *
 * <p>Spring injects a map of all {@code MoveStratergy} beans into the constructor where the map key is
 * the bean name. This class converts those bean names to {@link Pieces} keys and stores the result in
 * an internal map for fast lookup by piece enum.</p>
 *
 * <p>Bean names must correspond to piece identifiers (for example: "KNIGHT", "PAWN", etc.) or be
 * convertible via {@link Pieces#fromString(String)} after trimming and upper-casing.</p>
 */
@Component
public class MoveStratergyFactory {
    /**
     * Internal mapping from piece enum to its associated move strategy implementation.
     */
    private final Map<Pieces, MoveStratergy> moveStratergyMap;

    /**
     * Construct the factory by converting the injected bean map into a typed map keyed by {@link Pieces}.
     *
     * @param moveStratergyMap map of bean name -> MoveStratergy supplied by Spring (all beans of type MoveStratergy)
     */
    MoveStratergyFactory(Map<String, MoveStratergy> moveStratergyMap) {
        this.moveStratergyMap = new HashMap<>();
        // Convert bean name (string) to Pieces enum and store the corresponding strategy.
        // Trims and upper-cases the name to make the mapping tolerant to bean name formatting.
        moveStratergyMap.forEach((name, moveStratergy) ->
                this.moveStratergyMap.put(Pieces.fromString(name.trim().toUpperCase()), moveStratergy)
        );
    }

    /**
     * Retrieve the strategy for the requested piece.
     *
     * @param piece piece enum for which a strategy is required
     * @return the corresponding MoveStratergy, or null if none is registered for the piece
     */
    public MoveStratergy getStrategy(Pieces piece) {
        return moveStratergyMap.get(piece);
    }
}
