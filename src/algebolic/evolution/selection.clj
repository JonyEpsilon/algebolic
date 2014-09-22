;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.evolution.selection
  "The purpose of selection is to pick an individual from a set. The selector will usually not
  pick uniformly, so as to exert some evolutionary pressure. This namespace has some general
  purpose selection algorithms."
  (:refer-clojure :exclude [rand rand-nth rand-int])
  (:use [algebolic.utility.random]))

(defn tournament-selector
  "A simple tournament selector. Selects from the given population with given tournament-size
  using the score-key to extract the score from the individual. Scores are always minimized."
  [tournament-size score-key population]
  (let [competitors (repeatedly tournament-size #(rand-nth population))]
    (apply min-key score-key competitors)))