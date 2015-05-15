;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Released under the MIT license..
;

(ns algebolic.expression.score
  "Score functions for algebolic expressions."
  (:import (algebolic.expression Scores))
  (:require [algebolic.expression.tree :as tree]
            [algebolic.expression.interpreter :as interpreter]))

(defn abs-error
  "Calculates the summed abs error between the expression, evaluated at the coordinates, and the data. The vars are
  a list of symbols that represent the coordinates in the expression, in the order that they appear in the coords
  input."
  [vars coords data expr]
  (let [expr-vals (interpreter/evaluate expr vars coords)
        ;;_ (when (< (rand) 0.001) (print (count data)))
        ]
    (Scores/chiSquaredAbs expr-vals data)))

(defn abs-error-pp
  "An error with linear parsimony pressure. The same as `abs-error` but applies a penalty to the score
  proportional to the size of the expression. Uses the interpreter to evaluate the expressions."
  [vars coords data pp expr]
  (+ (abs-error vars coords data expr) (* pp (tree/count-nodes expr))))