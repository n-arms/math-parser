package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("x+(1+2+x)");
        ArrayList<Syntax> struct = l.lex();
        System.out.println(struct);
        ExpressionSyntax e = (ExpressionSyntax) Parser.parseText(struct);
        e.defVar("x", 3);
        System.out.println(e);
        System.out.println(e.approximate());
        System.out.println(e.reduce());
    }
}