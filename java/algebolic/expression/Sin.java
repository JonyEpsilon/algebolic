// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.List;

public class Sin extends UnaryExpression {

    public Sin(JExpr arg1) {
        super(arg1);
    }

    @Override
    public double evaluate(List<Double> vars) {
        return Math.sin(arg1.evaluate(vars));
    }
}
