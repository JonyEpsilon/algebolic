;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.evolutions.selection
  "The purpose of selection is to take a population and generate a new population.")

(defn tournament-selector
  "A simple tournament selector. Selects from the given population with given tournament-size
  using the score-key to extract the score from the individual."
  [tournament-size score-key population]
  (let [competitors (repeatedly tournament-size #(rand-nth population))]
    (apply max-key score-key competitors)))


(defn lexicographic-tournament-selector
  [tournament-size score-keys population]
  )