package com.github.narms.mathparser.exceptions;

public class IllegalSyntaxException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public IllegalSyntaxException(String errorMessage){
        super(errorMessage);
    }
}
