package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.Token;

public class Const extends ExpressionSyntax {
    private double value;
    public Const(Token t){
        this.value = Double.parseDouble(t.getValue());
    }

    @Override
    public double eval() {
        return this.value;
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
        return String.valueOf(this.value);
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.CONSTEXPR;
    }
    
}
