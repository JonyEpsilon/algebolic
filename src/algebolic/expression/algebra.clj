;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.algebra
  (:require [algebolic.expression.core :as expression]
            [clojure.core.match :as match]
            [clojure.walk :as walk]
            [algebolic.expression.core :as expression]))

(defn- not-number?
  [x]
  (not (number? x)))

(defn expand
  [expr]
  (println expr)
  (match/match [expr]
               ;; distribute plus through times
               [[:times a [:plus b c]]] [:plus [:times a b] [:times a c]]
               [[:times [:plus a b] c]] [:plus [:times a c] [:times b c]]
               ;; plus and times to the left
               [[:plus a [:plus b c]]] [:plus [:plus a b] c]
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
               [x] x
               ))

(defn expand-tree
  [expr]
  (if (expression/non-terminal? expr)
    (expand (expression/make-expression (first expr) (map expand-tree (rest expr))))
    expr))

(defn expand-full
  [expr]
  (let [new-exp (expand-tree expr)]
    (if (= expr new-exp)
      new-exp
      (recur new-exp))))