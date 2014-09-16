;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.expression.genetics
  "Core genetic operation on expressions. Includes functions for creating expressions."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random])
  (:require [algebolic.expression.core :as expression]
            [algebolic.expression.tree :as tree]
            [clojure.walk :as walk]))

;;; * Creation *

(defn random-full-tree
  "Generates a tree filled to the given depth from the given functions and terminals. The tree is full
  in the sense that all of the nodes at depths less than the max depth are functions. That is, every
  terminal is at the max-depth. The functions and terminals should be supplied in the format shown in
  algebolic.core."
  [functions terminals depth]
  (if (= depth 0)
    ;; note the double brackets on the next line. This is because `terminals` is a list of functions
    ;; that generate terminals.
    ((rand-nth terminals))
    (let [func (rand-nth functions)
          leaves (repeatedly (:arity func) #(random-full-tree functions terminals (- depth 1)))]
      (conj leaves (:name func)))))

(defn random-grow-tree
  "Generates a tree with given maximum depth from the given functions and terminals. The nodes will
  be chosen uniformly from the set of functions and terminals. The functions and terminals should be
  supplied in the format shown in algebolic.core."
  [functions terminals depth]
  ;; trees cannot be more that `depth` deep
  (if (= depth 0)
    ;; see above for note on double brackets.
    ((rand-nth terminals))
    ;; at depths less than `depth` we choose uniformly from functions or terminals. We do this
    ;; by drawing a random number to choose whether it's going to be a leaf or a terminal, and then
    ;; drawing the appropriate tree.
    (if (< (rand (+ (count terminals) (count functions))) (count terminals))
      ((rand-nth terminals))
      (let [func (rand-nth functions)
            leaves (repeatedly (:arity func) #(random-grow-tree functions terminals (- depth 1)))]
        (conj leaves (:name func))))))

(defn ramped-half-and-half-population
  "Koza's 'ramped half-and-half' tree generation algorithm. Half of the trees are made with `grow`
  and half with `full`, the depths range over 2 to the max depth, distributed uniformly."
  [functions terminals n max-depth]
  (let [generators [(partial random-full-tree functions terminals)
                    (partial random-grow-tree functions terminals)]]
    (repeatedly n #((rand-nth generators) (+ 2 (rand-int (- max-depth 2)))))))

(defn full-population
  "Make an intial population of n `full` expressions, with depths up to and including max-depth."
  [functions terminals n max-depth]
  (repeatedly n (fn [] (random-full-tree functions terminals (+ 1 (rand-int (- max-depth 1)))))))


;; * Mutation *

(defn simple-mutation
  "Replaces a randomly selected part of the expression with a new tree generated by calling the
  given function. The subtree to be replaced is selected from the nodes (terminal and function)
  with uniform probability."
  [new-tree-func expr]
  (let [size (tree/count-nodes expr)
        target (rand-int size)]
    (tree/tree-replace expr target (new-tree-func))))

(defn random-tree-mutation
  "Replaces part of the given expression with a random tree, generated from the given functions and
  terminals, with the given depth"
  [functions terminals depth expr]
  (simple-mutation #(random-grow-tree functions terminals depth) expr))

;; * Crossover *

(defn simple-crossover
  "Randomly selects a crossover point in each of the two given trees, and switches the subtrees
  at these points. The crossover points are selected from the terminal and function nodes with
  uniform probability."
  [expr1 expr2]
  (let [size1 (tree/count-nodes expr1)
        target1 (rand-int size1)
        size2 (tree/count-nodes expr2)
        target2 (rand-int size2)
        subtree1 (tree/sub-tree expr1 target1)
        subtree2 (tree/sub-tree expr2 target2)]
    [(tree/tree-replace expr1 target1 subtree2) (tree/tree-replace expr2 target2 subtree1)]))

(defn ninety-ten-crossover
  "Randomly selects a crossover point in each of the two given trees, and switches the subtrees
  at these points. Following Koza, The crossover points are selected from the terminal and function
  nodes with probability 0.10 and 0.90 respectively."
  [expr1 expr2]
  (let [terminal1 (> (rand) 0.9)
        target1 (if terminal1
                  (tree/random-terminal-index expr1)
                  (tree/random-nonterminal-index expr1))
        terminal2 (> (rand) 0.9)
        target2 (if terminal2
                  (tree/random-terminal-index expr2)
                  (tree/random-nonterminal-index expr2))
        subtree1 (tree/sub-tree expr1 target1)
        subtree2 (tree/sub-tree expr2 target2)]
    [(tree/tree-replace expr1 target1 subtree2) (tree/tree-replace expr2 target2 subtree1)]))

;; TODO: this could conceivably be a generic function in algebolic.evolution.reproduction
(defn size-limited-crossover
  "Takes a crossover operation and makes a size-limited version of it. If a product is larger
  than the size limit then it is replaced with the corresponding parent. Note that this will
  result in expressions over the size limit being locked out of crossover. So long as there is
  some mutation in the system that can reduce the size, then this should not be a problem."
  [max-size crossover-func expr1 expr2]
  (let [crossover-result (crossover-func expr1 expr2)
        new-expr1 (first crossover-result)
        new-expr2 (second crossover-result)]
    [(if (> (tree/count-nodes new-expr1) max-size) expr1 new-expr1)
     (if (> (tree/count-nodes new-expr2) max-size) expr2 new-expr2)]))

(defn ninety-ten-sl-crossover
  "A helper for making a size-limited 90:10 crossover, which you'll probably want to use."
  [max-size expr1 expr2]
  (size-limited-crossover max-size ninety-ten-crossover expr1 expr2))

;; * Non-genetic operations

(defn- twiddle-constant
  "Takes a constant and returns a slightly different constant. Adds uniform noise to the
  constant with spread +-0.05 of the constant's value."
  [const]
  (* const (+ 0.95 (rand 0.1))))

(defn twiddle-constants
  "Twiddle all the constants in an expression randomly by at most +- 5%."
  [expr]
  (walk/postwalk
    #(if (number? %) (twiddle-constant %) %)
    expr))