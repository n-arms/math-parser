package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.output.Output;

public abstract class LiteralSyntax extends ExpressionSyntax {

    @Override
    public abstract boolean hasVar(String name);

    @Override
    public abstract boolean defVar(String name, Output value);

    @Override
    public abstract String toString();

    @Override
    public abstract ExpressionSyntax derivative(String variable);

    @Override
    public abstract ExpressionSyntax reduce();

    @Override
    public abstract EvalType evaluatable();

    @Override
    public abstract SyntaxType getType();

    public abstract Object getValue();
    
}
