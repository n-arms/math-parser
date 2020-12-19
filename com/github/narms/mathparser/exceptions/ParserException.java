package com.github.narms.mathparser.exceptions;

public class ParserException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ParserException(String errorMessage) {
        super(errorMessage);
    }
}
