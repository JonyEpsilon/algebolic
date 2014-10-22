// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.List;

public class Divide extends BinaryExpr {

    public Divide(JExpr arg1, JExpr arg2) {
        super(arg1, arg2);
    }

    @Override
    public double evaluate(List<Double> vars) {
        double a2 = arg2.evaluate(vars);
        if (a2 == 0) return 1.0;
        else return arg1.evaluate(vars) / a2;
    }

    @Override
    public double[] evaluateD(List<Double> vars) {
        int n = vars.size();
        double[] res = new double[n + 1];

        // we check the denominator first. If it is zero, we extend the approach of Koza's protected division, and
        // return a value of 1.0, with all derivatives equal to zero. This is equivalent to returning a constant value
        // of 1.0.
        double[] a2v = arg2.evaluateD(vars);
        if (a2v[0] == 0.0) return new Constant(1.0).evaluateD(vars);
        // not dividing by zero, so press on with calculating the derivatives
        double[] a1v = arg1.evaluateD(vars);

        res[0] = a1v[0] / a2v[0];

        // Quotient rule for each variable.
        for (int i = 0; i < n; i++) {
            res[i + 1] = ((a1v[i + 1] * a2v[0]) - (a1v[0] * a2v[i + 1]) / (a2v[0] * a2v[0]));
        }
        return res;
    }
}
