package com.github.narms.mathparser;
import java.util.Scanner;

import com.github.narms.mathparser.expressions.Equation;

public class Test {
    public static void main(String[] args){
        /*
        ExpressionSyntax mySyntax;
        Scanner myScanner = new Scanner(System.in);
        System.out.print("> ");
        while (!myScanner.hasNext("quit")){
            mySyntax = (ExpressionSyntax)Parser.parseText(Lexer.lex(myScanner.nextLine()));
            mySyntax = mySyntax.reduce();
            System.out.print("Distributes to:\n"+mySyntax.normalize()+"\n\n> ");
        }
        myScanner.close();*/

        ExpressionSyntax l = (ExpressionSyntax)(Parser.parseText(Lexer.lex("x*2")));
        ExpressionSyntax r = (ExpressionSyntax)(Parser.parseText(Lexer.lex("5")));
        Equation e = new Equation(l, r);
        System.out.println(e);
        e.solve("x");
        System.out.println(e);
    }
}