package com.github.narms.mathparser;

import java.util.ArrayList;

public class Lexer {
    private ArrayList<Syntax> tokens;
    private char[] input;


    public Lexer(String input){
        this.input = input.toCharArray();
        this.tokens = new ArrayList<Syntax>();
        this.tokens.add(new Token());
    }

    public ArrayList<Syntax> lex(){
        for (char c: this.input){
            if (((Token)this.tokens.get(this.tokens.size()-1)).matchType(c)){
                ((Token)this.tokens.get(this.tokens.size()-1)).addChar(c);
                ((Token)this.tokens.get(this.tokens.size()-1)).setType();
            }
            else{
                this.tokens.add(new Token());
                ((Token)this.tokens.get(this.tokens.size()-1)).addChar(c);
                ((Token)this.tokens.get(this.tokens.size()-1)).setType();
            }
        }
        return this.tokens;
    }
}
