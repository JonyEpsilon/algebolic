// This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
//
// Not for distribution.

package algebolic.expression.score;

import algebolic.expression.JExpr;

import java.util.ArrayList;
import java.util.List;

public class Scores {


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

    public static double chiSquaredAbs(List<Double> a, List<Double> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += Math.abs(a.get(i) - b.get(i));
        }
        return sum;
    }
}
