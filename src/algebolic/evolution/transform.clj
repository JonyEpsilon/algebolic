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
  "Apply a transformation to a randomly selected fraction of the population."
  [transform fraction population]
  (map
    #(if (< (rand) fraction) (transform %) %)
    population))

(defn apply-to-genotype
  [func individual]
  (assoc individual :genotype (func (:genotype individual))))

;; * Generic transformations *

(defn- hill-descent-genotype
  "See below."
  [tweak-function score-function genotype]
  (let [tweaked (tweak-function genotype)
        score (score-function genotype)
        tweaked-score (score-function tweaked)]
    (if (< tweaked-score score)
      tweaked
      genotype)))

(defn hill-descent
  "Takes an individual and applies the given tweak function, returns the new individual if it
  scores better on the score-function, otherwise returns the individual. The tweak function and
  the score function should operate on the genotype of the individual."
  [tweak-function score-function individual]
  (apply-to-genotype (partial hill-descent-genotype tweak-function score-function) individual))
