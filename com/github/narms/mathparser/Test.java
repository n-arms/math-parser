package com.github.narms.mathparser;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax)Parser.parseText(Lexer.lex("(x+y)(z+w)(a+b)"));
        System.out.println(mySyntax);
        mySyntax = mySyntax.normalize();
        System.out.println(mySyntax);
    }
}