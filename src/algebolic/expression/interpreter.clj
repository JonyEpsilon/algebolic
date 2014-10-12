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
  "Evaluate the given expression at the given value of the variables."
  [expr vars]
    (case (nth expr 0)
      :plus (+ (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
      :minus (- (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
      :times (* (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
      :div (expression/pdiv (evaluate (nth expr 1) vars) (evaluate (nth expr 2) vars))
      :sin (Math/sin (evaluate (nth expr 1) vars))
      :cos (Math/cos (evaluate (nth expr 1) vars))
      :const (nth expr 1)
      :var (nth vars (nth expr 1))))

(defrecord Instruction [^Integer op ^Double arg1 ^Double arg2])

(defn itr
  ([op arg1] (->Instruction op arg1 0.0))
  ([op arg1 arg2] (->Instruction op arg1 arg2)))

(defn ^Double evaluate-rec
  "Evaluate the given expression at the given value of the variables."
  [^Instruction expr ^doubles vars]
  (case (:op expr)
    0 (+ (evaluate-rec (:arg1 expr) vars) (evaluate-rec (:arg2 expr) vars))
    1 (- (evaluate-rec (:arg1 expr) vars) (evaluate-rec (:arg2 expr) vars))
    2 (* (evaluate-rec (:arg1 expr) vars) (evaluate-rec (:arg2 expr) vars))
    3 (expression/pdiv (evaluate-rec (:arg1 expr) vars) (evaluate-rec (:arg2 expr) vars))
    4 (Math/sin (evaluate-rec (:arg1 expr) vars))
    5 (Math/cos (evaluate-rec (:arg1 expr) vars))
    6 (:arg1 expr)
    7 (nth vars (:arg1 expr))))