// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

public abstract class UnaryExpr extends JExpr {

    protected final JExpr arg1;

    protected UnaryExpr(JExpr arg1) {
        this.arg1 = arg1;
    }

    @Override
    public int size() {
        return 1 + arg1.size();
    }
}
