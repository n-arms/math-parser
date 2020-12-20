package com.github.narms.mathparser;

public abstract class ExpressionSyntax extends Syntax{
    public abstract double eval();
    public abstract boolean hasVar(String name);
    public abstract boolean defVar(String name, double value);
    public abstract String toString();
    public abstract ExpressionSyntax derivative(String variable);
    public abstract ExpressionSyntax reduce();
}