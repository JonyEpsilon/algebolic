(ns algebolic.algorithms.sso
  "Implements a simple, single-objective genetic algorithm."
  (:require [algebolic.evolution.selection :as selection]
            [algebolic.evolution.metrics :as metrics]))

(defn sso-ea-config
  "Generate a default config for a simple, single-objective genetic algorithm."
  [config]
  (let [{:keys [unary-ops binary-ops tournament-size goal]} config]
    {:elite-selector         (fn [_ _] [])
     :mating-pool-selector   (fn [rabble _] rabble)
     :reproduction-config    {:selector   (partial selection/tournament-selector tournament-size goal)
                              :unary-ops  unary-ops
                              :binary-ops binary-ops}}))