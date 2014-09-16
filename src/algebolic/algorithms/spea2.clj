(ns algebolic.algorithms.spea2
  "An implementation of the SPEA2 algorithm of Zitzler et al.

  Currently limited to two objectives, but the API is arranged so that should be easy to
  generalise if needed."
  (:require [algebolic.evolution.pareto :as pareto]))

(defn- calculate-strength
  [keys population]
  (map
    #(assoc % :strength (pareto/dominated-count keys population %))
    population))

(defn- calculate-raw-fitness
  [keys population i]
  (let [dominators (pareto/dominator-set keys population i)]
    (apply + (map :strength dominators))))


(defn- calculate-raw-fitnesses
  [keys population]
  (let [counted-pop (calculate-strength keys population)]
    (map
      #(assoc % :raw-fitness (calculate-raw-fitness keys counted-pop %))
      population)))

(defn- distance
  [[k1 k2] i1 i2]
  (Math/sqrt
    (+
      (Math/pow (- (k1 i1) (k1 i2)) 2)
      (Math/pow (- (k2 i1) (k2 i2)) 2))))

(defn- kth-nearest-distance
  [keys k population i]
  (nth (sort < (map (partial distance keys i) population)) k))

(defn- calculate-density
  [keys population i]
  (let [distance (kth-nearest-distance keys (int (Math/sqrt (count population))) population i)]
    (/ 1 (+ distance 2))))

(defn- calculate-densities
  [keys population]
  (map #(assoc % :density (calculate-density keys population %)) population))

(defn calculate-fitnesses
  [keys population]
  (map #(assoc % :fitness (+ (:raw-fitness %) (:density %)))
       (->> population
            (calculate-densities keys)
            (calculate-raw-fitnesses keys))))