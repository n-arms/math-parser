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

    @Override
    public Output applyBin(String op, Output other) {
        if (!(other instanceof Bool))
        throw new IllegalTypeException("Cannot perform op '"+op+"' on Bool.java");
        switch (op){
            case "|":
            return new Bool(this.value || other.boolValue());
            case "&":
            return new Bool(this.value && other.boolValue());
            default:
            throw new IllegalTypeException("Cannot perform op '"+op+"' Bool.java");
        }
    }

    @Override
    public Output apply(String op){
        if (op.equals("~"))
        return new Bool(!this.value);
        throw new IllegalTypeException("Cannot perform op '"+op+"' Bool.java");
    }

    @Override
    public Output copy(){
        return new Bool(this.value);
    }

    @Override
    public boolean equals(Output o){
        return (o instanceof Bool) && (o.boolValue().equals(value));
    }
}
