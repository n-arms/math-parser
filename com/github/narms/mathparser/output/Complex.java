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

    @Override
    public Output applyBin(String op, Output other){
        switch(op){
            case "+":
            if (other instanceof Num){
                return new Complex(this.complexValue()[0]+other.numValue(), this.complexValue()[1]);
            }else{
                assert (other instanceof Complex);
                return new Complex(this.value[0]+other.complexValue()[0], this.value[1]+other.complexValue()[1]);
            }
            case "*":
            if (other instanceof Num){
                return new Complex(this.value[0]*other.numValue(), this.value[1]*other.numValue());
            }else{
                assert (other instanceof Complex);
                return new Complex(this.value[0]*other.complexValue()[0]-this.value[1]*other.complexValue()[1], this.value[1]*other.complexValue()[0]+this.value[0]*other.complexValue()[1]);
            }
            default:
            throw new IllegalTypeException("Illegal op '"+op+"' on Complex.java");
        }
    }

    @Override
    public Output apply(String op){
        switch (op){
            case "-":
            return new Complex(-1*this.value[0], -1*this.value[1]);
            case "1/":
            return new Complex(this.value[0]/this.complexSquare().numValue(), this.conjugate().complexValue()[1]/this.complexSquare().numValue());
            default:
            throw new IllegalTypeException("Illegal op '"+op+"' on Complex.java");
        }
    }

    public Output conjugate(){
        return new Complex(this.value[0], -1*this.value[1]);
    }

    public Output complexSquare(){
        return new Num(Math.pow(this.value[0], 2)+Math.pow(this.value[1], 2));
    }
    
}
