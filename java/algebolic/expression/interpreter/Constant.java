// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

public class Constant implements JExpr {
    private double c;

    public Constant(double c) {
        this.c = c;
    }

    public double evaluate(double[] vars) {
        return c;
    }
}
