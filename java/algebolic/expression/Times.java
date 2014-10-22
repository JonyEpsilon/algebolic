// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.ArrayList;
import java.util.List;

public class Times extends BinaryExpr {

    public Times(JExpr arg1, JExpr arg2) {
        super(arg1, arg2);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return arg1.evaluate(vars) * arg2.evaluate(vars);
    }

    @Override
    public List<Double> evaluateD(List<Double> vars) {
        int n = vars.size();
        List<Double> res = new ArrayList<Double>(n + 1);

        List<Double> a1v = arg1.evaluateD(vars);
        List<Double> a2v = arg2.evaluateD(vars);

        res.add(0, a1v.get(0) * a2v.get(0));

        // Product rule for each variable.
        for (int i = 0; i < n; i++) {
            res.add(i + 1, (a1v.get(0) * a2v.get(i + 1)) + (a1v.get(i + 1) * a2v.get(0)));
        }
        return res;
    }
}
