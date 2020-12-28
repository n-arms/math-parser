package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.BinOp;
import com.github.narms.mathparser.expressions.BoolConst;
import com.github.narms.mathparser.expressions.Const;
import com.github.narms.mathparser.expressions.Paren;
import com.github.narms.mathparser.expressions.UnaryOp;
import com.github.narms.mathparser.expressions.Var;
import com.github.narms.mathparser.exceptions.ParserException;

public class Parser {
    public static ArrayList<Syntax> parseTerm(ArrayList<Syntax> structure){
        int i = 1;
        ArrayList<Syntax> output = structure;
        while (i<output.size()-1){
            if (output.get(i) instanceof Token && output.get(i-1) instanceof ExpressionSyntax && output.get(i+1) instanceof ExpressionSyntax){
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
                }else if (((Token)s).getType().equals(SyntaxType.SYMTOKEN)){
                    output.set(structure.indexOf(s), new Var(((Token)output.get(structure.indexOf(s))).getValue()));
                } 
                else if (((Token)s).getType().equals(SyntaxType.KEYVARTOKEN) && (((Token)s).getValue().equals("true") || ((Token)s).getValue().equals("false"))){
                    output.set(structure.indexOf(s), new BoolConst(Boolean.parseBoolean(((Token)output.get(structure.indexOf(s))).getValue())));
                } 
            }
        }
        return output;
    }
    public static ArrayList<Syntax> parseFactor(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        int i = 1;
        while (i<output.size()-1){
            if (output.get(i) instanceof Token && output.get(i-1) instanceof ExpressionSyntax && output.get(i+1) instanceof ExpressionSyntax){
                if (((Token)output.get(i)).getValue().equals("*") || ((Token)output.get(i)).getValue().equals("/")){
                    output.set(i, new BinOp(((Token)output.get(i)).getValue(), 
                    (ExpressionSyntax)output.get(i-1), 
                    (ExpressionSyntax)output.get(i+1)));
                    output.remove(i+1);
                    output.remove(i-1);
                }else{i++;}
            } else if (!(output.get(i) instanceof Token) && !(output.get(i-1) instanceof Token)){
                output.set(i, new BinOp("*", (ExpressionSyntax) output.get(i), (ExpressionSyntax)output.get(i-1)));
                output.remove(i-1);
            }else{i++;}
        }
        if (output.size()>1){
            i = output.size()-1;
            if (!(output.get(i) instanceof Token) && !(output.get(i-1) instanceof Token)){
                output.set(i, new BinOp("*", (ExpressionSyntax) output.get(i), (ExpressionSyntax) output.get(i-1)));
                output.remove(i-1);
            }
        }
        return output;
    }
    public static ArrayList<Syntax> parsePower(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        int i = 1;
        while (i<output.size()-1){
            if (output.get(i) instanceof Token && output.get(i+1) instanceof ExpressionSyntax && output.get(i-1) instanceof ExpressionSyntax){
                if (((Token)output.get(i)).getValue().equals("^")){
                    output.set(i, new BinOp("^", (ExpressionSyntax)output.get(i-1), (ExpressionSyntax)output.get(i+1)));
                    output.remove(i+1);
                    output.remove(i-1);
                }else{i++;}
            }else{i++;}
        }
        return output;
    }


    public static ArrayList<Syntax> parseComparison(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        int i = 1;
        while (i<output.size()-1){
            if (output.get(i) instanceof Token && output.get(i-1) instanceof ExpressionSyntax && output.get(i+1) instanceof ExpressionSyntax){
                if (((Token)output.get(i)).getValue().equals("|") || ((Token)output.get(i)).getValue().equals("&") || 
                ((Token)output.get(i)).getValue().equals(">") || ((Token)output.get(i)).getValue().equals("<")){
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

    public static ArrayList<Syntax> parseUnary(ArrayList<Syntax> structure){
        int i = structure.size()-1;
        ArrayList<Syntax> output = structure;
        while (i>0){
            if (i==1 && output.get(0) instanceof Token && output.get(1) instanceof ExpressionSyntax){
                if (((Token)output.get(0)).getValue().equals("+") || ((Token)output.get(0)).getValue().equals("-")){
                    output.set(0, new UnaryOp(((Token)output.get(0)).getValue(), (ExpressionSyntax)output.get(1)));
                    output.remove(1);
                    return output;
                } else {i=i-1;}
            } else if (output.get(i) instanceof ExpressionSyntax && output.get(i-1) instanceof Token && output.get(i-2) instanceof Token){
                output.set(i, new UnaryOp(((Token)output.get(i-1)).getValue(), (ExpressionSyntax)output.get(i)));
                output.remove(i-1);
                i = i-2;
            } else{i = i-1;}

        }
        return output;
    }

    public static Syntax parseText(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = structure;
        output = Parser.parseParentheses(output);
        output = Parser.parseLiteral(output);
        output = Parser.parsePower(output);
        output = Parser.parseUnary(output);
        output = Parser.parseFactor(output);
        output = Parser.parseTerm(output);
        output = Parser.parseComparison(output);
        if (output.size() != 1){
            System.out.println(output);
            throw new ParserException("Parser couldn't reduce a 1 root tree");
        }
        return output.get(0);
    }
}
