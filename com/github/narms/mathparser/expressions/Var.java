package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.UndefinedVariableException;

public class Var extends ExpressionSyntax {
    private String name;
    private double value;
    private boolean defined;

    public Var(String name){
        this.name = name;
        this.defined = false;
    }

    @Override
    public double eval() {
        if (this.defined){
            return this.value;
        }else{
            throw new UndefinedVariableException("Undefined Variable "+this.name+" was called");
        }
    }

    @Override
    public boolean hasVar(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean defVar(String name, double value) {
        if (this.name.equals(name)){
            this.value = value;
            this.defined = true;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.VAREXPR;
    }
}
