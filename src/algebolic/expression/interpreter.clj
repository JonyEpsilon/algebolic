;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.interpreter
  "Provides an interpreter for algebolic expressions. This can be used to get the value of an expression for particular
  values of the variables. It is much faster for most use cases than compiling the expression with the  Clojure compiler
  and evaluating it as a function."
  (:require [algebolic.expression.core :as expression]))

(defn evaluate
  "Evaluate the given expression at the given value of the variables. The variables should
  be specified as a map from symbol to value i.e. `{'x 3 'y 4}`."
  [expr vars]
  (cond
    (symbol? expr) (expr vars)
    (number? expr) expr
    true (case (nth expr 0)
           :plus (+ (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
           :minus (- (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
           :times (* (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
           :div (expression/pdiv (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
           :sin (Math/sin (evaluate (nth expr 1) vars))
           :cos (Math/cos (evaluate (nth expr 1) vars)))))