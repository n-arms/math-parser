package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.UndefinedVariableException;

public class Var extends LiteralSyntax {
    private String name;
    private Object value;
    private boolean defined;

    public Var(String name){
        this.name = name;
        this.defined = false;
    }

    @Override
    public ExpressionSyntax derivative(String name){
        if (this.name.equals(name)){
            return new Const(1);
        }
        return new Const(0);
    }

    @Override
    public boolean hasVar(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean defVar(String name, Object value) {
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
            if (this.value instanceof Boolean){
                return EvalType.BOOL;
            }else{
                return EvalType.DOUBLE;
            }
        }
        return EvalType.TREE;
    }

    public Object getValue(){
        if (this.defined){
            if (this.value instanceof Number){
                return ((Number)this.value).doubleValue();
            }
            return this.value;
        }
        throw new UndefinedVariableException("Undefined Variable "+this.name);
    }
}
