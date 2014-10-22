// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.ArrayList;
import java.util.List;

public class Cos extends UnaryExpression {

    public Cos(JExpr arg1) {
        super(arg1);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return Math.cos(arg1.evaluate(vars));
    }

    @Override
    public List<Double> evaluateD(List<Double> vars) {
        int n = vars.size();
        List<Double> res = new ArrayList<Double>(n + 1);

        List<Double> a1v = arg1.evaluateD(vars);

        res.add(0, Math.cos(a1v.get(0)));

        // Calculate the derivative using the chain rule.
        double s = -1.0 * Math.sin(a1v.get(0));
        for (int i = 0; i < n; i++) {
            res.add(i + 1, a1v.get(i + 1) * s);
        }
        return res;
    }
}
