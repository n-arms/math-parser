package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.Token;
import com.github.narms.mathparser.output.Bool;
import com.github.narms.mathparser.output.Num;
import com.github.narms.mathparser.output.Output;

public class Const extends LiteralSyntax {
    private Output value;
    public Const(Token t){
        this.value = new Num(Double.parseDouble(t.getValue()));
    }
    public Const(double d){
        this.value = new Num(d);
    }
    public Const(Output o){
        this.value = o;
    }

    @Override
    public ExpressionSyntax derivative(String name){
        return new Const(0);
    }

    @Override
    public boolean hasVar(String name) {
        return false;
    }

    @Override
    public boolean defVar(String name, Output value) {
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.CONSTEXPR;
    }

    @Override
    public ExpressionSyntax reduce(){
        return this;
    }

    @Override
    public EvalType evaluatable(){
        if (value instanceof Bool){
            return EvalType.BOOL;
        }else{
            return EvalType.NUM;
        }
    }

    @Override
    public Output getValue(){
        return this.value;
    }

    @Override
    public Output approximate(){
        return this.value;
    }
}
