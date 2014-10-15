;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.score
  "Score functions for algebolic expressions."
  (:require [algebolic.expression.core :as expression]
            [algebolic.expression.tree :as tree]
            [algebolic.expression.interpreter :as interpreter]))

(defn abs-error
  "Calculates the summed abs error between the expression and the data, given as [[[x1 x2] y] ...] pairs.
  Uses the interpreter to evaluate the expressions."
  [vars data expr]
  ;; the `long` below prevents crashes when the numbers are integers or rationals
  (let [expr-vals (interpreter/evaluate expr vars (map first data))]
    (apply + (map #(Math/abs (double (- %1 %2))) expr-vals (map second data)))))

(defn abs-error-pp
  "An error with linear parsimony pressure. The same as `abs-error` but applies a penalty to the score
  proportional to the size of the expression. Uses the interpreter to evaluate the expressions."
  [vars data pp expr]
  (+ (abs-error vars data expr) (* pp (tree/count-nodes expr))))