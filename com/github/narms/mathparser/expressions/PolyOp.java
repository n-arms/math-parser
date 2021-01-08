package com.github.narms.mathparser.expressions;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.IllegalSyntaxException;

public class PolyOp extends ExpressionSyntax {
    private String operator;
    private List<ExpressionSyntax> values;

    public PolyOp(List<ExpressionSyntax> values, String operator){
        this.values = values;
        this.operator = operator;
    }

    @Override
    public Object approximate() {
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
    public boolean defVar(String name, Object value) {
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
        for (ExpressionSyntax es: this.values)
        output.append(es.toString());
        output.append(')');
        return output.toString();
    }

    @Override
    public ExpressionSyntax derivative(String variable) {
        
        switch (operator){
            case "+":
            List<ExpressionSyntax> outputAdd = new ArrayList<ExpressionSyntax>();
            for (ExpressionSyntax es: values){
                outputAdd.add(es.derivative(variable));
            }
            return new PolyOp(outputAdd, "+");
            case "*":
            throw new IllegalSyntaxException("Undefiend behaviour");//TODO: polyop multiplicative derivative
            default:
            throw new IllegalSyntaxException("Undefined operator for "+this); 
        }
    }

    @Override
    public ExpressionSyntax reduce() {
        // TODO polyop reduce
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
                state = EvalType.HARDTREE;
                break;
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
    
    private Double eval(){
        Double output;
        switch (this.operator){
            case "+":
            output = 0.0;
            for (ExpressionSyntax es: this.values)
                output+=(Double)es.approximate();
            case "*":
            output = 1.0;
            for (ExpressionSyntax es: this.values)
                output+=(Double)es.approximate();
            default:
            throw new IllegalSyntaxException("Illegal op "+this.operator+" on polyOp "+this);
        }
    }
}
