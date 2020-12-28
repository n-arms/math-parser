package com.github.narms.mathparser.expressions;

import com.github.narms.mathparser.EvalType;
import com.github.narms.mathparser.ExpressionSyntax;
import com.github.narms.mathparser.SyntaxType;
import com.github.narms.mathparser.exceptions.IllegalSyntaxException;

public class UnaryOp extends ExpressionSyntax {
    private ExpressionSyntax contents;
    private String operator; //either "-", "1/" or "~"

    public UnaryOp(String operator, ExpressionSyntax contents){
        this.contents = contents;
        this.operator = operator;
    }

    @Override
    public boolean hasVar(String name) {
        return this.contents.hasVar(name);
    }

    @Override
    public boolean defVar(String name, Object value) {
        return this.contents.defVar(name, value);
    }

    @Override
    public String toString() {
        return "("+this.operator+this.contents.toString()+")";
    }

    @Override
    public ExpressionSyntax derivative(String variable) {
        if (this.operator.equals("-")){
            return new BinOp("*", this.contents.derivative(variable), new Const(-1));
        } else if(this.operator.equals("1/")){
            return new BinOp("*", new UnaryOp("-", this.contents.derivative(variable)), new UnaryOp("1/", new BinOp("^", this.contents, new Const(2))));
        } else if(this.operator.equals("~")){
            return new Const(0);
        }
        throw new IllegalSyntaxException("Illegal operator on "+this.toString());
    }

    @Override
    public ExpressionSyntax reduce() {
        this.contents = this.contents.reduce();
        switch (this.operator){
            case "-":
            switch(this.contents.evaluatable()){
                case NUM:
                return new Const((Double)this.eval());
                case BOOL:
                default:
                return (new BinOp("*", this.contents, new Const(-1))).reduce();
            }
            
            case "1/":
            switch (this.contents.evaluatable()){
                case BOOL:
                throw new IllegalSyntaxException("Illegal operand return type 'bool' for "+this.toString());
                case NUM:
                return new Const((Double)this.eval());
                default:
                return new UnaryOp("1/", this.contents);

            }
            case "~":
            switch (this.contents.evaluatable()){
                case BOOL:
                return new BoolConst(!(Boolean)((BoolConst)this.contents).getValue());
                case NUM:
                throw new IllegalSyntaxException("Illegal operand return type 'num' for "+this.toString());
                default:
                return new UnaryOp("~", this.contents);
                
            }
            default:
            throw new IllegalSyntaxException("Illegal operator on "+this.toString());
        }
    }

    @Override
    public SyntaxType getType() {
        return SyntaxType.UNARYOPEXPR;
    }

    @Override
    public EvalType evaluatable(){
        return this.contents.evaluatable();
    }

    @Override
    public Object approximate(){
        return this.eval();
    }

    private Object eval(){
        switch (this.operator){
            case "-":
            return -1*(Double)this.contents.approximate();
            case "1/":
            return 1/(Double)this.contents.approximate();
            case "~":
            return !(Boolean)this.contents.approximate();
            default:
            throw new IllegalSyntaxException("Illegal operator on "+this.toString());
        }
    }
}
