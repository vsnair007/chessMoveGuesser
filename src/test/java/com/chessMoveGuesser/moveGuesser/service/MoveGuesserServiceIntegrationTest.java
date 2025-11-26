package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.exception.PostionOutOfBoardException;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "board.size=8")
public class MoveGuesserServiceIntegrationTest {

    @Autowired
    private MoveGuesserServiceImpl service;

    @Test
    void pawn_fromA1_movesToA2() {
        String result = service.getPossibleMoves(new Position("A1"), Pieces.PAWN);
        assertEquals("A2", result);
    }

    @Test
    void king_fromD5_hasEightMoves() {
        String result = service.getPossibleMoves(new Position("D5"), Pieces.KING);
        assertEquals(
                "C4, C5, C6, D4, D6, E4, E5, E6",
                result
        );
    }

    @Test
    void queen_fromE4_movesCorrectly() {
        String result = service.getPossibleMoves(new Position("E4"), Pieces.QUEEN);

        String expected =
                "A4, A8, B1, B4, B7, C2, C4, C6, D3, D4, D5, E1, E2, E3, E5, E6, E7, E8, F3, F4, F5, G2, G4, G6, H1, H4, H7";

        assertEquals(expected, result);
    }

    @Test
    void pawn_fromLastRank_returnsEmpty() {
        String result = service.getPossibleMoves(new Position("A8"), Pieces.PAWN);
        assertEquals("", result);
    }

    @Test
    void king_fromA1_hasOnlyThreeMoves() {
        String result = service.getPossibleMoves(new Position("A1"), Pieces.KING);
        assertEquals("A2, B1, B2", result);
    }

    @Test
    void invalidPosition_throwsException() {
        Exception ex = assertThrows(
                PostionOutOfBoardException.class,
                () -> service.getPossibleMoves(new Position("Z9"), Pieces.QUEEN)
        );

        assertTrue(ex.getMessage().contains("out of board"));
    }
}
