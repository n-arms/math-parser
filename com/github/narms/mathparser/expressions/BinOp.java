package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;

public class BinOp extends ExpressionSyntax {

    @Override
    public double eval() {
        return 0;
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
        return null;
    }

    @Override
    public SyntaxType getType() {
        return null;
    }
    
}
