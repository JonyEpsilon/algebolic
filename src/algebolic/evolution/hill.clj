(ns algebolic.evolution.hill
  "Implements a very simple hill-descent step that can be combined with the EA.")

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

(defn hill-descender
  "Applies hill-descent to each individual in the population. See `hill-descent` for docs."
  [tweak-function score-function population]
  (map (partial hill-descent tweak-function score-function) population))