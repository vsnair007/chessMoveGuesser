package com.chessMoveGuesser.moveGuesser.service;

import com.chessMoveGuesser.moveGuesser.exception.PostionOutOfBoardException;
import com.chessMoveGuesser.moveGuesser.model.Pieces;
import com.chessMoveGuesser.moveGuesser.model.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for `MoveGuesserServiceImpl`.
 *
 * Tests:
 * - normal flow: strategy returns multiple positions -> concatenated DTO string returned
 * - empty moves: strategy returns empty list -> empty string returned
 * - invalid position: board reports invalid -> PostionOutOfBoardException thrown
 */
@ExtendWith(MockitoExtension.class)
class MoveGuesserServiceImplTest {

    @Mock
    private MoveStratergyFactory strategyFactory;

    @Mock
    private MoveStratergy strategy;

    @Mock
    private com.chessMoveGuesser.moveGuesser.model.Board mockBoard;

    /**
     * Helper to create the service and replace its private board with a mock via reflection.
     */
    private MoveGuesserServiceImpl createServiceAndInjectMockBoard(int boardSize) throws Exception {
        MoveGuesserServiceImpl service = new MoveGuesserServiceImpl(boardSize, strategyFactory);

        Field boardField = MoveGuesserServiceImpl.class.getDeclaredField("board");
        boardField.setAccessible(true);
        boardField.set(service, mockBoard);

        return service;
    }

    @Test
    public void getPossibleMoves_whenStrategyReturnsPositions_thenReturnsJoinedDtos() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position inputPosition = mock(Position.class);

        Position p1 = mock(Position.class);
        Position p2 = mock(Position.class);
        when(p1.toDto()).thenReturn("g1");
        when(p2.toDto()).thenReturn("e2");

        when(mockBoard.isValid(inputPosition)).thenReturn(true);
        when(strategyFactory.getStrategy(Pieces.KING)).thenReturn(strategy);
        when(strategy.getMoves(inputPosition, mockBoard)).thenReturn(Arrays.asList(p1, p2));

        String result = service.getPossibleMoves(inputPosition, Pieces.KING);

        assertEquals("g1, e2", result);
        verify(strategyFactory, times(1)).getStrategy(Pieces.KING);
        verify(mockBoard, times(1)).isValid(inputPosition);
        verify(strategy, times(1)).getMoves(inputPosition, mockBoard);
    }


    @Test
    public void getPossibleMoves_whenNoMoves_thenReturnsEmptyString() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position inputPosition = mock(Position.class);
        when(mockBoard.isValid(inputPosition)).thenReturn(true);
        when(strategyFactory.getStrategy(Pieces.KING)).thenReturn(strategy);
        when(strategy.getMoves(inputPosition, mockBoard)).thenReturn(Collections.emptyList());

        String result = service.getPossibleMoves(inputPosition, Pieces.KING);

        assertEquals("", result);
        verify(strategyFactory).getStrategy(Pieces.KING);
        verify(strategy).getMoves(inputPosition, mockBoard);
    }

    @Test
    public void getPossibleMoves_whenPositionOutOfBoard_thenThrowsPostionOutOfBoardException() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position invalidPosition = mock(Position.class);
        when(invalidPosition.toString()).thenReturn("z9");
        when(mockBoard.isValid(invalidPosition)).thenReturn(false);

        PostionOutOfBoardException ex = assertThrows(PostionOutOfBoardException.class,
                () -> service.getPossibleMoves(invalidPosition, Pieces.QUEEN));

        assertTrue(ex.getMessage().contains("out of board"));
        verify(mockBoard).isValid(invalidPosition);
        // strategyFactory should not be called when position is invalid
        verify(strategyFactory, never()).getStrategy(ArgumentMatchers.any());
    }

    @Test
    public void getPossibleMoves_whenUsingRealPositionObjects_thenReturnsJoinedDtos() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position inputPosition = new Position("D5");
        Position p1 = new Position("C4");
        Position p2 = new Position("C5");
        Position p3 = new Position("C6");
        Position p4 = new Position("D4");
        Position p5 = new Position("D6");
        Position p6 = new Position("E4");
        Position p7 = new Position("E5");
        Position p8 = new Position("E6");

        when(mockBoard.isValid(inputPosition)).thenReturn(true);
        when(strategyFactory.getStrategy(Pieces.KING)).thenReturn(strategy);
        when(strategy.getMoves(inputPosition, mockBoard)).thenReturn(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8));

        String result = service.getPossibleMoves(inputPosition, Pieces.KING);

        assertEquals("C4, C5, C6, D4, D6, E4, E5, E6", result);
        verify(mockBoard).isValid(inputPosition);
        verify(strategyFactory).getStrategy(Pieces.KING);
        verify(strategy).getMoves(inputPosition, mockBoard);
    }

    @Test
    public void getPossibleMoves_whenStrategyReturnsUnsortedPositions_preservesOrder() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position inputPosition = new Position("E4");
        List<Position> results = Arrays.stream("A4, B4, C4, D4, F4, G4, H4, E1, E2, E3, E5, E6, E7, E8, A8, B7, C6, D5, F3, G2, H1, B1, C2, D3, F5, G6, H7".split(", "))
                .map(String::trim)
                .map(Position::new)
                .toList();

        when(mockBoard.isValid(inputPosition)).thenReturn(true);
        when(strategyFactory.getStrategy(Pieces.QUEEN)).thenReturn(strategy);
        when(strategy.getMoves(inputPosition, mockBoard)).thenReturn(results);

        String result = service.getPossibleMoves(inputPosition, Pieces.QUEEN);

        assertEquals("A4, B4, C4, D4, F4, G4, H4, E1, E2, E3, E5, E6, E7, E8, A8, B7, C6, D5, F3, G2, H1, B1, C2, D3, F5, G6, H7", result);
        verify(mockBoard).isValid(inputPosition);
        verify(strategyFactory).getStrategy(Pieces.QUEEN);
        verify(strategy).getMoves(inputPosition, mockBoard);
    }

    @Test
    public void getPossibleMoves_whenStrategyReturnsSinglePosition_thenReturnsSingleDto() throws Exception {
        MoveGuesserServiceImpl service = createServiceAndInjectMockBoard(8);

        Position inputPosition = new Position("d4");
        Position p1 = new Position("D5");

        when(mockBoard.isValid(inputPosition)).thenReturn(true);
        when(strategyFactory.getStrategy(Pieces.PAWN)).thenReturn(strategy);
        when(strategy.getMoves(inputPosition, mockBoard)).thenReturn(Arrays.asList(p1));

        String result = service.getPossibleMoves(inputPosition, Pieces.PAWN);

        assertEquals("D5", result);
        verify(mockBoard).isValid(inputPosition);
        verify(strategyFactory).getStrategy(Pieces.PAWN);
        verify(strategy).getMoves(inputPosition, mockBoard);
    }

}

