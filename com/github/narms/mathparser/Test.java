package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("2*PI");
        ArrayList<Syntax> struct = l.lex();
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(struct);
        System.out.println(e.defVar("Pi", 3.1415));
        System.out.println(e);
        System.out.println(e.eval());
        System.out.println(e.defVar("Pi", 6.283));
        System.out.println(e.eval());
        System.out.println(e.hasVar("phi"));
    }
}