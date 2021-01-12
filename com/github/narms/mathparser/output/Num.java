package com.github.narms.mathparser.output;

import javax.management.RuntimeErrorException;

import com.github.narms.mathparser.exceptions.IllegalSyntaxException;
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

    @Override
    public Output applyBin(String op, Output other) {
        assert (other instanceof Bool);
        switch (op){
            case "+":
            if (other instanceof Complex){
                return new Complex(other.complexValue()[0]+this.numValue(), other.complexValue()[1]);
            }else{
                assert other instanceof Num;
                return new Num(this.numValue()+other.numValue());
            }
            case "*":
            if (other instanceof Complex){
                return new Complex(other.complexValue()[0]*this.numValue(), other.complexValue()[1]*this.numValue());
            }else{
                assert (other instanceof Num);
                return new Num(other.numValue()+this.numValue());
            }
            case "^":
            throw new IllegalSyntaxException("use of WIP operator ^");//TODO, exponentiation
            case ">":
            throw new IllegalSyntaxException("use of WIP operator >");//TODO, comparison
            case "<":
            throw new IllegalSyntaxException("use of WIP operator <");
            default:
            throw new IllegalTypeException("Illegal op '"+op+"' on Num.java");
        }
    }

    @Override
    public Output apply(String op) {
        switch (op){
            case "-":
            return new Num(-1*this.value);
            case "1/":
            if (this.value.equals(0D))
            throw new IllegalTypeException("arithmetic throw"); //TODO
            return new Num(1/this.value);
            default:
            throw new IllegalTypeException("Illegal op '"+op+"' on Num.java");
        }
    }
}
