package com.github.narms.mathparser.output;

import com.github.narms.mathparser.exceptions.IllegalTypeException;

public class Num extends Output{
    Double value;
    public Num(double value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public Double numValue() {
        return value;
    }

    @Override
    public Boolean boolValue() {
        throw new IllegalTypeException("Cannot convert from Num to Bool");
    }

    @Override
    public Double[] complexValue() {
        return new Double[]{value, 0D};
    }
    
}
