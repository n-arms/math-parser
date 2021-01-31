package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.UndefinedVariableException;
import com.github.narms.mathparser.output.Num;
import com.github.narms.mathparser.output.Output;

public class Var extends ExpressionSyntax {
    private String name;
    private Output value;
    private boolean defined;

    public Var(String name){
        this.name = name;
        this.defined = false;
    }

    @Override
    public ExpressionSyntax derivative(String name){
        if (this.name.equals(name)){
            return new Const(new Num(1));
        }
        return new Const(new Num(0));
    }

    @Override
    public boolean hasVar(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean defVar(String name, Output value) {
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

    @Override
    public ExpressionSyntax reduce(){
        return this;
    }

    @Override
    public EvalType evaluatable(){
        if (this.defined){
            return EvalType.SOFTTREE;
        }
        return EvalType.HARDTREE;
    }

    public Output getValue(){
        if (this.defined){
            return this.value;
        }
        throw new UndefinedVariableException("Undefined Variable "+this.name);
    }

    public Output approximate(){
        return this.getValue();
    }

    @Override
    public ExpressionSyntax normalize(){
        return this;
    }

    @Override
    public int degree(){
        return 1;
    }
}
