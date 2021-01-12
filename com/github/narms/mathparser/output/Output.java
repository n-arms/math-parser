package com.github.narms.mathparser.output;

public abstract class Output {
    public abstract String toString();
    public abstract Double numValue();
    public abstract Boolean boolValue();
    public abstract Double[] complexValue();
    public abstract Output applyBin(String op, Output other); //use with ops +, *, |, &
    public abstract Output apply(String op); //use with ops ~, 1/, -
}
