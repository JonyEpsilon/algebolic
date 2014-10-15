// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

import java.util.List;

public class Var implements JExpr {

    private int index;

    public Var(int index) {
        this.index = index;
    }

    public double evaluate(List<Double> vars) {
        return vars.get(index);
    }
}
