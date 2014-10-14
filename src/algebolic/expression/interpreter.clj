;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.interpreter
  "Provides a fast interpreter for algebolic expressions."
  (:import [algebolic.expression.interpreter Plus Times Var Constant JExpr])
  (:require [algebolic.expression.core :as expression]))

(defn- ->jexpr
  [expr]
  (cond
    (number? expr) (Constant. expr)
    true (case (nth expr 0)
           :plus (Plus. (->jexpr (nth expr 1)) (->jexpr (nth expr 2)))
           :times (Plus. (->jexpr (nth expr 1)) (->jexpr (nth expr 2))))))

(defn evaluate
  [expr vars]
  )