package com.github.narms.mathparser;

import com.github.narms.mathparser.exceptions.LexerException;

enum TokenType{
    NUM, SYM, OP;
}

public class Token extends Syntax{
    private SyntaxType type;
    private String value;
    private static char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'};
    private static char[] operators = {'+', '-', '/', '*'};
    private static char lpar = '(';
    private static char rpar = ')';
    private static char[] symbol = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 
    's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public Token(){
        this.value = "";
    }

    public void addChar(char c){
        this.value+=c;
    }
    
    public SyntaxType getType(char c){
        for (char i: Token.numbers){
            if (i==c){
                return SyntaxType.NUMTOKEN;
            }
        }
        for (char i: Token.operators){
            if (i==c){
                return SyntaxType.OPTOKEN;
            }
        }
        for (char i: Token.symbol){
            if (i==c){
                return SyntaxType.SYMTOKEN;
            }
        }
        if (c==Token.lpar){
            return SyntaxType.LPARTOKEN;
        }else if (c==Token.rpar){
            return SyntaxType.RPARTOKEN;
        }
        throw new LexerException("Illegal char: "+c);
    }

    public void setType(){
        this.type = getType(this.value.charAt(0));
    }
    @Override
    public SyntaxType getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }

    public boolean matchType(char c){
        if (this.type == SyntaxType.OPTOKEN || this.type==SyntaxType.LPARTOKEN || this.type==SyntaxType.RPARTOKEN){
            return false;
        }
        if (this.type == null){
            return true;
        }
        return getType(c).equals(this.type);

        
        
        
    }

    public String toString(){
        return "<"+this.type+", "+this.value+">";
    }
}
