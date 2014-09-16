(ns algebolic.evolution.transform
  "Functions for transforming individuals, and the population. These can be supplied to the EA
  to perform non-EA transformations before scoring (see `algebolic.evolution.core` for details.
  This namespace contains general helper functions for constructing transformations, and generic
  transformations that are representation independent. A given representation might implement more
  specific transformations (think simplifying symbolic regression expressions, for instance)."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random]))


;; * Helpers *

(defn apply-to-fraction
  [transform fraction population]
  (map
    #(if (< (rand) fraction) (transform %) %)
    population))


;; * Generic transformations *

(defn hill-descent
  "Takes an individual and applies the given tweak function, returns the new individual if it
  scores better on the score-function, otherwise returns the individual. The tweak function and
  the score function should operate on the genotype of the individual."
  [tweak-function score-function individual]
  (let [tweaked (assoc individual :genotype (tweak-function (:genotype individual)))
        score (score-function (:genotype individual))
        tweaked-score (score-function (:genotype tweaked))]
    (if (< tweaked-score score)
      tweaked
      individual)))
