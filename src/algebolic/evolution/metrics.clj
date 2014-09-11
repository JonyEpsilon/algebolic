(ns algebolic.evolution.metrics
  "Population level metrics functions.")

(defn update-zeitgeist-age
  [zeitgeist]
  (assoc zeitgeist :age (+ 1 (or (:age zeitgeist) 0))))
