package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;

public interface MoveGuesserService {

    String getPossibleMoves(Position position, Pieces piece);

}
