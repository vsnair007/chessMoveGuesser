package com.chessMoveGuesser.moveGuesser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PieceNotFoundException.class)
    public ResponseEntity<String> handlePieceNotFoundException(PieceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostionOutOfBoardException.class)
    public ResponseEntity<String> handlePostionOutOfBoardException(PostionOutOfBoardException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
