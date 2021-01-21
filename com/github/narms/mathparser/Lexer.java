package com.github.narms.mathparser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static ArrayList<Syntax> lex(String input){
        ArrayList<Syntax> tokens = new ArrayList<Syntax>();
        tokens.add(new Token());
        for (char c: input.toCharArray()){
            if (c!=' '){
                if (((Token)tokens.get(tokens.size()-1)).matchType(c)){
                    ((Token)tokens.get(tokens.size()-1)).addChar(c);
                    ((Token)tokens.get(tokens.size()-1)).setType();
                }
                else{
                    tokens.add(new Token());
                    ((Token)tokens.get(tokens.size()-1)).addChar(c);
                    ((Token)tokens.get(tokens.size()-1)).setType();
                }
            }
        }
        return tokens;
    }
}
