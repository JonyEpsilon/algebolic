// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

import java.util.List;

/* Interface for classes used in the interpreter's internal representation of algebolic expressions. */
public interface JExpr {
    public double evaluate(List<Double> vars);
}
