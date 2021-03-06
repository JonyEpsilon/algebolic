/*
 * This file is part of algebolic.
 *
 * Copyright (C) 2014-, Imperial College, London, All rights reserved.
 *
 * Contributors: Jony Hudson
 *
 * Released under the MIT license..
 */

package algebolic.expression;

public abstract class UnaryExpr extends JExpr {

    protected final JExpr arg1;

    protected UnaryExpr(JExpr arg1, int numVars) {
        super(numVars);
        this.arg1 = arg1;
    }

    @Override
    public int size() {
        return 1 + arg1.size();
    }
}
