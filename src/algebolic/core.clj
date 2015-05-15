;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Released under the MIT license..
;

(ns algebolic.core
  (:require [algebolic.expression.core :as expression]
            [algebolic.expression.genetics :as genetics]
            [algebolic.expression.tree :as tree]
            [algebolic.expression.score :as score]
            [darwin.algorithms.spea2 :as spea2]
            [darwin.evolution.transform :as transform]
            [darwin.evolution.core :as evolution]))

(defn- generation-config
  [functions terminals vars coords data]
  (let [size-limit 80
        min-size 1
        ea-config (spea2/spea2-config
                    {:goals [:error :complexity]
                     :archive-size 50
                     :binary-ops [{:op (partial genetics/ninety-ten-sl-crossover size-limit) :repeat 45}]
                     :unary-ops [{:op (partial genetics/random-tree-mutation functions terminals 5) :repeat 10}]})
        score-functions {:complexity tree/count-nodes
                         :error      (fn [ex] (score/abs-error vars coords data ex))}]
    {:ea-config          ea-config
     :transformations    [(partial transform/apply-to-fraction-of-genotypes
                                   (partial transform/hill-descent
                                            genetics/twiddle-constants
                                            (:error score-functions))
                                   0.5)
                          (partial transform/apply-to-all-genotypes
                                   (partial genetics/trim-hard size-limit))
                          (partial transform/apply-to-all-genotypes
                                   (partial genetics/boost-hard min-size
                                            #(genetics/random-full-tree functions terminals 3)))]
     :score-functions    score-functions
     :reporting-function (fn [z] (if (= (mod (:age z) 10) 0) (print ".") (flush)))}))

(defn sr
  "Runs a symbolic regression evolutionary run with some reasonable default parameters. If you want to use different
  parameters then you should look at building your own generation-config and using the functions in
  `darwin.evolution.core` directly. This function takes a vector of `coords` each of these must be a vector of
  coordinate values, for instance [[x1 y1] [x2 y2] ...]. The parameter `data` is a vector of the target values, at the
  coordinates given in `coords`. The variables that will be used in the evolved expressions are passed in vars, so for
  instance following the example data above, we could pass `'[x y]`. The functions that will be used to build the
  expressions are passed as `functions`. Note that this isn't a list of Clojure functions, but a list of keys
  corresponding to functions defined in `algebolic.expression.core`. The number of generations to evolve is passed as
  the final argument."
  [coords data vars functions generations]
  (let [functions (expression/get-functions functions)
        terminals (expression/terminal-generators vars)
        config (generation-config functions terminals vars coords data)
        initial-zg (evolution/make-zeitgeist (genetics/full-population functions terminals 100 7))]
    (evolution/run-evolution config initial-zg (fn [zg gc] (>= (:age zg) generations)))))