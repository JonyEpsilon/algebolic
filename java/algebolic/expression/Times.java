// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

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
    public JExpr copy() {
        return new Times(arg1.copy(), arg2.copy());
    }

}
