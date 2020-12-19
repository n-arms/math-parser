package com.github.narms.mathparser;

import java.util.ArrayList;
public class Test{
    public static void main(String[] args){
        Lexer l = new Lexer("10/3");
        ArrayList<Syntax> struct = l.lex();
    
        System.out.println(Parser.parseTerm(Parser.parseFactor(Parser.parseLiteral(struct))).get(0));
        System.out.println(((ExpressionSyntax)Parser.parseTerm(struct).get(0)).eval());
    }
}