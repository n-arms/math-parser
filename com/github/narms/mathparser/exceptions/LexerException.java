package com.github.narms.mathparser.exceptions;

public class LexerException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public LexerException(String errorMessage) {
        super(errorMessage);
    }
}
