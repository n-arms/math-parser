package com.github.narms.mathparser;
import com.github.narms.mathparser.expressions.*;

public class Test{
    public static void main(String[] args){
        ExpressionSyntax tree = new BinOp("+", new Const(5), new BinOp("-", new Const(10), new Const(3)));
        System.out.println(tree);
        System.out.println(tree.eval());
    }
}