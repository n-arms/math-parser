package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.BinOp;
import com.github.narms.mathparser.expressions.Const;

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
    public static ArrayList<Syntax> parseLiteral(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        for (Syntax s: structure){
            if (s instanceof Token){
                if (((Token)s).getType().equals(SyntaxType.NUMTOKEN)){
                    output.set(structure.indexOf(s), new Const((Token)output.get(structure.indexOf(s))));
                }
            }
        }
        return output;
    }
    public static ArrayList<Syntax> parseFactor(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        int i = 0;
        while (i<output.size()){
            if (output.get(i) instanceof Token){
                if (((Token)output.get(i)).getValue().equals("*") || ((Token)output.get(i)).getValue().equals("/")){
                    output.set(i, new BinOp(((Token)output.get(i)).getValue(), 
                    (ExpressionSyntax)output.get(i-1), 
                    (ExpressionSyntax)output.get(i+1)));
                    output.remove(i+1);
                    output.remove(i-1);
                }else{i++;}
            }else{i++;}
        }
        return output;

    }
}
