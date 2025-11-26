package com.chessMoveGuesser.moveGuesser.exception;

public class PostionOutOfBoardException extends RuntimeException{
    PostionOutOfBoardException(){
        super();
    }
    public PostionOutOfBoardException(String message){
        super(message);
    }
}
