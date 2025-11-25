package com.chessMoveGuesser.moveGuesser.controller;

import lombok.RequiredArgsConstructor;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chessMoveGuesser.moveGuesser.service.MoveGuesserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/moveGuesser")
@RequiredArgsConstructor
public class MoveGuesserController {

    private final MoveGuesserService moveGuesserService;

    @GetMapping
    public ResponseEntity<List<Position>> moveGuesser(@RequestParam String piece,
                                                      @RequestParam String pos) {
        return ResponseEntity.ok(moveGuesserService.getPossibleMoves(new Position(pos), Pieces.fromString(piece.trim().toUpperCase())));
    }

}
