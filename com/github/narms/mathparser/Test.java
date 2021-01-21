package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("(1/3)+2+(1+4)");
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(l.lex());
        System.out.println(mySyntax);
        System.out.println(mySyntax.approximate());
    }
}