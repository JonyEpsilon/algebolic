;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.evaluate
  "Evaluating algebolic expressions as Clojure code."
  (:require [clojure.walk :as walk]))

(def ^:private mapping-to-clojure
  "The default implementation of the algebolic function symbols. Should correspond to what you'd think
  would be obvious, perhaps with the exception of div which used protected division."
  {:plus  '+
   :minus '-
   :times '*
   :div   'algebolic.expression.core/pdiv
   :sin   'Math/sin
   :cos   'Math/cos})

(defn- to-clojure-symbols
  "Takes an expression and an implementation and returns an equivalent expression with the function
  symbols implemented."
  [expr]
  (walk/postwalk-replace mapping-to-clojure expr))

(defn- to-sexp
  "Takes an expression represented as nested vectors, and transforms it into nested sequences."
  [expr]
  (walk/postwalk (fn [s] (if (vector? s) (seq s) s)) expr))

(defn functionalise
  "Takes an (implemented) expression and turns it into a function that can be called to get a value.
  The list of vars must be a quoted vector i.e. '[x y].

  Note that this invokes the Clojure compiler with `eval` and thus is relatively slow (around a ms).
  This function is useful for making functions you can plot etc, but you probably don't want to use
  it in a loop to evaluate fitness or the like. See the `interpreter` namespace for a better way to
  do that."
  [ex vars]
  (eval (list 'fn vars (to-clojure-symbols (to-sexp ex)))))