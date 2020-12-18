package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.BinOp;

public class Parser {
    public static ArrayList<Syntax> parseTerm(ArrayList<Syntax> structure){
        int i = 0;
        ArrayList<Syntax> output = structure;
        while (i<output.size()){
            if (output.get(i) instanceof Token){
                if (((Token) output.get(i)).getValue().equals("+") || ((Token) output.get(i)).getValue().equals("-")){
                    output.set(i, new BinOp(((Token)output.get(i)).getValue(), 
                    ((ExpressionSyntax)output.get(i-1)), 
                    ((ExpressionSyntax)output.get(i+1))));
                    output.remove(i+1);
                    output.remove(i-1);

                }
                else{i++;}
            }else{i++;}
        }
        return output;
    }
}
