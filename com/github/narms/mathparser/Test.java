package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("x^2+3x+4"));
        System.out.println(mySyntax);
        mySyntax = mySyntax.normalize();
        System.out.println(mySyntax);
        System.out.println(mySyntax.degree());
    }
}