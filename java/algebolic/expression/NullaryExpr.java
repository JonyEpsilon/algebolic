// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

public abstract class NullaryExpr extends JExpr {

    protected NullaryExpr(int numVars) {
        super(numVars);
    }

    @Override
    public int size() {
        return 1;
    }
}
