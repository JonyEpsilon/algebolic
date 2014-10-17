// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;


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
    public JExpr copy() {
        return new Var(index);
    }

}
