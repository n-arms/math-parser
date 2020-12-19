package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.BinOp;
import com.github.narms.mathparser.expressions.Const;
import com.github.narms.mathparser.expressions.Paren;
import com.github.narms.mathparser.exceptions.ParserException;

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
    public static ArrayList<Syntax> parseParentheses(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = new ArrayList<Syntax>();
        int i = 0;
        while (i<structure.size()){
            if (structure.get(i).getType().equals(SyntaxType.LPARTOKEN)){
                output.add(new Paren());
                i++;
                while (!((Paren)output.get(output.size()-1)).isFull()){
                    ((Paren) output.get(output.size() - 1)).addSyntax(structure.get(i));
                    i++;
                }
                ((Paren)output.get(output.size()-1)).trimTrailingParentheses();
                output.set(output.size() - 1, ((Paren)output.get(output.size() - 1)).parse());
            }
            else{
                output.add(structure.get(i));
                i++;
            }
        }
        return output;
    }
    public static Syntax parseText(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        output = Parser.parseParentheses(output);
        output = Parser.parseLiteral(output);
        output = Parser.parseFactor(output);
        output = Parser.parseTerm(output);

        if (output.size() != 1){
            System.out.println(output);
            throw new ParserException("Parser couldn't reduce a 1 root tree");
        }
        return output.get(0);
    }
}
