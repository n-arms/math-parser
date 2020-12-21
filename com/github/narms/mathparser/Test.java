package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("isOne&isTwo");
        ArrayList<Syntax> struct = l.lex();
        System.out.println(struct);
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(struct);
        System.out.println(e);
        e.defVar("isOne", true);
        e.defVar("isTwo", true);
        e = e.reduce();
        System.out.println(e);
    }
}