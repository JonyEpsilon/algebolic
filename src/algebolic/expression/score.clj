;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.expression.score
  "Score functions for algebolic expressions."
  (:require [algebolic.expression.core :as expression]
            [algebolic.expression.tree :as tree]))

(defn size
  "The number of nodes in an expression."
  [expr]
  (tree/count-nodes expr))

(defn abs-error
  "Calculates the summed abs error between the expression and the data, given as [[[x1 x2] y] ...] pairs.
  For the moment, the default implementation is hard-coded, but this could be easily passed in as a parameter."
  [vars data expr]
  (let [f (expression/functionalise expr vars)]
    ;; the `double` below prevents crashes when the numbers are integers or rationals
    (* -1 (apply + (map #(Math/abs (double (- (apply f (first %)) (second %)))) data)))))

(defn abs-error-pp
  "An error with linear parsimony pressure. The same as `abs-error` but applies a penalty to the score
  proportional to the size of the expression."
  [vars data pp expr]
  (- (abs-error vars data expr) (* pp (size expr))))