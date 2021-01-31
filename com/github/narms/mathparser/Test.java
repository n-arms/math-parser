package com.github.narms.mathparser;

import com.github.narms.mathparser.output.Num;

public class Test {
    public static void main(String[] args){
        ExpressionSyntax mySyntax = (ExpressionSyntax) Parser.parseText(Lexer.lex("a + b * 5"));
        System.out.println(mySyntax);
        mySyntax.defVar("a", new Num(17));
        System.out.println(mySyntax);
        mySyntax.defVar("b", new Num(42));
        System.out.println(mySyntax.approximate());
    }
}