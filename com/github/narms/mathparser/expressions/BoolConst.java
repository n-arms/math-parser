package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;

public class BoolConst extends LiteralSyntax {
    private boolean value;

    public BoolConst(boolean b){
        this.value = b;
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
    public ExpressionSyntax derivative(String variable) {
        return new Const(0);
    }

    @Override
    public ExpressionSyntax reduce() {
        return this;
    }

    @Override
    public EvalType evaluatable() {
        return EvalType.BOOL;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.BOOLEXPR;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
    
}
