package com.github.narms.mathparser;

enum TokenType{
    NUM, SYM, OP;
}

public class Token {
    private TokenType type;
    private String value;

    public Token(){
        this.value = "";
    }

    public void addChar(char c){
        this.value+=c;
    }

    public void setType(){
        try{
            Double.parseDouble(this.value);
            this.type = TokenType.NUM;
        }finally{
            switch(this.value.charAt(0)){
                case '+':
                this.type = TokenType.OP;
                break;
                case '-':
                this.type = TokenType.OP;
                break;
                default:
                this.type = TokenType.SYM;
                break;
            }
        }
    }

    public TokenType getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }
}
