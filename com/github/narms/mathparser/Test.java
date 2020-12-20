package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.BinOp;
import com.github.narms.mathparser.expressions.Const;
import com.github.narms.mathparser.expressions.Var;

public class Test {
    public static void main(String[] args){
        Lexer l = new Lexer("(1+x)*(2+x)");
        ArrayList<Syntax> struct = l.lex();
        ExpressionSyntax e = (ExpressionSyntax)Parser.parseText(struct);
        System.out.println(e);
        e = e.reduce();
        System.out.println(e);
        /*e = e.derivative("x");
        System.out.println(e);
        e = e.reduce();
        System.out.println(e+"\n");*/
        /*BinOp b = new BinOp("*", new BinOp("+", new Const(5), new Var("x")), new BinOp("+", new Const(2), new Var("x")));
        BinOp b2 = new BinOp("*", new BinOp("+", new Const(5), new Var("x")), new Const(3));
        BinOp b3 = new BinOp("*", new Const(5), new BinOp("+", new Var("x"), new Const(3)));
        BinOp b4 = new BinOp("*", new BinOp("+", new Const(0), new Const(0)), new BinOp("+", new Var("x"), new Const(3)));
        System.out.println(b);
        System.out.println(b.reduce()+"\n");
        System.out.println(b2);
        System.out.println(b2.reduce()+"\n");
        System.out.println(b3);
        System.out.println(b3.reduce()+"\n");
        System.out.println(b4);
        System.out.println(b4.reduce());*/
    }
}