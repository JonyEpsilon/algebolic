;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.algebra
  "This namespace implements some basic computer algebra operations on algebolic expressions. And by 'basic'
  I really mean basic. The functions here make no attempt at being a complete computer algebra system, but
  rather offer a few useful transformations that can be used both in the EA, and also by hand for
  understanding expressions."
  (:require [algebolic.expression.core :as expression]
            [clojure.core.match :as match]
            [clojure.walk :as walk]
            [algebolic.expression.core :as expression]))

(defn- not-number?
  [x]
  (not (number? x)))


(defn- operate-tree
  "Takes an expression and applies the given operation to every sub-expression, including the
  expression itself."
  [op expr]
  (if (expression/non-terminal? expr)
    (op (expression/make-expression (first expr) (map (partial operate-tree op) (rest expr))))
    expr))

(defn operate-full
  "Repeatedly applies the operation to the expression, and its sub-expressions, until the
  result no longer changes. Beware of infinte loops!"
  ;; TODO: could be more helpful here and offer a max-recursion-depth bailout.
  [op expr]
  (let [new-exp (operate-tree op expr)]
    (if (= expr new-exp)
      new-exp
      (recur op new-exp))))

(defn- expand-expr
  "Tries to expand an expression as much as possible, and transform it to a canonical form."
  ;; TODO: this doesn't really work at the moment.
  [expr]
  (match/match [expr]
    ;; distribute plus and minus through times
    [[:times a [:plus b c]]] [:plus [:times a b] [:times a c]]
    [[:times [:plus a b] c]] [:plus [:times a c] [:times b c]]
    [[:times a [:minus b c]]] [:minus [:times a b] [:times a c]]
    [[:times [:minus a b] c]] [:minus [:times a c] [:times b c]]
    ;; associative operations to the left
    [[:plus a [:plus b c]]] [:plus [:plus a b] c]
    [[:minus a [:minus b c]]] [:minus [:minus a b] c]
    [[:times a [:times b c]]] [:times [:times a b] c]
    ;; always move numbers to the left
    [[:plus (a :guard not-number?) (b :guard number?)]] [:plus b a]
    [[:minus (a :guard not-number?) (b :guard number?)]] [:minus b a]
    [[:times (a :guard not-number?) (b :guard number?)]] [:times b a]
    ;; reduce arithmetic operations on numbers
    [[:plus (a :guard number?) (b :guard number?)]] (+ a b)
    [[:minus (a :guard number?) (b :guard number?)]] (- a b)
    [[:times (a :guard number?) (b :guard number?)]] (* a b)
    ;; reduce trig operations on numbers
    [[:cos (a :guard number?)]] (Math/cos a)
    [[:sin (a :guard number?)]] (Math/sin a)
    ;; leave everything else
    [x] x))

(defn expand-full
  "Tries to expand an expression as much as possible, and transform it to a canonical form.  Doesn't do very well
  at the moment!"
  [expr]
  (operate-full expand-expr expr))