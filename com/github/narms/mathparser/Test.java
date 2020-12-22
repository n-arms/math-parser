package com.github.narms.mathparser;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        //Approximating sin(x) with a taylor series
        /*Lexer l = new Lexer("(x)-((x*x*x)/6)+((x*x*x*x*x)/120)-((x*x*x*x*x*x*x)/5040)+((x*x*x*x*x*x*x*x*x)/362880)");
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(l.lex());
        Graph g = new Graph(e, 300, 300, 0.05);
        g.graphExpression();*/
        Lexer l = new Lexer("2^(-1)");
        ArrayList<Syntax> struct = l.lex();
        System.out.println(struct);
        ExpressionSyntax e = (ExpressionSyntax) Parser.parseText(struct);
        System.out.println(e);
        System.out.println(e.reduce());
    }
}