(ns algebolic.algorithms.spea2
  "An implementation of the SPEA2 algorithm of Zitzler et al.

  Currently limited to two objectives, but the API is arranged so that should be easy to
  generalise if needed."
  (:require [algebolic.evolution.pareto :as pareto]))

(defn- calculate-strength
  "The strength of an individual is the count of how many individuals it dominates. This function
  calculates the strength for each member of the population, and assocs it into the individual."
  [keys population]
  (map
    #(assoc % :strength (pareto/dominated-count keys population %))
    population))

(defn- calculate-raw-fitness
  "The raw fitness of an individual i is the sum of the strengths of the individuals that dominate i.
  The smaller this fitness measure the better."
  [keys population i]
  (let [dominators (pareto/dominator-set keys population i)]
    (apply + (map :strength dominators))))


(defn- calculate-raw-fitnesses
  "Calculates raw fitnesses for each individual in a population and assocs it into the individual's
  under the :raw-fitness key"
  [keys population]
  (let [counted-pop (calculate-strength keys population)]
    (map
      #(assoc % :raw-fitness (calculate-raw-fitness keys counted-pop %))
      population)))

;(defn- distance
;  [[k1 k2] i1 i2]
;  (Math/sqrt
;    (+
;      (Math/pow (- (k1 i1) (k1 i2)) 2)
;      (Math/pow (- (k2 i1) (k2 i2)) 2))))
;
;(defn- kth-nearest-distance
;  [keys k population i]
;  (nth (sort < (map (partial distance keys i) population)) k))

(defn coords-from-individual
  "Get the individuals coordinates in objective space as a vector."
  [[k1 k2] i]
  [(k1 i) (k2 i)])

(defn- calculate-density
  [distance]
  (/ 1 (+ distance 2)))

(defn- kth-nearest-distance
  "Get the distance to the kth nearest neighbour of p, given a set of points represented by the given kd-tree."
  [tree k p]
  (Math/sqrt (first (sort > (map :dist-squared (kdtree/nearest-neighbor tree p k))))))

(defn- calculate-densities
  "Calculate the 'densities' for each individual in a population. The density is defined as 1 / (distance to
  kth-nearest neighbour + 2). k is taken as the sqrt of the population size. Assocs the densities into the
  individuals."
  [[k1 k2] population]
  (let [k (Math/sqrt (count population))
        ;; we extract the coordinates of each individual in objective space and build a kd-tree from them
        ;; so we can efficiently find the nearest neighbours.
        coords (map (partial coords-from-individual [k1 k2]) population)
        tree (kdtree/build-tree coords)]
    (map #(assoc % :density
                   (calculate-density
                     (kth-nearest-distance tree k (coords-from-individual [k1 k2] %))))
         population)))

(defn calculate-fitnesses
  [keys population]
  (map #(assoc % :fitness (+ (:raw-fitness %) (:density %)))
       (->> population
            (calculate-densities keys)
            (calculate-raw-fitnesses keys))))