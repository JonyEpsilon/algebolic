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

(defn evaluate-with-d
  [expr vars d-var]
  (cond
    (symbol? expr) [(expr vars) (if (= expr d-var) 1 0)]
    (number? expr) [expr 0]
    true (case (nth expr 0)
           :plus (let [arg1 (evaluate-with-d (nth expr 1) vars d-var)
                       arg2 (evaluate-with-d (nth expr 2) vars d-var)]
                   [(+ (nth arg1 0) (nth arg2 0)) (+ (nth arg1 1) (nth arg2 1))])
           :minus (let [arg1 (evaluate-with-d (nth expr 1) vars d-var)
                       arg2 (evaluate-with-d (nth expr 2) vars d-var)]
                   [(- (nth arg1 0) (nth arg2 0)) (- (nth arg1 1) (nth arg2 1))])
           :times (let [arg1 (evaluate-with-d (nth expr 1) vars d-var)
                        arg2 (evaluate-with-d (nth expr 2) vars d-var)]
                    [(* (nth arg1 0) (nth arg2 0)) (+ (* (nth arg1 0) (nth arg2 1)) (* (nth arg1 1) (nth arg2 0)))])
           :sin (let [arg (evaluate-with-d (nth expr 1) vars d-var)]
                  [(Math/sin (nth arg 0)) (* (nth arg 1) (Math/cos (nth arg 0)))])
           :cos (let [arg (evaluate-with-d (nth expr 1) vars d-var)]
                  [(Math/cos (nth arg 0)) (* -1.0 (nth arg 1) (Math/sin (nth arg 0)))])
           )))

(defn evaluate-d
  [expr var d-var]
  (nth (evaluate-with-d expr var d-var) 1))