// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.List;

public class Constant extends NullaryExpr {
    private final double c;

    public Constant(double c) {
        this.c = c;
    }

    @Override
    public double evaluate(List<Double> vars) {
        return c;
    }

    @Override
    public JExpr copy() {
        return new Constant(c);
    }
}
