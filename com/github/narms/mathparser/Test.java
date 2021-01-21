package com.github.narms.mathparser;

import com.github.narms.mathparser.output.Num;

public class Test {
    public static void main(String[] args){
        System.out.println(Lexer.lex("3+pi"));
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("3+a+5+pi"));
        mySyntax.defVar("pi", new Num(3.14159265358979323846264));
        System.out.println(mySyntax);
        System.out.println(mySyntax.reduce());
    }
}