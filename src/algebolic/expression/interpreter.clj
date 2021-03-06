;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Released under the MIT license..
;

(ns algebolic.expression.interpreter
  "Provides a fast interpreter for algebolic expressions.

  The core of the interpreter is implemented in Java, for speed. It transforms expressions to its own internal
  representation - nested Java objects - and then evaluates them. For efficiency, the interpreter takes a list of
  variable values at which to evaluate the expression, and returns a list of expression values. This is because
  transforming the expression to the interpreter's internal format has a non-trivial fixed cost which is best amortized
  over a whole set of values.

  The core of the interpreter is implemented in Java because I found it difficult to make a Clojure interpreter
  as fast. This was after many tests of different approaches in Clojure. I think the difficulty of making it
  as fast in Clojure comes from the fact the interpreter naturally has a deeply-nested recursive call graph,
  with methods that do very little. So even a small amount of overhead on calls and dispatch becomes
  significant. The closest I was able to get to the Java performace was using a protocol and type approach,
  but it was still a (small) few times slower. I suspect this was down to primitive type boxing/unboxing on
  the function calls, but I'm not sure, as that is typically very fast on the JVM."
  (:import [algebolic.expression Plus Times Var Constant JExpr Minus Cos Sin Divide Square]
           (clojure.lang APersistentVector))
  (:require [algebolic.expression.core :as expression]))

(defn ->jexpr
  "Converts an algebolic expression to the internal format used by the Java interpreter, which is a nested set
  of objects. `var-list` is the list of variable symbols in the expression e.g. `['x 'y]`. The order of this list
  determines the index that will correspond to that var in the JExpr. So, in the example given, `'x` would be
  translated to `Var(0)` and `'y` to `Var(1)`."
  ;; This type hint is critical, otherwise reflection on the indexOf call dominates the runtime.
  [^APersistentVector var-list expr]
  ;; Using `count` would seem more natural here, but it adds significantly to the runtime in a way that I couldn't
  ;; seem to eliminate.
  (let [numVars (.length var-list)]
    (cond
      (symbol? expr) (Var. (.indexOf var-list expr) numVars)
      (number? expr) (Constant. expr numVars)
      true (case (nth expr 0)
             :plus (Plus. (->jexpr var-list (nth expr 1)) (->jexpr var-list (nth expr 2)) numVars)
             :minus (Minus. (->jexpr var-list (nth expr 1)) (->jexpr var-list (nth expr 2)) numVars)
             :times (Times. (->jexpr var-list (nth expr 1)) (->jexpr var-list (nth expr 2)) numVars)
             :div (Divide. (->jexpr var-list (nth expr 1)) (->jexpr var-list (nth expr 2)) numVars)
             :sin (Sin. (->jexpr var-list (nth expr 1)) numVars)
             :cos (Cos. (->jexpr var-list (nth expr 1)) numVars)
             :square (Square. (->jexpr var-list (nth expr 1)) numVars)
             ))))

(defn ^Double evaluate
  "Evaluates the given expression at the given coordinates. `vars` is a vector of the variables in the expression, for
  example `['x 'y]`. `coords` is the values of the variables at which to evaluate the expression. It should be in the
  format `[[x1 y1] [x2 y2] ...]`, where the variables are given in the order specified in `vars`. The function returns
  an ArrayList of the expression values `[v1 v2 v3]`."
  [expr vars coords]
  (let [jexpr ^JExpr (->jexpr vars expr)]
    (.evaluateList jexpr coords)))

(defn evaluate-d
  "Evaluates the given expression and its first derivatives, at the given coordinates. `vars` is a vector of the
  variables in the expression, for example `['x 'y]`. `coords` is the values of the variables at which to evaluate
  the expression. It should be in the format `[[x1 y1] [x2 y2] ...]`, where the variables are given in the order
  specified in `vars`. The function returns an ArrayList of ArrayList. The inner lists contain the function
  value, followed by the first derivatives with respect to each variable, in the order given in `vars`. The outer
  list contains these values for each point in `coords`."
  [expr vars coords]
  (let [jexpr ^JExpr (->jexpr vars expr)]
    (.evaluateDList jexpr coords)))
