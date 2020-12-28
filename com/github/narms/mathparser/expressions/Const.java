package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.Token;

public class Const extends LiteralSyntax {
    private double value;
    public Const(Token t){
        this.value = Double.parseDouble(t.getValue());
    }
    public Const(double d){
        this.value = d;
    }

    @Override
    public ExpressionSyntax derivative(String name){
        return new Const(0);
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
        return String.valueOf(this.value);
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.CONSTEXPR;
    }

    @Override
    public ExpressionSyntax reduce(){
        return this;
    }

    @Override
    public EvalType evaluatable(){
        return EvalType.NUM;
    }

    @Override
    public Object getValue(){
        return this.value;
    }

    @Override
    public Object approximate(){
        return this.value;
    }
}
