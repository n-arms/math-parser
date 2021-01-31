package com.github.narms.mathparser.exceptions;

public class IllegalTypeException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IllegalTypeException(String errorMessage){
        super(errorMessage);
    }
    
}
