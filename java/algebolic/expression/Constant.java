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
    public double[] evaluateD(List<Double> vars) {
        int n = vars.size();
        double[] res = new double[n + 1];

        res[0] = c;

        // The derivative of a constant is zero with respect to any variable.
        for (int i = 0; i < n; i++) {
            res[i + 1] = 0.0;
        }
        return res;
    }
}