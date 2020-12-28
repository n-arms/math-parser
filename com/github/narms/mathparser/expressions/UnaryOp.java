package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
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
    public boolean hasVar(String name) {
        return false;
    }

    @Override
    public boolean defVar(String name, Object value) {
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
        if (this.operator.equals("-")){
            return (new BinOp("*", this.contents, new Const(-1))).reduce();
        }
        return this.contents.reduce();
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.UNARYOPEXPR;
    }

    @Override
    public EvalType evaluatable(){
        return this.contents.evaluatable();
    }

    @Override
    public Object approximate(){
        return this.reduce();
    }
}
