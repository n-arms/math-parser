package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("-1*2");
        ArrayList<Syntax> struct = l.lex();
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(struct);
        System.out.println(e);
        System.out.println(e.eval());
    }
}