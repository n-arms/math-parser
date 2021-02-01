package com.github.narms.mathparser;

import com.github.narms.mathparser.output.Output;

public abstract class ExpressionSyntax extends Syntax{
    public abstract Output approximate();
    public abstract boolean hasVar(String name);
    public abstract boolean defVar(String name, Output value);
    public abstract String toString();
    public abstract ExpressionSyntax derivative(String variable);
    public abstract ExpressionSyntax reduce();
    public abstract EvalType evaluatable();
    public abstract ExpressionSyntax normalize();
    public abstract int degree();
    public abstract int unboundedVariables();
    public abstract ExpressionSyntax copy();
}