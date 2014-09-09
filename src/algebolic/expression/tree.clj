;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.expression.tree
  "Contains operations that act on expressions as trees. These are used to implement genetic operations
  amongst other things."
  (:require [clojure.zip :as zip]))

(defn count-nodes
  "Count the number of nodes in a tree, including both the function symbols and terminals."
  [ex]
  (if (seq? ex)
    (+ 1 (apply + (map count-nodes (rest ex))))
    1))

(defn expr-zip
  "We define a zipper constructor for manipulating expression trees. Differs from the ordinary
  sequence zippers in that it doesn't recognise the sequence itself as a node. You probably
  don't need to use this directly."
  [expr]
  (zip/zipper
    (constantly true)
    (fn [node] (if (seq? node) (rest node) nil))
    (fn [node children] (with-meta (conj children (first node)) (meta node)))
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