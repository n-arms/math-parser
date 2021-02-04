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

    public PolyOp(List<ExpressionSyntax> values, String operator){
        this.values = values;
        this.operator = operator;
    }

    public PolyOp(String operator, ExpressionSyntax left, ExpressionSyntax right){
        this.operator = operator;
        this.values = new ArrayList<ExpressionSyntax>();
        this.values.add(left);
        this.values.add(right);
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
        if (reduced.size() <= 1)
        return reduced.get(0);
        Const zero = new Const(0D);
        Const one = new Const(1D);
        int i = 0;
        switch (operator){
            case "+":
            while (i < reduced.size()){
                if (reduced.get(i).equals(zero)){
                    reduced.remove(i);
                }else{
                    i++;
                }
            }
            if (reduced.size() <= 1)
            return reduced.get(0);
            return new PolyOp(reduced, this.operator);
            case "*":
            while (i<reduced.size()){
                if (reduced.get(i).equals(zero)){
                    return zero;
                }else if (reduced.get(i).equals(one)){
                    reduced.remove(i);
                }else{
                    i++;
                }
            }
            if (reduced.size() <= 1)
            return reduced.get(0);
            return new PolyOp(reduced, this.operator);
            default:
            throw new IllegalSyntaxException("Illegal op "+operator+" on "+this);
        }

        
        
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
        if (operator.equals("*")){
            for (int i = 0; i<values.size(); i++){
                if (values.size() > 1){
                    values.add(distribute(values.get(0), values.get(1)));
                    values.remove(0);
                    values.remove(0);
                }
            }
        }
        groupLikeTerms();
        for (ExpressionSyntax es: values){
            es = es.reduce();
        }
        return this.reduce();
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

    private void groupLikeTerms(){
        return; //TODO: HA fools, you thought i would ever actually implement this
    }

    private ExpressionSyntax distribute(ExpressionSyntax a, ExpressionSyntax b){
        ArrayList<ExpressionSyntax> terms = new ArrayList<ExpressionSyntax>();
        if (a instanceof PolyOp){
            switch (((PolyOp)a).getOperator()){
                case "+":
                if (b instanceof PolyOp){
                    switch (((PolyOp)b).getOperator()){
                        case "+":
                        for (ExpressionSyntax i: ((PolyOp)a).getTerms()){
                            for (ExpressionSyntax j: ((PolyOp)b).getTerms())
                            terms.add(new PolyOp("*", i, j));
                        }
                        return new PolyOp(terms, "+");
                        case "*":
                        for (ExpressionSyntax i: ((PolyOp)a).getTerms()){
                            terms.add(b.copy());
                            ((PolyOp)terms.get(terms.size()-1)).addValue(i);
                        }
                        return new PolyOp(terms, "+");
                        default:
                        throw new IllegalSyntaxException("Illegal op while distrbuting "+a+", "+b);
                    }
                }else{
                    for (ExpressionSyntax es: ((PolyOp)a).getTerms())
                    terms.add(new PolyOp("*", es, b));
                    return new PolyOp(terms, "+");
                }
                case "*":
                if (b instanceof PolyOp){
                    switch (((PolyOp)b).getOperator()){
                        case "+":
                        for (ExpressionSyntax i: ((PolyOp)b).getTerms()){
                            terms.add(a.copy());
                            ((PolyOp)terms.get(terms.size()-1)).addValue(i);
                        }
                        return new PolyOp(terms, "+");
                        case "*":
                        for (ExpressionSyntax i: ((PolyOp)b).getTerms()){
                            ((PolyOp)a).addValue(i);
                        }
                        return a;
                        default:
                        throw new IllegalSyntaxException("Illegal op while distrbuting "+a+", "+b);
                    }
                }else{
                    ((PolyOp)a).addValue(b);
                    return a;
                }
                default:
                throw new IllegalSyntaxException("Illegal op while distrbuting "+a+", "+b);
            }
        }else{
            if (b instanceof PolyOp){
                switch (((PolyOp)b).getOperator()){
                    case "+":
                    for (ExpressionSyntax i: ((PolyOp)b).getTerms()){
                        terms.add(new PolyOp("*", i, a));
                    }
                    return new PolyOp(terms, "+");
                    case "*":
                    ((PolyOp)b).addValue(a);
                    return b;
                    default:
                    throw new IllegalSyntaxException("Illegal op while distributing "+a+", "+b);
                }
            }else{
                return new PolyOp("*", a, b);
            }
        }
    }


    public int unboundedVariables(){
        int total = 0;
        for (ExpressionSyntax es: values)
        total += es.unboundedVariables();
        return total;
    }

    public String getOperator(){
        return this.operator;
    }

    public List<ExpressionSyntax> getTerms(){
        return values;
    }

    @Override
    public ExpressionSyntax copy(){
        ArrayList<ExpressionSyntax> output = new ArrayList<ExpressionSyntax>();
        for (ExpressionSyntax es: values)
        output.add(es.copy());
        return new PolyOp(output, this.operator);
    }

    @Override
    public boolean equals(ExpressionSyntax e){
        return false;//TODO
    }
}
