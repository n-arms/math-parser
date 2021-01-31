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

    public PolyOp(ArrayList<ExpressionSyntax> values, String operator){
        this.values = values;
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
        PolyOp output = new PolyOp("+");
        switch (operator){
            case "+":
            for (ExpressionSyntax es: values){
                output.addValue(es.derivative(variable));
            }
            return output;
            case "*":
            List<PolyOp> terms = new ArrayList<PolyOp>();
            for (int i = 0; i<values.size(); i++){
                terms.add(new PolyOp("*"));
                for (int j = 0; j<values.size(); j++){
                    if (j==i){
                        terms.get(i).addValue(values.get(j).derivative(variable));
                    }else{
                        terms.get(i).addValue(values.get(j));
                    }
                }
            }
            for (PolyOp p: terms)
            output.addValue(p);
            return output;
            default:
            throw new IllegalSyntaxException("Undefined operator for "+this); 
        }
    }

    @Override
    public ExpressionSyntax reduce() {
        for (int i = 0; i<values.size(); i++){
            if (values.get(i).evaluatable()==EvalType.NUM)
            values.set(i, values.get(i).reduce());
        }
        sort();
        ArrayList<ExpressionSyntax> reduced = new ArrayList<ExpressionSyntax>();
        reduced.add(values.get(0));
        for (int i = 1; i<values.size(); i++){
            if (reduced.get(reduced.size()-1).evaluatable().equals(EvalType.NUM) && values.get(i).evaluatable().equals(EvalType.NUM)){
                reduced.set(reduced.size()-1, new Const(values.get(i).approximate().applyBin(this.operator, reduced.get(reduced.size()-1).approximate())));
            }else{
                reduced.add(values.get(i));
            }
        }
        if (reduced.size() > 1)
        return new PolyOp(reduced, this.operator);
        return reduced.get(0);
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

    private void sort(){
        List<List<ExpressionSyntax>> buckets = new ArrayList<List<ExpressionSyntax>>();
        for (int i = 0; i<3; i++)
        buckets.add(new ArrayList<ExpressionSyntax>());

        for (ExpressionSyntax e: values){
            switch (e.evaluatable()){
                case HARDTREE:
                buckets.get(2).add(e);
                break;
                case SOFTTREE:
                buckets.get(1).add(e);
                break;
                case NUM:
                buckets.get(0).add(e);
                break;
                default:
                throw new IllegalSyntaxException("Illegal evaluatable type on "+this);
            }
        }
        int index = 0;
        for (List<ExpressionSyntax> i: buckets){
            for (ExpressionSyntax j: i){
                values.set(index, j);
                index++;
            }
        }
    }

    @Override
    public ExpressionSyntax normalize(){
        for (ExpressionSyntax es: values){
            es = es.reduce();
            es = es.normalize();
        }
        
        return this.reduce(); //TODO
    }

    @Override
    public int degree(){
        switch (this.operator){
            case "+":
            int max = 0;
            for (ExpressionSyntax es: values)
            max = (max > es.degree()) ? max : es.degree();
            return max;
            case "*":
            int total = 0;
            for (ExpressionSyntax es: values)
            total += es.degree();
            return total;
            default:
            throw new IllegalSyntaxException("Illegal op "+this.operator+" on polyOp "+this);
        }
    }
}
