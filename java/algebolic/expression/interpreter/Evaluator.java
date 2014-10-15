// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.interpreter;

import java.util.ArrayList;
import java.util.List;

/* The evaluator takes a JExpr's, which are the interpreter's internal format for an algebolic expression, and
 * evaluates them. It does this pretty fast!
 */
public class Evaluator {

    /* Evaluate the value of a JExpr at a set of coordinates. The coordinates are given as a nested list of
     * Doubles.
     */
    public static List<Double> evaluateList(JExpr expr, List<List<Double>> coords) {
        List<Double> result = new ArrayList<Double>(coords.size());
        for (List<Double> c : coords) {
            result.add(expr.evaluate(c));
        }
        return result;
    }
}
