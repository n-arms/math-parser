package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        //Approximating sin(x) with a taylor series
        Lexer l = new Lexer("(x)-((x*x*x)/6)+((x*x*x*x*x)/120)-((x*x*x*x*x*x*x)/5040)+((x*x*x*x*x*x*x*x*x)/362880)");
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(l.lex());
        Graph g = new Graph(e, 300, 300, 0.05);
        g.graphExpression();
    }
}