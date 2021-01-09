package com.github.narms.mathparser.output;

import com.github.narms.mathparser.exceptions.IllegalTypeException;

public class Bool extends Output{
    private Boolean value;

    public Bool(boolean value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Double numValue() {
        throw new IllegalTypeException("Cannot convert from Bool to Num");
    }

    @Override
    public Boolean boolValue() {
        return value;
    }

    @Override
    public Double[] complexValue() {
        throw new IllegalTypeException("Cannot convert from Num to Bool");
    }
    
}
