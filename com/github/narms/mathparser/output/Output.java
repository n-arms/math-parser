package com.github.narms.mathparser.output;

public abstract class Output {
    public abstract String toString();
    public abstract Double numValue();
    public abstract Boolean boolValue();
    public abstract Double[] complexValue();
}
