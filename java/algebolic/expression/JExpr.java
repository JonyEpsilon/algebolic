// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.ArrayList;
import java.util.List;

/* Internal representation of an algebolic expression for the Java interpreter. */
public abstract class JExpr {

    /* Evaluate the expression for the given variable values. */
    public abstract double evaluate(List<Double> vars);

    /* Evaluate the expression for the given variable values. */
    public abstract List<Double> evaluateD(List<Double> vars);

    /* The number of nodes this expression has. Currently unused. */
    public abstract int size();

    /* Evaluate the value of a JExpr at a set of coordinates. The coordinates are given as a nested list of Doubles. */
    public List<Double> evaluateList(List<List<Double>> coords) {
        List<Double> result = new ArrayList<Double>(coords.size());
        for (List<Double> c : coords) {
            result.add(this.evaluate(c));
        }
        return result;
    }

    /* Evaluate the value of a JExpr at a set of coordinates. The coordinates are given as a nested list of Doubles.
    * The function returns a list of lists where each inner list is the value of the function, followed by the value
    * of the first derivatives of each variable, in index order.*/
    public List<List<Double>> evaluateDList(List<List<Double>> coords) {
        List<List<Double>> result = new ArrayList<List<Double>>(coords.size());
        for (List<Double> c : coords) {
            result.add(this.evaluateD(c));
        }
        return result;
    }
}
