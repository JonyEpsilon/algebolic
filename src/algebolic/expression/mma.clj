;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(ns algebolic.expression.mma
  "Some functions for using Mathematica with algebolic expressions."
  (:require [algebolic.expression.core :as expression]))

(defn fullform
  "Transform an algebolic expression to a string in Mathematica format. Useful for copying expressions
  into Mathematica when you're stuck!"
  [expr]
  (cond
    (expression/non-terminal? expr) (case (first expr)
                                      :plus (str "Plus[" (fullform (nth expr 1)) ", "
                                                 (fullform (nth expr 2)) "]")
                                      :minus (str "Subtract[" (fullform (nth expr 1)) ", "
                                                  (fullform (nth expr 2)) "]")
                                      :times (str "Times[" (fullform (nth expr 1)) ", "
                                                  (fullform (nth expr 2)) "]")
                                      :div (str "Divide[" (fullform (nth expr 1)) ", "
                                                  (fullform (nth expr 2)) "]")
                                      :sin (str "Sin[" (fullform (nth expr 1)) "]")
                                      :cos (str "Cos[" (fullform (nth expr 1)) "]")
                                      :square (str "Power[" (fullform (nth expr 1)) ", 2]")
                                      ;; make sure we notice if something has gone wrong
                                      (println "Throw[\"Unknown expression: " expr "\"]"))
    (symbol? expr) (str expr)
    (number? expr) (str expr)))