;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.evolution.reproduction
  "The purpose of a reproduction step is to take a mating pool - a set of individuals that have
  somehow been selected from the population - and generate a new generation of the population.")

(defn reproduce
  [mating-pool pool-selector config]
  (let [{:keys [mutation-new-tree-func tournament-size clone-n mutate-n crossover-n]} config
        clones (repeatedly clone-n #(pool-selector mating-pool tournament-size))
        mutations (repeatedly mutate-n
                              #(mutate-expr
                                (pool-selector mating-pool tournament-size)
                                mutation-new-tree-func))
        crossovers (reduce into (repeatedly crossover-n
                                            #(crossover-expr
                                              (pool-selector mating-pool tournament-size)
                                              (pool-selector mating-pool tournament-size))))]
    (into clones (into mutations crossovers))))