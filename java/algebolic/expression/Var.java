// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.ArrayList;
import java.util.List;

public class Var extends NullaryExpr {

    private final int index;

    public Var(int index) {
        this.index = index;
    }

    @Override
    public double evaluate(List<Double> vars) {
        return vars.get(index);
    }

    @Override
    public List<Double> evaluateD(List<Double> vars) {
        int n = vars.size();
        List<Double> res = new ArrayList<Double>(n + 1);

        res.add(0, vars.get(index));

        // The derivative of a variable is one with respect to itself, and zero with respect to any other variable.
        for (int i = 0; i < n; i++) {
            double v = 0.0;
            if (i == index) v = 1.0;
            res.add(i + 1, v);
        }
        return res;
    }
}
