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
        while (canReduce && !(rightSide instanceof Var && rightSide.toString().equals(target))){
            canReduce = reduce(target);
            leftSide = leftSide.reduce();
            rightSide = rightSide.reduce();
        }
    }

    private boolean reduce(String target){
        boolean rightDone = purifyRight(target);
        boolean leftDone = purifyLeft(target);
        return  leftDone || rightDone;
    }

    private boolean purifyLeft(String target){
        leftSide = leftSide.reduce();
        leftSide = leftSide.normalize();

        if (leftSide instanceof PolyOp){
            System.out.println("polyop on purify left");
            String inverseOp;
            if (((PolyOp)leftSide).getOperator().equals("+")){
                inverseOp = "-";
            }else if (((PolyOp)leftSide).getOperator().equals("*")){
                inverseOp = "1/";
            }else{
                throw new IllegalArgumentException("Illegal argument on polyop leftside");
            }

            rightSide = new PolyOp(rightSide, ((PolyOp)leftSide).getOperator());
            for (int i = ((PolyOp)leftSide).getTerms().size()-1; i>=0; i--){
                if (! ((PolyOp)leftSide).getTerms().get(i).hasVar(target)){
                    ((PolyOp)rightSide).addValue(new UnaryOp(inverseOp, ((PolyOp)leftSide).getTerms().get(i)));
                    ((PolyOp)leftSide).getTerms().remove(i);
                }
            }
            return true;
        }else if (leftSide instanceof UnaryOp){
            System.out.println("unaryop on purify left");
            rightSide = new UnaryOp(((UnaryOp)leftSide).getOperator(), rightSide);
            leftSide = ((UnaryOp)leftSide).getContents();
            return true;
        }else if (leftSide instanceof Const){
            System.out.println("const on purify left");
            return false;
        }else if (leftSide instanceof Var){
            System.out.println("var on purify left");
            return false;
        }else if (leftSide instanceof BinOp){
            System.out.println("binop on purify left");
            throw new IllegalArgumentException("n-arms, you havent implemented this yet");
        }else{
            throw new IllegalArgumentException("Illegal type on leftside"+leftSide);
        }
    }

    private boolean purifyRight(String target){
        rightSide = rightSide.reduce();
        rightSide = rightSide.normalize();

        if (rightSide.hasVar(target)){
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
