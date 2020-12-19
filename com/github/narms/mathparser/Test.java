package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("(1+2)*3");
        Syntax tree = Parser.parseText(l.lex());
        System.out.println(tree);
        System.out.println(((ExpressionSyntax)tree).eval());
    }
}