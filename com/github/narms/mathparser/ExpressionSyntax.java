package com.github.narms.mathparser;

public abstract class ExpressionSyntax extends Syntax{
    public abstract Object approximate();
    public abstract boolean hasVar(String name);
    public abstract boolean defVar(String name, Object value);
    public abstract String toString();
    public abstract ExpressionSyntax derivative(String variable);
    public abstract ExpressionSyntax reduce();
    public abstract EvalType evaluatable();
}