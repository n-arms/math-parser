package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("4(x)*5/6"));
        System.out.println(mySyntax);
        mySyntax = mySyntax.reduce();
        System.out.println(mySyntax);
    }
}