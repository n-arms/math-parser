package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;

public class UnaryOp extends ExpressionSyntax {
    private ExpressionSyntax contents;
    private String operator;

    public UnaryOp(String operator, ExpressionSyntax contents){
        this.contents = contents;
        this.operator = operator;
    }

    @Override
    public double eval() {
        if (this.operator.equals("-")){
            return this.contents.eval()*-1;
        }
        return this.contents.eval();
    }

    @Override
    public boolean hasVar(String name) {
        return false;
    }

    @Override
    public boolean defVar(String name, double value) {
        return false;
    }

    @Override
    public String toString() {
        return "("+this.operator+this.contents.toString()+")";
    }

    @Override
    public ExpressionSyntax derivative(String variable) {
        if (this.operator.equals("-")){
            return new BinOp("*", this.contents.derivative(variable), new Const(-1));
        }
        return this.contents.derivative(variable);
    }

    @Override
    public ExpressionSyntax reduce() {
        return this;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.UNARYOPEXPR;
    }
    
}
