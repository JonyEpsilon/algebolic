(ns algebolic.evolution.metrics
  "Functions for extracting metrics for each generation.")

(defn initialise-metrics
  "Generates an empty metrics structure with the appropriates slots, given the score keys."
  [score-keys]
  (into {:time []} (map (fn [k] [k {:mean [] :min [] :max []}]) score-keys)))

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

(defn- update-other-metrics
  "Used for bunging in other ad-hoc metrics dervied from the zeitgeist."
  [metrics zg]
  (update-in metrics [:time] #(conj % (:time zg))))

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