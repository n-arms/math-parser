package com.github.narms.mathparser;

import java.util.ArrayList;
import com.github.narms.mathparser.expressions.*;


public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("3/4");
        ArrayList<Syntax> struct = l.lex();
        System.out.println(struct);
        Syntax s = Parser.parseText(struct);
        System.out.println(s);
        System.out.println(((ExpressionSyntax)s).reduce());
    }
}