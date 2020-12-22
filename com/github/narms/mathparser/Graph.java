package com.github.narms.mathparser;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.github.narms.mathparser.expressions.Const;
import java.awt.Color;
public class Graph extends Canvas{
    private static final long serialVersionUID = 1L;
    private final ExpressionSyntax expression;
    private int[] values;
    private final int width;
    private final int height;
    private final double scale;

    public Graph(ExpressionSyntax e, int screenWidth, int screenHeight, double scale){
        this.expression = e;
        this.width = screenWidth;
        this.height = screenHeight;
        this.values = new int[width];
        this.scale = scale;
        int i = screenWidth/-2;
        while (i<width/2){
            this.expression.defVar("x", i*this.scale);
            this.values[i+screenWidth/2] = (int)(height-(((Number)((Const)expression.reduce()).getValue()).doubleValue()/this.scale));
            i++;
        }
    }

    public void graphExpression() {
        JFrame frame = new JFrame();
        this.setSize(width, height);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g){
        int x = 0;
        for (int y: this.values){
            g.fillRect(x, y, 1, 1);
            x++;
        }
        g.setColor(Color.RED);
        g.drawLine(0, height, width, height);
        g.drawLine(width/2, 0, width/2, height);
    }
}
