package com.github.narms.mathparser;

import com.github.narms.mathparser.output.*;


public class Test {
    public static void main(String[] args){
        Output[] values = {new Complex(1, 2), new Num(4), new Bool(false)};
        for (Output o: values)
        System.out.println(o);
    }
}