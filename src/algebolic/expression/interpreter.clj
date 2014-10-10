;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.interpreter
  "Provides an interpreter for algebolic expressions. This can be used to get the
  value of an expression for particular values of the variables. It is much faster
  for most use cases than compiling the expression to Clojure and evaluating it as
  a function."
  (:require [algebolic.expression.core :as expression]))

(defn evaluate
  "Evaluate the given expression at the given value of the variables. The variables should
  be specified as a map from symbol to value i.e. `{'x 3 'y 4}`."
  [expr vars]
  ;; TODO: if I knew any computer science I could probably re-write this as a stack based interpreter
  ;; TODO: or something similar that was less abusive to the Java stack (and probably faster).
  (cond
    (number? expr) expr
    (symbol? expr) (expr vars)
    ;; this is a performance optimisation. In principle we would be testing here whether we are dealing
    ;; with a non-terminal expression. But, because it's neither of the above it must be, so skip the test.
    true (let [opcode (nth expr 0)]
           (cond
             (= opcode 0) (+ (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
             (= opcode 1) (- (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
             (= opcode 2) (* (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
             (= opcode 3) (expression/pdiv (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
             (= opcode 4) (Math/sin (evaluate (nth expr 1) vars))
             (= opcode 5) (Math/cos (evaluate (nth expr 1) vars))))))