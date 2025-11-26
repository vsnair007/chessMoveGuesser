package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;

import java.util.List;

public interface MoveGuesserService {

    String getPossibleMoves(Position position, Pieces piece);

}
