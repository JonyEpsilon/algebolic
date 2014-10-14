// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

public class Var implements JExpr {

    private int index;

    public Var(int index) {
        this.index = index;
    }

    public double evaluate(double[] vars) {
        return vars[index];
    }
}
