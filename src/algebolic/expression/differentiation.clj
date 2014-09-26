;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.differentiation
  "Symbolic differentiation of expressions."
  (:require [algebolic.expression.core :as expression]))

(declare d)

(defn- diff-expr
  [var expr]
  (case (first expr)
    :plus (expression/make-expression :plus (map (partial d var) (rest expr)))
    :minus (expression/make-expression :minus (map (partial d var) (rest expr)))
    :times [:plus [:times (d var (nth expr 1)) (nth expr 2)] [:times (nth expr 1) (d var (nth expr 2))]]
    :sin [:times (d var (nth expr 1)) [:cos (nth expr 1)]]
    :cos [:times (d var (nth expr 1)) [:times -1 [:sin (nth expr 1)]]]))

(defn d
  "Returns the partial derivative of `expr` with respect to `var`."
  [var expr]
  (cond
    (expression/non-terminal? expr) (diff-expr var expr)
    (number? expr) 0
    (symbol? expr) (if (= var expr) 1 0)))