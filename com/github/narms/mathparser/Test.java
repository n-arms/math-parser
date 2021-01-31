package com.github.narms.mathparser;

import com.github.narms.mathparser.output.Num;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("x*3"));
        System.out.println(mySyntax);
        mySyntax = mySyntax.derivative("x");
        System.out.println(mySyntax);
        mySyntax = mySyntax.reduce();
        System.out.println(mySyntax);
    }
}