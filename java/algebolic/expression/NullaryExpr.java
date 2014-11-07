/*
 * This file is part of algebolic.
 *
 * Copyright (C) 2014-, Imperial College, London, All rights reserved.
 *
 * Contributors: Jony Hudson
 *
 * Not for distribution.
 */

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
