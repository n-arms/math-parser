package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;

public class BinOp extends ExpressionSyntax {
    private String operator;
    private ExpressionSyntax value1;
    private ExpressionSyntax value2;
    public BinOp(String operator, ExpressionSyntax value1, ExpressionSyntax value2){
        this.operator = operator;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public double eval() {
        switch(this.operator){
            case "+":
            return this.value1.eval() + this.value2.eval();
            case "-":
            return this.value1.eval() - this.value2.eval();
            case "*":
            return this.value1.eval() * this.value2.eval();
            case "/":
            return this.value1.eval() / this.value2.eval();
            default:
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean hasVar(String name) {
        return this.value1.hasVar(name) || this.value2.hasVar(name);
    }

    @Override
    public boolean defVar(String name, double value) {
        return this.value1.defVar(name, value) || this.value2.defVar(name, value);
    }

    @Override
    public String toString() {
        return "("+this.value1.toString()+" "+this.operator+" "+this.value2.toString()+")";
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.BINOPEXPR;
    }
    
}
