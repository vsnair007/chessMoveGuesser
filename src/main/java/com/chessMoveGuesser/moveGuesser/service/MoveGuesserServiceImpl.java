package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.model.Board;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveGuesserServiceImpl implements MoveGuesserService {

    private final Board board;

    private final MoveStratergyFactory moveStratergyFactory;

    @Autowired
    public MoveGuesserServiceImpl(@Value("${board.size}") int size, MoveStratergyFactory moveStratergyFactory) {
        this.board = new Board(size, size);
        this.moveStratergyFactory = moveStratergyFactory;
    }


    @Override
    public List<Position> getPossibleMoves(Position position, Pieces piece) {
        MoveStratergy strategy = moveStratergyFactory.getStrategy(piece);
        return strategy.getMoves(position, board);
    }
}
