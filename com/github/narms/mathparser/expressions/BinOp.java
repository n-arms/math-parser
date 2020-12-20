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
    public ExpressionSyntax derivative(String variable){
        if (this.operator.equals("+") || this.operator.equals("-")){
            return new BinOp(this.operator, this.value1.derivative(variable), this.value2.derivative(variable));
        }else if (this.operator.equals("*")){
            return new BinOp("+", new BinOp("*", this.value1, this.value2.derivative(variable)), 
            new BinOp("*", this.value2, this.value1.derivative(variable)));
        }else if (this.operator.equals("/")){
            return new BinOp("/", new BinOp("-", new BinOp("*", this.value1.derivative(variable), this.value2), new BinOp("*", this.value2.derivative(variable), this.value1)), new BinOp("*", this.value2, this.value2));
        }
        throw new IllegalArgumentException();
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

    @Override
    public ExpressionSyntax reduce(){
        ExpressionSyntax safeValue1 = this.value1.reduce();
        ExpressionSyntax safeValue2 = this.value2.reduce();
        boolean isZero1 = false;
        boolean isZero2 = false;
        boolean isOne2 = false;
        if (safeValue1 instanceof Const){
            isZero1 = safeValue1.eval()==0;
        }
        if (safeValue2 instanceof Const){
            isZero2 = safeValue2.eval()==0;
            isOne2 = safeValue2.eval()==1;
        }
        switch (this.operator){
            case "+":
            return this.commute();
            case "-":
            return this.commute();
            case "*":
            return this.distribute();
            case "/":
            if (isZero1){
                return new Const(0);
            }
            if (isZero2){
                throw new ArithmeticException();
            }
            if (isOne2){
                return safeValue1;
            }
            break;
            default:
            break;
        }
        return new BinOp(this.operator, safeValue1, safeValue2);
    }
    private ExpressionSyntax distribute(){
        if (this.value1.reduce() instanceof Const){
            if (this.value1.reduce().eval()==0){
                return new Const(0);
            }
        }
        if (this.value2.reduce() instanceof Const){
            if (this.value2.reduce().eval()==0){
                return new Const(0);
            }
        }
        if (this.value1.reduce() instanceof Const){
            if (this.value1.reduce().eval()==1){
                return this.value2.reduce();
            }
        }
        if (this.value2.reduce() instanceof Const){
            if (this.value2.reduce().eval()==1){
                return this.value1.reduce();
            }
        }
        if (this.value1.reduce() instanceof BinOp && value2.reduce() instanceof BinOp){
            if (((BinOp)this.value1.reduce()).getOperator().equals("+") && ((BinOp)this.value2.reduce()).getOperator().equals("+")){
                return new BinOp("+", new BinOp("+", new BinOp("*", ((BinOp)this.value1.reduce()).getValue1(), ((BinOp)this.value2.reduce()).getValue1()), 
                new BinOp("*", ((BinOp)this.value1.reduce()).getValue2(), ((BinOp)this.value2.reduce()).getValue2())), new BinOp("+", 
                new BinOp("*", ((BinOp)this.value1.reduce()).getValue2(), ((BinOp)this.value2.reduce()).getValue1()), 
                new BinOp("*", ((BinOp)this.value1.reduce()).getValue1(), ((BinOp)this.value2.reduce()).getValue2())));
            }
        }
        if (this.value1.reduce() instanceof BinOp){
            return new BinOp("+", new BinOp("*", this.value2.reduce(), ((BinOp)this.value1.reduce()).getValue1()), new BinOp("*", this.value2.reduce(), ((BinOp)this.value1.reduce()).getValue2()));
        }
        if (this.value2.reduce() instanceof BinOp){
            return new BinOp("+", new BinOp("*", this.value1.reduce(), ((BinOp)this.value2.reduce()).getValue1()), new BinOp("*", this.value1.reduce(), ((BinOp)this.value2.reduce()).getValue2()));
        }
        
        return this.reduce();
    }
    private ExpressionSyntax commute(){
        System.out.println("commuting");
        if (this.value1.reduce() instanceof Const){
            if (this.value1.reduce().eval()==0){
                System.out.println("found zero");
                if (this.operator.equals("+")){
                    System.out.println("adding zeroes");
                    return this.value2.reduce();
                }else if (this.operator.equals("-")){
                    return new BinOp("*", new Const(-1), this.value2.reduce());
                }
                
            }
        }
        if (this.value2.reduce() instanceof Const){
            if (this.value2.reduce().eval()==0){
                return this.value1.reduce();
            }
        }
        return this;
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
}
