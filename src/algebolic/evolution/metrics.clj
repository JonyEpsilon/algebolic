;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(ns algebolic.evolution.metrics
  "Functions for extracting metrics for each generation.")

;; TODO: this could be better, I think. It's a bit repetitive specifying the metrics to
;; TODO: generate in the evolution function, and then having to specify the metrics to
;; TODO: gather again here.

(defn initialise-metrics
  "Generates an empty metrics structure with the appropriates slots, given the score keys."
  [score-keys]
  (into {:time [] :selection-time [] :reproduction-time [] :scoring-time []}
        (map (fn [k] [k {:mean [] :min [] :max []}]) score-keys)))

(defn- update-population-metric
  "Update a single population-level metric."
  [pop metrics key]
  (let [scores (map key pop)
        mean-score (double (/ (apply + scores) (count scores)))
        min-score (apply min scores)
        max-score (apply max scores)]
    (-> metrics
        (update-in [key :mean] #(conj % mean-score))
        (update-in [key :min] #(conj % min-score))
        (update-in [key :max] #(conj % max-score)))))

(defn- update-population-metrics
  "Update all population-level metrics."
  [metrics pop keys]
  (reduce
    (partial update-population-metric pop) metrics keys))

(defn- update-metric
  [zg metrics key]
  (update-in metrics [key] #(conj % (key zg))))

(defn- update-other-metrics
  "Used for bunging in other ad-hoc metrics dervied from the zeitgeist."
  [metrics zg]
  (reduce (partial update-metric zg) metrics [:time :selection-time :scoring-time :reproduction-time]))

(defn update-metrics!
  "Takes a metrics atom and update score and other metrics from a given zeitgeist. Swaps the
  atom contents with the new metrics."
  [zg metrics-atom score-keys]
  (let [rabble (:rabble zg)
        elite (:elite zg)
        all (into rabble elite)]
    (swap! metrics-atom
           (fn [m]
             (-> m
                 (update-population-metrics all score-keys)
                 (update-other-metrics zg))))))