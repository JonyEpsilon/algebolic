(ns algebolic.evolution.metrics)

(defn update-age
  [zeitgeist]
  (assoc zeitgeist :age (+ 1 (or (:age zeitgeist) 0))))
