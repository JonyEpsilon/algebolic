;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.expression.core
  "Algebolic expressions are Clojure data structures that represent mathematical expressions.
  They are defined in terms of a number of function symbols and terminal symbols, both defined
  in this namespace. The expressions are not defined directly in terms of Clojure functions
  but rather in terms of keyword symbols. So, for instance the operation of addition is
  represented by :plus, not '+. This level of indirection is provided so that alternate
  implementations of the functions can be provided. This allows, for instance, automatic
  differentiation to be implemented as an alternate implementation of the symbols. This
  namespace has a default implementation of the symbols in terms of the obvious Clojure and
  java functions. It also has helpers for implementing and transforming expressions into
  functions that can be called."
  (:require [clojure.walk :as walk]))

(def function-symbols
  "The primitive set of functions that algebolic expressions can be built from."
  ;; TODO: should this be made user extensible?
  [{:name :plus :arity 2}
   {:name :minus :arity 2}
   {:name :times :arity 2}
   {:name :div :arity 2}
   {:name :sin :arity 1}
   {:name :cos :arity 1}])

(defn terminals
  "The set of terminals that an expression can be built with. Takes a list of variables that
  will be included in the terminal set. Note that these variables need to be symbols (i.e. not
  keywords) as they will be used as function arguments when the expression is functionalised.
  The symbol ::constant will be added to the vars, and it represents a placeholder for a constant
  in the expression. This placeholder could be filled at a later stage with an epehemeral random
  constant, or perhaps a symbolic constant etc."
  [vars]
  (conj vars ::constant))

;; Defined here because it has to be somewhere!
(defn pdiv
  "Protected division, that is division that doesn't blow up when the denominator is zero. Returns 1
  instead of undefined."
  [x y]
  (if (zero? y) 1 (/ x y)))

(def default-implementation
  "The default implementation of the algebolic function symbols. Should correspond to what you'd think
  would be obvious, perhaps with the exception of div which used protected division."
  {:plus  '+
   :minus '-
   :times '*
   :div   'pdiv
   :sin   'Math/sin
   :cos   'Math/cos})

(defn make-random-constants
  "Replaces ::constant placeholders in an expression with random reals. An optional :range can be
  specified. The constants will be drawn from 0 to :range, which defaults to 1.0."
  [expr & {:keys [range]
           :or [range 1.0]}]
  (walk/postwalk (fn [e] (if (= ::constant e) (rand range) e)) expr))

(defn implement
  "Takes an expression and an implementation and returns an equivalent expression with the function
  symbols implemented."
  [expr implementation]
  (walk/postwalk-replace implementation expr))

(defn functionalise
  "Takes an (implemented) expression and turns it into a function that can be called to get a value.
  The list of vars must be a quoted vector i.e. '[x y]"
  [ex vars]
  (eval (list 'fn vars ex)))