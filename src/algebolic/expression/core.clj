;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.core
  "Algebolic expressions are Clojure data structures that represent mathematical expressions.
  They are defined in terms of a number of function symbols and terminal symbols, both defined
  in this namespace. Valid expressions are either a terminal symbol, or a vector whose `first` is
  a function symbol and whose `rest` is the appropriate number of arguments to that function, which
  themselves must be expressions.

  The expressions are not defined directly in terms of Clojure functions but rather in terms of
  keyword symbols. So, for instance the operation of addition is represented by :plus, not '+.
  In part this is to make the expressions more readable. It also eases multiple implementations
  of expression evaluation:

  There is a function `functionalise` for transforming expressions into Clojure functions that
  can be called. This is useful for turning the expressions into things you can plot etc. It lives
  in the `algebolic.expression.evaluate` namespace.

  There is also an `interpreter` namespace which provides an alternate (and much faster)
  mechanism for evaluating the expressions at a particular point. It is found in the
  `algebolic.expression.interpreter` namespace.

  The `score` namespace provides score functions that use both of these implementations."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random])
  (:require [clojure.walk :as walk]))

;; * Functions and terminals *

;; When you change the set of function symbols you have to make corresponding changes to a number of things:
;; - the Clojure evaluator mapping table (evaluate.clj)
;; - the interpreter (interpreter.clj)
;; - the differentiator (differentiation.clj)
;; - possibly the computer algebra code (algebra.clj)
;; - the Mathematica export code (mma.clj)
;; - the LaTex export code (render.clj)
(def function-symbols
  "The primitive set of functions that algebolic expressions can be built from."
  [{:name 0 :friendly-name :plus :arity 2}
   {:name 1 :friendly-name :minus :arity 2}
   {:name 2 :friendly-name :times :arity 2}
   {:name 3 :friendly-name :div :arity 2}
   {:name 4 :friendly-name :sin :arity 1}
   {:name 5 :friendly-name :cos :arity 1}])

(defn get-function-symbols
  "A helper for selecting a subset of the function symbols. Pass the names of the symbols you
  want (as keywords) in a set."
  [names]
  (filter #((:name %) names) function-symbols))

(defn make-terminals
  "The set of terminals that an expression can be built with. Takes a list of variables that
  will be included in the terminal set. Note that these variables need to be symbols (i.e. not
  keywords) as they will be used as function arguments when the expression is functionalised.
  An ephemeral random constant will be added to the terminals.

  Note that this function returns a list of functions that can be used to _generate_ a terminal.
  This allows easy implementation of ephemeral random constants. You probably will not need to
  worry about this, but this note hopefully avoids confusion."
  [vars & {:keys [constant-max]
           :or   {constant-max 2.0}}]
  (conj (map (fn [v] (constantly v)) vars)
        #(rand constant-max)))

;; * Expressions *

(defn non-terminal?
  "Is an expression a non-terminal? Doesn't check whether the function symbol is valid, or whether
  the arity is correct."
  [expr]
  (vector? expr))

(defn terminal?
  "Is an expression a terminal?"
  [expr]
  (not (vector? expr)))

(defn make-expression
  "A helper for constructing expression vectors. Makes it less painful if you want to change the way
  expressions are represented."
  [function arguments]
  (vec (cons function arguments)))

;; * Other common code *

;; Defined here because it has to be somewhere!
(defn pdiv
  "Protected division, which is division that doesn't blow up when the denominator is zero. Returns 1
  instead of undefined."
  [x y]
  (if (zero? y) 1 (/ x y)))