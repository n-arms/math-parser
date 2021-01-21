package com.github.narms.mathparser.expressions;

import java.util.ArrayList;
import java.util.List;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.IllegalSyntaxException;
import com.github.narms.mathparser.output.Num;
import com.github.narms.mathparser.output.Output;

public class PolyOp extends ExpressionSyntax {
    private String operator;
    private List<ExpressionSyntax> values;

    public PolyOp(ExpressionSyntax value, String operator){
        this.values = new ArrayList<ExpressionSyntax>();
        values.add(value);
        this.operator = operator;
    }

    public PolyOp(String operator){
        this.values = new ArrayList<ExpressionSyntax>();
        this.operator = operator;
    }

    public void addValue(ExpressionSyntax value){
        values.add(value);
    }

    @Override
    public Output approximate() {
        if (this.evaluatable().equals(EvalType.HARDTREE)){
            throw new IllegalSyntaxException("Undefined Variable in "+this);
        }
        return this.eval();
    }

    @Override
    public boolean hasVar(String name) {
        boolean output = false;
        for (ExpressionSyntax es: this.values){
            output = es.hasVar(name) | output;
        }
        return output;
    }

    @Override
    public boolean defVar(String name, Output value) {
        boolean output = false;
        for (ExpressionSyntax es: this.values){
            output = es.defVar(name, value) | output;
        }
        return output;
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        output.append('(');
        for (int i = 0; i<this.values.size(); i++){
            output.append(this.values.get(i).toString());
            if (i != (this.values.size()-1))
            output.append(" "+this.operator+" ");
        }
        
        output.append(')');
        return output.toString();
    }

    @Override
    public ExpressionSyntax derivative(String variable) {
        
        switch (operator){
            case "+":
            PolyOp output = new PolyOp("+");
            for (ExpressionSyntax es: values){
                output.addValue(es);
            }
            return output;
            case "*":
            throw new IllegalSyntaxException("Undefiend behaviour");//TODO: polyop multiplicative derivative
            default:
            throw new IllegalSyntaxException("Undefined operator for "+this); 
        }
    }

    @Override
    public ExpressionSyntax reduce() {
        for (int i = 0; i<values.size(); i++){
            values.set(i, values.get(i).reduce());
        }
        return this;
    }

    @Override
    public EvalType evaluatable() {
        assert values.size() > 0;
        EvalType state = values.get(0).evaluatable();
        for (ExpressionSyntax es: values){
            switch (es.evaluatable()){
                case SOFTTREE:
                if (!state.equals(EvalType.HARDTREE)){
                    state = EvalType.SOFTTREE;
                }
                break;
                case HARDTREE:
                return EvalType.HARDTREE;
                default:
                break;
            }
        }
        return state;
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.POLYOPEXPR;
    }
    
    private Output eval(){
        Output output;
        switch (this.operator){
            case "+":
            output = new Num(0);
            for (ExpressionSyntax es: this.values)
                output = output.applyBin("+", es.approximate());
            return output;
            case "*":
            output = new Num(1);
            for (ExpressionSyntax es: this.values)
                output = output.applyBin("*", es.approximate());
            return output;
            default:
            throw new IllegalSyntaxException("Illegal op "+this.operator+" on polyOp "+this);
        }
    }
}
