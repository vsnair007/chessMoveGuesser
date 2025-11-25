package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Pieces;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MoveStratergyFactory {
    private final Map<Pieces, MoveStratergy> moveStratergyMap;

    MoveStratergyFactory(Map<String, MoveStratergy> moveStratergyMap) {
        this.moveStratergyMap = new HashMap<>();
        moveStratergyMap.forEach((name, moveStratergy) ->
                this.moveStratergyMap.put(Pieces.fromString(name.trim().toUpperCase()), moveStratergy)
        );
    }

    public MoveStratergy getStrategy(Pieces piece) {
        return moveStratergyMap.get(piece);
    }
}
