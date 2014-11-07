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

import java.util.ArrayList;
import java.util.List;

/* Internal representation of an algebolic expression for the Java interpreter. */
public abstract class JExpr {

    // These fields and this constructor are performance optimisations. We pass in the number of variables to the
    // constructor, and pre-allocate the array that the results of evaluateD will be computed and returned in.
    // Allocating this array here means that no array allocations are needed when computing the derivatives. It is
    // also possible that having numVars be a final field would allow the compiler to unroll the loops in the
    // derivative calculation, but I have not tested this. Overall, doing it this way approximately halves the
    // evaluation time.
    //
    // Note very well that the _same_ array object is returned each time you call evaluateD, just with different values
    // in it. So you must be very careful if you hold a reference to it. If you use evaluateDList then you will not
    // have to worry about this, as the array will be copied before it is returned to you.
    //
    // Note also that, as a result of these optimisations, evaluateD and evaluateDList are not thread-safe. If you use
    // the expressions in the way that the algebolic interpreter does, which is creating an expression for each
    // evaluation, then this will not be a problem.
    protected final int numVars;
    protected final double[] dRes;
    protected JExpr(int numVars) {
        this.numVars = numVars;
        this.dRes = new double[numVars + 1];
    }

    /* Evaluate the expression for the given variable values. */
    public abstract double evaluate(List<Double> vars);

    /* Evaluate the expression and its first derivatives for the given variable values. Note well that this function
     * always returns the same array, just with different values in it. See the note at the top of this class for more
     * details. You will most likely want to make a copy of the returned array if you need to hold on to it.
     *
     * This is done for you if you use evaluateDList, which is the preferred way of evaluating expressions. Note also,
     * as described above, that this function is not thread-safe. */
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
    * of the first derivatives of each variable, in index order.
    *
    * Note that this method is not thread-safe.*/
    public List<List<Double>> evaluateDList(List<List<Double>> coords) {
        List<List<Double>> result = new ArrayList<List<Double>>(coords.size());
        for (List<Double> c : coords) {
            // We compute the value and derivatives using double[] for efficiency, but here we pack the results into
            // ArrayLists for smoother interop with Clojure. This is also where we copy the results out of the array
            // returned by evaluate.
            double [] vals = evaluateD(c);
            List<Double> dat = new ArrayList<Double>(vals.length);
            for (double v : vals) dat.add(v);
            result.add(dat);
        }
        return result;
    }
}
