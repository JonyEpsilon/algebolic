// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

public abstract class BinaryExpr extends JExpr {

    protected final JExpr arg1;
    protected final JExpr arg2;

    public BinaryExpr(JExpr arg1, JExpr arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public int size() {
        return 1 + arg1.size() + arg2.size();
    }

}
