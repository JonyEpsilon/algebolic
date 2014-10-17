;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.tree
  "Contains operations that act on expressions as trees. These are used to implement genetic operations
  amongst other things."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random])
  (:require [algebolic.expression.core :as expression]
            [clojure.zip :as zip]))

;(defn count-nodes-slow
;  "Count the number of nodes in a tree, including both the function symbols and terminals."
;  [ex]
;  (if (expression/non-terminal? ex)
;    (+ 1 (apply + (map count-nodes-slow (rest ex))))
;    1))

;; TODO: use arity, rather than explicitly listing all function symbols
(defn count-nodes
  "Count the number of nodes in a tree, including both the function symbols and terminals."
  [expr]
  (cond
    (symbol? expr) 1
    (number? expr) 1
    true (case (nth expr 0)
           :plus (+ 1 (count-nodes (nth expr 1)) (count-nodes (nth expr 2)))
           :minus (+ 1 (count-nodes (nth expr 1)) (count-nodes (nth expr 2)))
           :times (+ 1 (count-nodes (nth expr 1)) (count-nodes (nth expr 2)))
           :div (+ 1 (count-nodes (nth expr 1)) (count-nodes (nth expr 2)))
           :sin (+ 1 (count-nodes (nth expr 1)))
           :cos (+ 1 (count-nodes (nth expr 1))))))

(defn expr-zip
  "We define a zipper constructor for manipulating expression trees. Differs from the ordinary
  sequence zippers in that it views the `rest` of a vector as the children of the `first` of the
  vector. That makes almost no sense, but if you draw the right diagram it's obvious. Basically, does
  the right thing for navigating the parts of expressions. You probably don't need to use this
  directly."
  [expr]
  (zip/zipper
    (constantly true)
    (fn [node] (if (expression/non-terminal? node) (rest node) nil))
    (fn [node children] (with-meta (expression/make-expression (first node) children) (meta node)))
    expr))

(defn tree-replace
  "Takes a tree and replaces the sub-tree specified by the index with a new-tree. The index is the
  index of the root of the sub-tree in a depth-first walk of the original tree."
  [tree index new-tree]
  (let [subtree-z (nth (iterate zip/next (expr-zip tree)) index)
        new-zipper (zip/replace subtree-z new-tree)]
    (zip/root new-zipper)))

(defn sub-tree
  "Returns the sub-tree of a given tree at index. The index is the index of the root of the sub-tree
   in a depth-first walk of the original tree."
  [tree index]
  (zip/node (nth (iterate zip/next (expr-zip tree)) index)))

(defn random-terminal-index
  "Returns the depth-first index of a randomly selected terminal from the expression. Uniformly
  distributed over the terminals."
  [expr]
  (let [size (count-nodes expr)]
    (first
      (filter #(expression/terminal? (sub-tree expr %))
              (repeatedly #(rand-int size))))))

(defn random-nonterminal-index
  "Returns the depth-first index of a randomly selected non-terminal from the expression. Uniformly
  distributed over the non-terminals. If there are _only_ terminals, that is to say this tree's size
  is 1, then this function will return the terminal anyway."
  [expr]
  (let [size (count-nodes expr)]
    (if (= size 1)
      ;; return the terminal's index, about the best we can do
      0
      (first
        (filter #(expression/non-terminal? (sub-tree expr %))
                (repeatedly #(rand-int size)))))))