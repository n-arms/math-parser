package com.github.narms.mathparser.expressions;

import java.util.ArrayList;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.Parser;
import com.github.narms.mathparser.Syntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.Token;
import com.github.narms.mathparser.exceptions.IllegalSyntaxException;

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
        String output = "(";
        for (Syntax s: this.rawContents){
            output += ((Token)s).toString();
        }
        return output+")";
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

    public boolean isFull(){
        if (this.rawContents.size()==0)
            return false;
        int parentheses = 1;
        for (Syntax s: rawContents){
            if (s instanceof Token){
                if (s.getType().equals(SyntaxType.LPARTOKEN)){
                    parentheses++;
                }
                else if (s.getType().equals(SyntaxType.RPARTOKEN)){
                    parentheses--;
                }
            }
        }
        return parentheses==0;
    }

	public void trimTrailingParentheses() {
        if (((Token)this.rawContents.get(this.rawContents.size()-1)).getValue().equals(")"))
            this.rawContents.remove(this.rawContents.size()-1);
        else
            throw new IllegalSyntaxException("Illegal closing char on "+this.toString());
	}
}
