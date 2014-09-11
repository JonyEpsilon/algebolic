(ns algebolic.evolution.metrics
  "Functios for population-level metrics.")

(defn update-zeitgeist-age
  [zeitgeist]
  (assoc zeitgeist :age (+ 1 (or (:age zeitgeist) 0))))
