;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(ns algebolic.evolution.core
  "The functions in this namespace provide generic plumbing for a generational evolutionary algorithm.
  Supported features include explicit mating pool generation, optional maintenance of an elite population,
  and optional pre-processing/transformation of the population before scoring. Central to these functions
  is the zeitgeist data structure. The zeitgeist contains all of the state of the evolutionary algorithm at one
  generation.

  This plumbing is general enough to run simple GA, or more complex multi-objective algorithms like NSGA-II
  or SPEA2, along with hybrid algorithms that combine these with hill-descent and other non EA
  transformations. This namespace doesn't, though, implement any of those algorithms itself. See other
  namespaces in the algorithms package for specific implementations."
  (:require [algebolic.evolution.reproduction :as reproduction]
            [algebolic.evolution.scoring :as scoring]
            [algebolic.evolution.metrics :as metrics]))

(defn evolve
  "Runs one generation of the evolutionary algorithm. Takes a zeitgeist and a configuration
  and returns the new zeitgeist.

  This function just provides the plumbing and calls out to functions provided in the config to do the actual work.
  The algorithm proceeds in a number of steps:
   - determine the new elite by taking the current rabble and elite, and applying a function
   - decide who is eligible to participate in reproduction, by applying a function to the rabble and elite
   - generate a new rabble from the mating pool, using a given selection procedure and given genetic operations
   - run a list of transformations on the rabble
   - update the scores of the new rabble
   - update metrics for the whole population and store in the zeitgeist
   - run a reporting function to provide the user with information about this generation."
  [zeitgeist config]
  (let [{:keys [ea-config transformations score-functions reporting-function]} config
        {:keys [elite-selector mating-pool-selector reproduction-config]} ea-config
        ;; we time each generations execution (against the wall-clock)
        start-time (System/currentTimeMillis)
        ;; the EA proper
        rabble (:rabble zeitgeist)
        elite (or (:elite zeitgeist) [])
        new-elite (elite-selector rabble elite)
        elite-selected-time (System/currentTimeMillis)
        mating-pool (mating-pool-selector rabble new-elite)
        new-rabble (reproduction/reproduce reproduction-config mating-pool)
        transformed-rabble (if (nil? transformations)
                             new-rabble
                             ((apply comp transformations) new-rabble))
        rabble-ready-time (System/currentTimeMillis)
        scored-transformed-rabble (scoring/update-scores transformed-rabble score-functions)
        evolved-zg (assoc (assoc zeitgeist :rabble scored-transformed-rabble) :elite new-elite)
        end-time (System/currentTimeMillis)
        ;; update some stats on the zg
        stats {:age (+ 1 (or (:age evolved-zg) 0))
               :time (- end-time start-time)
               :selection-time (- elite-selected-time start-time)
               :reproduction-time (- rabble-ready-time elite-selected-time)
               :scoring-time (- end-time rabble-ready-time)}
        final-zg (merge evolved-zg stats)
        _ (when reporting-function (reporting-function final-zg))]
    final-zg))

(defn run-evolution
  "Runs the evolutionary algorithm until the stopping-function in the config is satisfied. Returns
  the final zeitgeist. Runs the checkpointing function in the config after each generation.

  Metrics are accumulated and stored in a user-provided atom. This allows one to monitor the run in
  realtime by watching the atom in another thread, if desired."
  [config metrics-atom]
  ;; score the initial zeitgeist and store it in a atom
  (let [initial-zg (:initial-zeitgeist config)
        scored-initial-zg (assoc initial-zg :rabble
                                            (scoring/update-scores (:rabble initial-zg) (:score-functions config)))
        zeitgeist (atom scored-initial-zg)]
    ;; initialise the metrics atom
    (reset! metrics-atom (metrics/initialise-metrics (keys (:score-functions config))))
    ;; run the main loop
    (while (not ((:stopping-condition config) @zeitgeist))
      ;; evolve a new generation
      (let [new-zg (evolve @zeitgeist config)
            _ (reset! zeitgeist new-zg)
            ;; run the checkpointing function, if present
            cp-func (:checkpoint-function config)
            _ (when (not (nil? cp-func)) (cp-func @zeitgeist))
            ;; update the metrics
            _ (metrics/update-metrics! new-zg metrics-atom (keys (:score-functions config)))]
        ))
    @zeitgeist))

(defn make-zeitgeist
  "A helper function for making an initial zeitgeist from a list of genotypes."
  [genotypes-list]
  {:elite  []
   :rabble (map (fn [g] {:genotype g}) genotypes-list)
   :age    0})