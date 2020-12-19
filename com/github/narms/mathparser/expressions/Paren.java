package com.github.narms.mathparser.expressions;

import java.util.ArrayList;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.Parser;
import com.github.narms.mathparser.Syntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.Token;

public class Paren extends Syntax {
    private ArrayList<Syntax> rawContents;
    public Paren(){
        this.rawContents = new ArrayList<Syntax>();
    }

    public void addSyntax(Syntax s){
        this.rawContents.add(s);
    }

    public ExpressionSyntax parse(){
        return (ExpressionSyntax)Parser.parseText(rawContents);
    }

    @Override
    public String toString() {
        String output = "";
        for (Syntax s: this.rawContents){
            output += ((Token)s).toString();
        }
        return output;
    }

    public void printContents() {
        for (Syntax s: this.rawContents){
            System.out.println(((Token)s).toString());
        }
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.PARENCONTAINER;
    }
    
}
