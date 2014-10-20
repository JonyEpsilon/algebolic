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

}
