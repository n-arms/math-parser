package com.github.narms.mathparser;

import java.util.ArrayList;

import com.github.narms.mathparser.expressions.*;
import com.github.narms.mathparser.output.Bool;
import com.github.narms.mathparser.exceptions.ParserException;

public class Parser {
    public static Syntax parseText(ArrayList<Syntax> structure){
        structure = parseParentheses(structure);
        structure = parseLiteral(structure);
        structure = parsePower(structure);
        structure = parseNegation(structure);
        structure = parseFactor(structure);
        structure = parseAddition(structure);
        structure = parseLogic(structure);
        if (structure.size()!=1){
            throw new ParserException("Parser could not reduce "+ structure + " to a 1 root tree");
        }
        return structure.get(0);
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

    public static ArrayList<Syntax> parseLiteral(ArrayList<Syntax> structure){
        ArrayList<Syntax> output = new ArrayList<Syntax>();
        for (int i = 0; i<structure.size(); i++){
            if (structure.get(i) instanceof Token){
                switch (((Token)structure.get(i)).getType()){
                    case NUMTOKEN:
                    output.add(new Const(Double.parseDouble(((Token)structure.get(i)).getValue())));
                    break;
                    case SYMTOKEN:
                    output.add(new Var(((Token)structure.get(i)).getValue()));
                    break;
                    case KEYVARTOKEN:
                    output.add(new Const(new Bool(Boolean.parseBoolean(((Token)structure.get(i)).getValue()))));
                    break;
                    default:
                    output.add(structure.get(i));
                }
            }else {output.add(structure.get(i));}
        }
        return output;
    }
    public static ArrayList<Syntax> parsePower(ArrayList<Syntax> structure){
        int i = 1;
        while (i<structure.size()-1){
            if (structure.get(i) instanceof Token && !(structure.get(i-1) instanceof Token) && !(structure.get(i+1) instanceof Token) && ((Token)structure.get(i)).getValue().equals("^")){
                structure.set(i, new BinOp("^", (ExpressionSyntax)structure.get(i-1), (ExpressionSyntax)structure.get(i+1)));
                structure.remove(i+1);
                structure.remove(i-1);
            }else {i++;}
        }
        return structure;
    }

    public static ArrayList<Syntax> parseFactor(ArrayList<Syntax> structure){
        int i = 1;
        while(i<structure.size()-1){
            if (match(structure.get(i), "*") || match(structure.get(i), "/")){
                
                if (match(structure.get(i), "*")){
                    structure.set(i, new PolyOp((ExpressionSyntax)structure.get(i-1), "*"));
                    ((PolyOp)structure.get(i)).addValue((ExpressionSyntax)structure.get(i+1));
                }else{
                    structure.set(i, new PolyOp((ExpressionSyntax)structure.get(i-1), "*"));
                    ((PolyOp)structure.get(i)).addValue(new UnaryOp("1/", (ExpressionSyntax)structure.get(i+1)));
                }
                structure.remove(i+1);
                structure.remove(i-1);
                int polyindex = i-1;
                while (i<structure.size()-1 && (match(structure.get(i), "*") || match(structure.get(i), "/"))){
                    if (match(structure.get(i), "*")){
                        ((PolyOp)structure.get(polyindex)).addValue((ExpressionSyntax)structure.get(i+1));
                    }else{
                        ((PolyOp)structure.get(polyindex)).addValue(new UnaryOp("1/", (ExpressionSyntax)structure.get(i+1)));
                    }
                    structure.remove(i+1);
                    structure.remove(i);
                }

            }else{i++; }
        }
        return structure;
    }

    public static ArrayList<Syntax> parseNegation(ArrayList<Syntax> structure){
        int i = 1;
        if (structure.get(0) instanceof Token && ((Token)structure.get(0)).getValue().equals("-") && structure.get(1) instanceof ExpressionSyntax){
            structure.set(0, new UnaryOp("-", (ExpressionSyntax)structure.get(1)));
            structure.remove(1);
        }
        while (i<structure.size()-1){
            if (structure.get(i) instanceof Token && structure.get(i+1) instanceof ExpressionSyntax && ((Token)structure.get(i)).getValue().equals("-") && structure.get(i-1) instanceof Token){
                structure.set(i, new UnaryOp("-", (ExpressionSyntax)structure.get(i+1)));
                structure.remove(i+1);
            }else {i++;}
        }
        return structure;
    }

    public static ArrayList<Syntax> parseAddition(ArrayList<Syntax> structure){
        int i = 1;
        while (i<structure.size()-1){
            if (structure.get(i) instanceof Token && (((Token)structure.get(i)).getValue().equals("+") || ((Token)structure.get(i)).getValue().equals("-")) && (structure.get(i-1) instanceof ExpressionSyntax) && (structure.get(i+1) instanceof ExpressionSyntax)){
                if (match(structure.get(i), "+")){
                    structure.set(i, new PolyOp((ExpressionSyntax)structure.get(i-1), "+"));
                    ((PolyOp)structure.get(i)).addValue((ExpressionSyntax)structure.get(i+1));
                }else{
                    structure.set(i, new PolyOp((ExpressionSyntax)structure.get(i-1), "+"));
                    ((PolyOp)structure.get(i)).addValue(new UnaryOp("-", (ExpressionSyntax)structure.get(i+1)));
                }
                structure.remove(i+1);
                structure.remove(i-1);
                int polyindex = i-1;
                while (i<structure.size()-1 && (match(structure.get(i), "+") || match(structure.get(i), "-"))){
                    if (match(structure.get(i), "+")){
                        ((PolyOp)structure.get(polyindex)).addValue((ExpressionSyntax)structure.get(i+1));
                    }else{
                        ((PolyOp)structure.get(polyindex)).addValue(new UnaryOp("-", (ExpressionSyntax)structure.get(i+1)));
                    }
                    structure.remove(i+1);
                    structure.remove(i);
                }
            }else{i++; }
        }
        return structure;
    }
    public static ArrayList<Syntax> parseLogic(ArrayList<Syntax> structure){
        if (structure.get(0) instanceof Token && structure.get(1) instanceof ExpressionSyntax && ((Token)structure.get(0)).getValue().equals("~")){
            structure.set(0, new UnaryOp("~", (ExpressionSyntax)structure.get(1)));
            structure.remove(1);
        }

        int i = 1;
        while (i<structure.size()-1){
            if (structure.get(i) instanceof Token && (structure.get(i-1) instanceof ExpressionSyntax) && (structure.get(i+1) instanceof ExpressionSyntax)){
                switch (((Token)structure.get(i)).getValue()){
                    case ">":
                    structure.set(i, new BinOp(">", (ExpressionSyntax)structure.get(i-1), (ExpressionSyntax)structure.get(i+1)));
                    structure.remove(i+1);
                    structure.remove(i-1);
                    break;
                    case "<":
                    structure.set(i, new BinOp("<", (ExpressionSyntax)structure.get(i-1), (ExpressionSyntax)structure.get(i+1)));
                    structure.remove(i+1);
                    structure.remove(i-1);
                    break;
                    case "|":
                    structure.set(i, new BinOp("|", (ExpressionSyntax)structure.get(i-1), (ExpressionSyntax)structure.get(i+1)));
                    structure.remove(i+1);
                    structure.remove(i-1);
                    break;
                    case "&":
                    structure.set(i, new BinOp("&", (ExpressionSyntax)structure.get(i-1), (ExpressionSyntax)structure.get(i+1)));
                    structure.remove(i+1);
                    structure.remove(i-1);
                    break;
                    case "~":
                    structure.set(i, new UnaryOp("~", (ExpressionSyntax)structure.get(i+1)));
                    structure.remove(i+1);
                    break;
                    default:
                    i++;
                }
            }else {i++;}
        }
        return structure;
    }

    private static boolean match(Syntax value, String target){
        return value instanceof Token && ((Token)value).getValue().equals(target);
    }
}
