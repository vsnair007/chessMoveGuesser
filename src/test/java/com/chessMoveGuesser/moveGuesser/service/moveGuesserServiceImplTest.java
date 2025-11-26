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
}

