;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.differentiation
  "Symbolic differentiation of expressions."
  (:require [algebolic.expression.core :as expression]
            [algebolic.expression.algebra :as algebra]
            [clojure.core.match :as match]))

(declare d-internal)

(defn contains-variable?
  "Does an expression contain the given variable? Recursively checks all sub-expressions. Note that this is a
  structural check, and doesn't mean that the value of the expression depends on the value of the variable, just
  that it might (consider `[:minus 'x 'x]`, for instance)."
  [var expr]
  (if (expression/terminal? expr)
    (= expr var)
    (true? (some #(= % true) (map (partial contains-variable? var) (rest expr))))))

(defn- diff-product
  "Differentiates an expression of the form `[:times a b]` with respect to var. Checks directly if the
  factors depend on the variable, and short-circuits appropriately. This leads to significantly smaller
  resulting expressions for deeply-nested input to `d`."
  [var [_ a b]]
  (let [a-dep (contains-variable? var a)
        b-dep (contains-variable? var b)]
    (match/match [[a-dep b-dep]]
      ;; expression doesn't depend on the var
      [[false false]] 0
      ;; only one of the factors depends on the var
      [[false true]] [:times a (d-internal var b)]
      [[true false]] [:times (d-internal var a) b]
      ;; both factors depend on the var - chain rule
      [[true true]] [:plus
                     [:times (d-internal var a) b]
                     [:times a (d-internal var b)]])))

(defn- diff-non-terminal
  "Returns the partial derivative of non-terminal `expr` with respect to `var`."
  [var expr]
  (case (first expr)
    :plus (expression/make-expression :plus (map (partial d-internal var) (rest expr)))
    :minus (expression/make-expression :minus (map (partial d-internal var) (rest expr)))
    :times (diff-product var expr)
    :sin [:times (d-internal var (nth expr 1)) [:cos (nth expr 1)]]
    :cos [:times (d-internal var (nth expr 1)) [:times -1 [:sin (nth expr 1)]]]))

(defn- d-internal
  "Internal recursive target. Allows transformations to be applied per sub-expression, or overall (in d)."
  [var expr]
  (cond
    (expression/non-terminal? expr) (diff-non-terminal var expr)
    (number? expr) 0
    (symbol? expr) (if (= var expr) 1 0)))


(defn- flatten-identities
  "Simplifies expressions that multiply by 1 or 0, or add 0. These types of expression are generated often
  by the (naive) differentiator."
  [expr]
  (match/match [expr]
    [[:times 0 _]] 0
    [[:times _ 0]] 0
    [[:times 1 a]] a
    [[:times a 1]] a
    [[:plus 0 a]] a
    [[:plus a 0]] a
    [a] a))

(defn- simplify-derivative
  "Clean up the output of the differentiator, by removing obviously dopey expressions."
  [expr]
  (algebra/operate-full flatten-identities expr))

(defn d
  "Returns the partial derivative of `expr` with respect to `var`."
  [var expr]
  (simplify-derivative (d-internal var expr)))