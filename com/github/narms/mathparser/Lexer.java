package com.github.narms.mathparser;

import java.util.ArrayList;

public class Lexer {
    private ArrayList<Token> tokens;
    private char[] input;
    public Lexer(String input){
        this.input = input.toCharArray();
        this.tokens = new ArrayList<Token>();
        this.tokens.add(new Token());
    }

    public ArrayList<Token> lex(){
        for (char c: this.input){
            if (this.tokens.get(this.tokens.size()-1).matchType(c)){
                this.tokens.get(this.tokens.size()-1).addChar(c);
                this.tokens.get(this.tokens.size()-1).setType();
            }
            else{
                this.tokens.add(new Token());
                this.tokens.get(this.tokens.size()-1).addChar(c);
                this.tokens.get(this.tokens.size()-1).setType();
            }
        }
        return this.tokens;
    }
}
