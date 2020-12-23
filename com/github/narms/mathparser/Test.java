package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("1+(1+pi)");
        ArrayList<Syntax> struct = l.lex();
        System.out.println(struct);
        ExpressionSyntax e = (ExpressionSyntax) Parser.parseText(struct);
        System.out.println(e);
        System.out.println(e.reduce());
    }
}