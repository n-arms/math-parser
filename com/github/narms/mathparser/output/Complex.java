package com.github.narms.mathparser.output;

import com.github.narms.mathparser.exceptions.IllegalTypeException;

public class Complex extends Output{
    private Double[] value;

    public Complex(double real, double imag){
        this.value = new Double[]{real, imag};
    }

    @Override
    public String toString() {
        return "("+this.value[0]+" + "+this.value[1]+"i)";
    }

    @Override
    public Double numValue() {
        if (value[1]==0D){
            return value[0];
        }
        return Double.NaN;
    }

    @Override
    public Boolean boolValue() {
        throw new IllegalTypeException("Cannot convert from Complex to Bool");
    }

    @Override
    public Double[] complexValue() {
        return this.value;
    }
    
}
