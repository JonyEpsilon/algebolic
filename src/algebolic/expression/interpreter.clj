;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.interpreter
  "Provides a fast interpreter for algebolic expressions.

  The core of the interpreter is implemented in Java, for speed. It transforms expressions to its own internal
  representation - nested Java classes - and then evaluates them.

  The core of the interpreter is implemented in Java because I found it difficult to make a Clojure interpreter
  as fast. This was after many tests of different approaches in Clojure. I think the difficulty of making it
  as fast in Clojure comes from the fact the interpreter naturally has a deeply-nested recursive call graph,
  with methods that do very little. So even a small amount of overhead on calls and dispatch becomes
  significant. The closest I was able to get to the Java performace was using a protocol and type approach,
  but it was still a (small) few times slower. I suspect this was down to primitive type boxing/unboxing on
  the function calls."
  (:import [algebolic.expression.interpreter Plus Times Var Constant JExpr])
  (:require [algebolic.expression.core :as expression]))

(defn ->jexpr
  [expr]
  (cond
    (number? expr) (Constant. expr)
    true (case (nth expr 0)
           :plus (Plus. (->jexpr (nth expr 1)) (->jexpr (nth expr 2)))
           :times (Times. (->jexpr (nth expr 1)) (->jexpr (nth expr 2))))))

(defn evaluate
  [expr vars coords]
  )