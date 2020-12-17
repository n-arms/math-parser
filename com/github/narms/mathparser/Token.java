package com.github.narms.mathparser;

enum TokenType{
    NUM, SYM, OP;
}

public class Token {
    private TokenType type;
    private String value;
    private static char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'};
    private static char[] operators = {'+', '-'};
    private static char[] symbol = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};

    public Token(){
        this.value = "";
    }

    public void addChar(char c){
        this.value+=c;
    }

    public TokenType getType(char c){
        for (char i: Token.numbers){
            if (i==c){
                return TokenType.NUM;
            }
        }
        for (char i: Token.operators){
            if (i==c){
                return TokenType.OP;
            }
        }
        for (char i: Token.symbol){
            if (i==c){
                return TokenType.SYM;
            }
        }
        throw new IllegalArgumentException();
    }

    public void setType(){
        this.type = getType(this.value.charAt(0));
    }

    public TokenType getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }

    public boolean matchType(char c){
        //try{
            if (this.type == TokenType.OP){
                return false;
            }
            if (this.type == null){
                return true;
            }
            return getType(c).equals(this.type);
            
        //}catch(NullPointerException e){
            //return true;
        //}
        
        
        
    }
}
