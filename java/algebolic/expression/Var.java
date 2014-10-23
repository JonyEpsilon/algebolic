// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.List;

public class Var extends NullaryExpr {

    private final int index;

    public Var(int index, int numVars) {
        super(numVars);
        this.index = index;
    }

    @Override
    public double evaluate(List<Double> vars) {
        return vars.get(index);
    }

    @Override
    public double[] evaluateD(List<Double> vars) {

        dRes[0] = vars.get(index);

        // The derivative of a variable is one with respect to itself, and zero with respect to any other variable.
        for (int i = 0; i < numVars; i++) {
            if (i == index) dRes[i + 1] = 1.0;
            else dRes[i + 1] = 0.0;
        }
        return dRes;
    }
}
