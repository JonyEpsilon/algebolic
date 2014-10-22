// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Internal representation of an algebolic expression for the Java interpreter. */
public abstract class JExpr {

    /* Evaluate the expression for the given variable values. */
    public abstract double evaluate(List<Double> vars);

    /* Evaluate the expression for the given variable values. */
    public abstract double[] evaluateD(List<Double> vars);

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
            // We compute the value and derivatives using double[] for efficiency, but here we pack the results into
            // ArrayLists for smoother interop with Clojure.
            double [] vals = evaluateD(c);
            List<Double> dat = new ArrayList<Double>(vals.length);
            for (double v : vals) dat.add(v);
            result.add(dat);
        }
        return result;
    }
}
