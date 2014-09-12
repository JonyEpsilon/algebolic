;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.expression.core
  "Algebolic expressions are Clojure data structures that represent mathematical expressions.
  They are defined in terms of a number of function symbols and terminal symbols, both defined
  in this namespace. The expressions are not defined directly in terms of Clojure functions
  but rather in terms of keyword symbols. This mainly is to make the expressions more readable.
  So, for instance the operation of addition is represented by :plus, not '+. There is a
  function for transforming expressions into Clojure functions that can be called."
  (:require [clojure.walk :as walk]))

(def function-symbols
  "The primitive set of functions that algebolic expressions can be built from."
  [{:name :plus :arity 2}
   {:name :minus :arity 2}
   {:name :times :arity 2}
   {:name :div :arity 2}
   {:name :sin :arity 1}
   {:name :cos :arity 1}])

(defn make-terminals
  "The set of terminals that an expression can be built with. Takes a list of variables that
  will be included in the terminal set. Note that these variables need to be symbols (i.e. not
  keywords) as they will be used as function arguments when the expression is functionalised.
  An ephemeral random constant will be added to the terminals.

  Note that this function returns a list of functions that can be used to _generate_ a terminal.
  This allows easy implementation of ephemeral random constants. You probably will not need to
  worry about this, but this note hopefully avoids confusion."
  [vars & {:keys [constant-max]
           :or   {constant-max 1.0}}]
  (conj (map (fn [v] (constantly v)) vars)
        #(rand constant-max)))

;; Defined here because it has to be somewhere!
(defn pdiv
  "Protected division, which is division that doesn't blow up when the denominator is zero. Returns 1
  instead of undefined."
  [x y]
  (if (zero? y) 1 (/ x y)))

(def default-implementation
  "The default implementation of the algebolic function symbols. Should correspond to what you'd think
  would be obvious, perhaps with the exception of div which used protected division."
  {:plus  '+
   :minus '-
   :times '*
   :div   'algebolic.expression.core/pdiv
   :sin   'Math/sin
   :cos   'Math/cos})

(defn implement
  "Takes an expression and an implementation and returns an equivalent expression with the function
  symbols implemented."
  [expr implementation]
  (walk/postwalk-replace implementation expr))

(defn functionalise
  "Takes an (implemented) expression and turns it into a function that can be called to get a value.
  The list of vars must be a quoted vector i.e. '[x y]"
  [ex vars]
  (eval (list 'fn vars (implement ex default-implementation))))

(def functionalise-m (memoize functionalise))