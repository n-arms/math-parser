package com.github.narms.mathparser;

enum SyntaxType{
    NUMTOKEN, OPTOKEN, SYMTOKEN, BINOPEXPR, CONSTEXPR;
}

public abstract class Syntax {
    public abstract SyntaxType getType();
}
