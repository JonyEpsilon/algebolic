// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;


import java.util.List;

public class Times implements JExpr {
    private JExpr arg1;
    private JExpr arg2;

    public Times(JExpr arg1, JExpr arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public double evaluate(List<Double> vars) {
        return arg1.evaluate(vars) * arg2.evaluate(vars);
    }
}
