package com.github.narms.mathparser.expressions;

import java.util.List;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.Syntax;
import com.github.narms.mathparser.SyntaxType;

public class Equation extends Syntax {
    private ExpressionSyntax leftSide;
    private ExpressionSyntax rightSide;

    public Equation(ExpressionSyntax left, ExpressionSyntax right) {
        leftSide = left;
        rightSide = right;
    }

    public void solve(String target) {
        boolean canReduce = true;
        while (canReduce && !(leftSide instanceof Var && leftSide.toString().equals(target))){
            canReduce = reduce(target);
            leftSide = leftSide.reduce();
            rightSide = rightSide.reduce();
        }
    }

    private boolean reduce(String target){
        rightSide = rightSide.reduce();
        rightSide = rightSide.normalize();
        if (rightSide.hasVar(target)){
            if (rightSide instanceof PolyOp){
                for (ExpressionSyntax es: ((PolyOp)rightSide).getTerms()){
                    List<ExpressionSyntax> terms;
                    if (!es.hasVar(target)){
                        switch (((PolyOp)rightSide).getOperator()){
                            case "+":
                            leftSide = new PolyOp("+", leftSide, new UnaryOp("-", es));
                            terms = ((PolyOp)rightSide).getTerms();
                            terms.remove(es);
                            rightSide = new PolyOp(terms, "+");
                            break;
                            case "*":
                            leftSide = new PolyOp("*", leftSide, new UnaryOp("1/", es));
                            terms = ((PolyOp)rightSide).getTerms();
                            terms.remove(es);
                            rightSide = new PolyOp(terms, "*");
                            break;
                            default:
                            return false;
                        }
                    }
                }
                return true;
            }else if (rightSide instanceof BinOp){
                if (((BinOp)rightSide).getValue1().hasVar(target)){
                    if (((BinOp)rightSide).getValue2().hasVar(target)){
                        return false;
                    }else{
                        leftSide = new BinOp("^", leftSide, new UnaryOp("1/", ((BinOp)rightSide).getValue2()));
                        return true;
                    }
                }else{
                    return false;
                }
            }else if (rightSide instanceof UnaryOp){
                switch (((UnaryOp)rightSide).getOperator()){
                    case "1/":
                    leftSide = new PolyOp("*", leftSide, ((UnaryOp)rightSide).getContents());
                    rightSide = new Const(1);
                    return true;
                    case "-":
                    leftSide = new PolyOp("+", leftSide, ((UnaryOp)rightSide).getContents());
                    rightSide = new Const(0);
                    return true;
                    default:
                    return false;
                }
            }else if (rightSide instanceof Var){
                return false;
            }
        }
        return false;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.EQUATION;
    }

    @Override
    public String toString(){
        return leftSide.toString()+" = "+rightSide.toString();
    }
}
