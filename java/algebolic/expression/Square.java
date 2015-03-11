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

public class Square extends UnaryExpr {

    public Square(JExpr arg1, int numVars) {
        super(arg1, numVars);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return Math.pow(arg1.evaluate(vars), 2);
    }

    @Override
    public double[] evaluateD(List<Double> vars) {

        double[] a1v = arg1.evaluateD(vars);

        dRes[0] = Math.pow(a1v[0], 2);

        double v = 2.0 * a1v[0];
        for (int i = 0; i < numVars; i++) {
            dRes[i + 1] = v * a1v[i + 1];
        }
        return dRes;
    }
}
