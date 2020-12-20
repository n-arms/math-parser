package com.github.narms.mathparser.exceptions;

public class UndefinedVariableException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UndefinedVariableException(String errorMessage){
        super(errorMessage);
    }
    
}
