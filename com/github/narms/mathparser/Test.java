package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.*;

public class Test{
    public static void main(String[] args){
        ArrayList<Syntax> struct = new ArrayList<Syntax>();
        Token t = new Token();
        t.addChar('+');
        t.setType();
        struct.add(new Const(5));
        struct.add(t);
        struct.add(new Const(3));
        Token t2 = new Token();
        t2.addChar('-');
        t2.setType();
        struct.add(t2);
        struct.add(new Const(2));
        System.out.println(Parser.parseTerm(struct).get(0));
        System.out.println(((ExpressionSyntax)Parser.parseTerm(struct).get(0)).eval());
    }
}