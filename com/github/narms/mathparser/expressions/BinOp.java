package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.IllegalSyntaxException;
import com.github.narms.mathparser.output.Output;

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
    public ExpressionSyntax derivative(String variable){
        if (this.operator.equals("^")){
            
        }else{

        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean hasVar(String name) {
        return this.value1.hasVar(name) || this.value2.hasVar(name);
    }

    @Override
    public boolean defVar(String name, Output value) {
        boolean left = this.value1.defVar(name, value);
        boolean right = this.value2.defVar(name, value);
        return  left || right;
    }

    @Override
    public String toString() {
        return "("+this.value1.toString()+" "+this.operator+" "+this.value2.toString()+")";
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.BINOPEXPR;
    }

    @Override
    public ExpressionSyntax reduce(){
        this.value1 = this.value1.reduce();
        this.value2 = this.value2.reduce();
        switch (this.evaluatable()){
            case BOOL:
            return new Const(this.approximate());
            case NUM:
            return new Const(this.approximate());
            default:
            return this;
        }
    }

    public String getOperator(){
        return this.operator;
    }
    public ExpressionSyntax getValue1(){
        return this.value1;
    }
    public ExpressionSyntax getValue2(){
        return this.value2;
    }
    @Override
    public EvalType evaluatable(){
        EvalType value1Type = this.value1.evaluatable();
        EvalType value2Type = this.value2.evaluatable();
        if (value1Type.equals(EvalType.SOFTTREE) || value2Type.equals(EvalType.SOFTTREE)){
            return EvalType.SOFTTREE;
        }
        else if (value1Type.equals(EvalType.HARDTREE) || value2Type.equals(EvalType.HARDTREE)){
            return EvalType.HARDTREE;
        }
        else if ((value1Type.equals(EvalType.BOOL) && value2Type.equals(EvalType.BOOL)) || 
        (value1Type.equals(EvalType.NUM) && value2Type.equals(EvalType.NUM) && (this.operator.equals(">") || this.operator.equals("<")))){
            return EvalType.BOOL;
        } 
        else{
            return EvalType.NUM;
        }
        
    }

    @Override
    public Output approximate(){
        if (this.evaluatable().equals(EvalType.HARDTREE))
            throw new IllegalSyntaxException("Undefined variable in "+this.toString());
        return eval();
    }

    private Output eval(){
        switch(this.operator){
            case "^":
            return this.value1.approximate().applyBin("^", this.value2.approximate());
            case "|":
            return this.value1.approximate().applyBin("|", this.value2.approximate());
            case "&":
            return this.value1.approximate().applyBin("&", this.value2.approximate());
            case ">":
            return this.value1.approximate().applyBin(">", this.value2.approximate());
            case "<":
            return this.value1.approximate().applyBin("<", this.value2.approximate());
            default:
            throw new IllegalSyntaxException("Illegal operator on "+this.toString());
        }
    }

    @Override
    public ExpressionSyntax normalize(){
        this.value1 = this.value1.reduce();
        this.value1 = this.value1.normalize();
        this.value2 = this.value2.reduce();
        this.value2 = this.value2.normalize();
        return this.reduce();
    }

    @Override
    public int degree(){
        if (this.operator.equals("^")){
            if (this.value2.evaluatable()==EvalType.NUM)
            return this.value1.degree()*this.value2.approximate().numValue().intValue();
        }
        throw new IllegalSyntaxException("Illegal operator on "+this.toString());
    }

    @Override
    public int unboundedVariables(){
        return value1.unboundedVariables() + value2.unboundedVariables();
    }

    @Override
    public ExpressionSyntax copy(){
        return new BinOp(this.operator, this.value1.copy(), this.value2.copy());
    }
}
