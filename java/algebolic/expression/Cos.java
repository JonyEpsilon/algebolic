/*
 * This file is part of algebolic.
 *
 * Copyright (C) 2014-, Imperial College, London, All rights reserved.
 *
 * Contributors: Jony Hudson
 *
 * Not for distribution.
 */

package algebolic.expression;

import java.util.List;

public class Cos extends UnaryExpr {

    public Cos(JExpr arg1, int numVars) {
        super(arg1, numVars);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return Math.cos(arg1.evaluate(vars));
    }

    @Override
    public double[] evaluateD(List<Double> vars) {

        double[] a1v = arg1.evaluateD(vars);

        dRes[0] = Math.cos(a1v[0]);

        // Calculate the derivative using the chain rule.
        double s = -1.0 * Math.sin(a1v[0]);
        for (int i = 0; i < numVars; i++) {
            dRes[i + 1] = a1v[i + 1] * s;
        }
        return dRes;
    }
}
