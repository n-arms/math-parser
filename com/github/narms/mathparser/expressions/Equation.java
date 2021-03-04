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

        
        if (! rightSide.hasVar(target)){
            ExpressionSyntax temp = leftSide;
            leftSide = rightSide;
            rightSide = temp;
        }

        if (rightSide instanceof PolyOp){
            System.out.println("polyop reducing "+this);
            switch (((PolyOp)rightSide).getOperator()){
                case "+":
                leftSide = new PolyOp(leftSide, "+");
                for (int i = ((PolyOp)rightSide).getTerms().size()-1; i>=0; i--){
                    if (((PolyOp)rightSide).getTerms().get(i).hasVar(target)){
                        ((PolyOp)leftSide).addValue(new UnaryOp("-", ((PolyOp)rightSide).getTerms().get(i)));
                        ((PolyOp)rightSide).getTerms().remove(i);
                    }
                }
                return false;
                case "*":
                leftSide = new PolyOp(leftSide, "*");
                for (int i = ((PolyOp)rightSide).getTerms().size()-1; i>=0; i--){
                    if (((PolyOp)rightSide).getTerms().get(i).hasVar(target)){
                        ((PolyOp)leftSide).addValue(new UnaryOp("1/", ((PolyOp)rightSide).getTerms().get(i)));
                        ((PolyOp)rightSide).getTerms().remove(i);
                    }
                }
                return false;
                default:
                throw new IllegalArgumentException("Illegal operator on polyop rightside");
            }
        }else if (rightSide instanceof UnaryOp){
            leftSide = new UnaryOp(((UnaryOp)rightSide).getOperator(), leftSide);
            rightSide = ((UnaryOp)rightSide).getContents();
            return true;
        }else if (rightSide instanceof Var){
            return false;
        }else if (rightSide instanceof BinOp){
            throw new IllegalArgumentException("n-arms you havent implemented this you idiot");
        }else if (rightSide instanceof Const){
            return false;
        }else{
            throw new IllegalArgumentException("illegal type called "+rightSide.getClass());
        }
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
