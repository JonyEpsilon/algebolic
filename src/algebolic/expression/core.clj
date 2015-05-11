;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(ns algebolic.expression.core
  "Algebolic expressions are Clojure data structures that represent mathematical expressions.
  They are trees built from a set of possible functions (non-leaf nodes) and terminals (leaf nodes). Terminals are
  either Clojure symbols, representing a variable (i.e. `'x`) or numbers, representing a constant. Valid expressions
  are either a terminal, or a vector whose `first` is a function name and whose `rest` is the appropriate number
  of arguments to that function, which themselves must be expressions. Function names are represented as Clojure
  keywords.

  Algebolic expressions are not used to build Clojure functions, as in classic LISP symbolic regression,
  rather they are fed to a fast interpreter which calculates their value. This interpreter is written
  in Java and optimised for performance, as expression evaluation is a significant part of the run-time
  of a regression run."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random])
  (:require [clojure.walk :as walk]))

;; * Functions and terminals *

;; When you change the function set you have to make corresponding changes to a number of things:
;; - the interpreter (interpreter.clj and the Java code in `algebolic.expression`)
;; - the fast node count and depth count code (tree.clj)
;; - the Mathematica export code (mma.clj)
;; - the LaTex export code (render.clj)
(def ^:private function-set
  "The primitive set of functions that algebolic expressions can be built from."
  [{:name :plus :arity 2}
   {:name :minus :arity 2}
   {:name :times :arity 2}
   {:name :div :arity 2}
   {:name :sin :arity 1}
   {:name :cos :arity 1}
   {:name :square :arity 1}])

(defn get-functions
  "A helper for selecting a subset of the function set. Pass the names of the symbols you want (as keywords) in a set."
  [names]
  (filter #((:name %) names) function-set))

(defn terminal-generators
  "The set of terminals that an expression can be built with. Takes a list of variables that
  will be included in the terminal set. Note that these variables need to be symbols (i.e. not
  keywords). An ephemeral random constant will be added to the terminals.

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