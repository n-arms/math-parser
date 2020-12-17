package com.github.narms.mathparser;

public class Test{
    public static void main(String[] args){
        Lexer l = new Lexer("0.3+1.14-903");
        for (Token t: l.lex()){
            System.out.println(t.getValue()+": "+t.getType());
        }
    }
}