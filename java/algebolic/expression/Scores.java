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

import java.util.List;

/* Helper functions for scoring. These are performance critical, so implemented in Java. They are wrapped by the
 * Clojure functions in `algebolic.expression.interpreter`. */
public class Scores {

    public static double chiSquaredAbs(List<Double> a, List<Double> b) {
        double sum = 0.0;
        for (int i = 0; i < a.size(); i++) {
            sum += Math.abs(a.get(i) - b.get(i));
        }
        return sum;
    }
}
