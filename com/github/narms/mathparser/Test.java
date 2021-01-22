package com.github.narms.mathparser;

import com.github.narms.mathparser.output.Num;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("(-4)^0.5*5*(-1)^0.5"));
        System.out.println(mySyntax);
        System.out.println(mySyntax.reduce());
    }
}