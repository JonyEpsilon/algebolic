// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

import java.util.List;

public class Constant implements JExpr {
    private double c;

    public Constant(double c) {
        this.c = c;
    }

    public double evaluate(List<Double> vars) {
        return c;
    }
}
