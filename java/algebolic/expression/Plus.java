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

public class Plus extends BinaryExpr {

    public Plus(JExpr arg1, JExpr arg2, int numVars) {
        super(arg1, arg2, numVars);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return arg1.evaluate(vars) + arg2.evaluate(vars);
    }

    @Override
    public double[] evaluateD(List<Double> vars) {

        double[] a1v = arg1.evaluateD(vars);
        double[] a2v = arg2.evaluateD(vars);

        dRes[0] = a1v[0] + a2v[0];

        // The derivative of a sum w.r.t some variable is the sum of the derivatives of the terms w.r.t that variable.
        for (int i = 0; i < numVars; i++) {
            dRes[i + 1] = a1v[i + 1] + a2v[i + 1];
        }
        return dRes;
    }
}
