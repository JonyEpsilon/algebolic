;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.evolution
  (:require [clojure.walk :as walk]
            [clojure.zip :as zip]))

(defn random-full-tree
  [functions terminals depth]
  (if (= depth 0)
    ;; note that the elements of terminals are _functions_ that generate the terminal, hence the
    ;; double brackets on the line below
    ((rand-nth terminals))
    (let [func (rand-nth functions)
          leaves (repeatedly (:arity func) #(random-full-tree functions terminals (- depth 1)))]
      (conj leaves (:name func)))))


(defn functionalise [ex] (eval (list 'fn '[x] ex)))

;(defn make-initial-population
;  [n max-depth]
;  (repeatedly n #(random-full-tree functions terminals (+ 1 (rand-int (- max-depth 1))))))

(def data (doall (map (fn [x] [x (+ (* 3 x) (* 2 x x))]) (range 0 10 0.5))))

(defn score
  [data ex]
  (let [f (functionalise ex)]
    (* -1 (apply + (map #(Math/abs (- (f (first %)) (second %))) data)))))

(defn count-nodes
  [ex]
  (if (seq? ex)
    (+ 1 (apply + (map count-nodes (rest ex))))
    1))

(defn score-pp
  [data ex pressure-coeff]
  (+ (score data ex) (* pressure-coeff (count-nodes ex))))

(defn expr-zip
  [expr]
  (zip/zipper
    (constantly true)
    (fn [node] (if (seq? node) (rest node) nil))
    (fn [node children] (with-meta (conj children (first node)) (meta node)))
    expr))

(defn tree-replace
  [tree index new-tree]
  (let [subtree-z (nth (iterate zip/next (expr-zip tree)) index)
        new-zipper (zip/replace subtree-z new-tree)]
    (zip/root new-zipper)))


(defn mutate-expr
  [expr new-tree-func]
  (let [size (count-nodes expr)
        target (rand-int size)]
    (tree-replace expr target (new-tree-func))))

(defn sub-tree
  [tree index]
  (zip/node (nth (iterate zip/next (expr-zip tree)) index)))

(defn crossover-expr
  [expr1 expr2]
  (let [size1 (count-nodes expr1)
        target1 (rand-int size1)
        size2 (count-nodes expr2)
        target2 (rand-int size2)
        subtree1 (sub-tree expr1 target1)
        subtree2 (sub-tree expr2 target2)]
    [(tree-replace expr1 target1 subtree2) (tree-replace expr2 target2 subtree1)]))

(defn score-population
  [population score-func]
  (map (fn [expr] {:expr expr :score (score-func expr)}) population))

(defn tournament-selector
  [scored-popn tournament-size]
  (let [competitors (repeatedly tournament-size #(rand-nth scored-popn))]
    (:expr (apply max-key :score competitors))))

(defn evolve
  [population config]
  (let [{:keys [score-func mutation-new-tree-func tournament-size clone-n mutate-n crossover-n]} config
        scored-popn (score-population population score-func)
        clones (repeatedly clone-n #(tournament-selector scored-popn tournament-size))
        mutations (repeatedly mutate-n
                              #(mutate-expr
                                (tournament-selector scored-popn tournament-size)
                                mutation-new-tree-func))
        crossovers (reduce into (repeatedly crossover-n
                                            #(crossover-expr
                                              (tournament-selector scored-popn tournament-size)
                                              (tournament-selector scored-popn tournament-size))))]
    (into clones (into mutations crossovers))))

;(def config {:score-func             (memoize #(score-pp data % -0.2))
;             :mutation-new-tree-func #(random-full-tree functions terminals 3)
;             :tournament-size        5
;             :clone-n                40
;             :crossover-n            25
;             :mutate-n               10})
;
;(def init-pop (repeatedly 100 #(random-full-tree functions terminals 3)))

(defn best-in-generation
  [pop score-func]
  (apply max-key :score (score-population pop score-func)))

;(def bests (doall (map #(best-in-generation % (:score-func config)) run-data)))
;
;(defn sample-pop [n gen] (repeatedly n #(rand-nth (nth run-data gen))))
;
