(ns algebolic.evolution.core
  "The functions in this namespace provide generic plumbing for a generational evolutionary algorithm with explicit
  mating pool generation and maintenance of an elite population. Central to these functions is the zeitgeist data
  structure. The zeitgeist contains all of the state of the evolutionary algorithm at one generation.

  This plumbing is general enough to run simple GA, or more complex multi-objective algorithms like NSGA-II
  or SPEA2. This namespace doesn't, though, implement any of those algorithms itself. See other namespaces in
  this package for specific implementations."
  (:require [algebolic.evolution.reproduction :as reproduction]
            [algebolic.evolution.scoring :as scoring]))

(defn evolve
  "Runs one generation of the evolutionary algorithm. Takes a zeitgeist and a configuration
  and returns the new zeitgeist.

  This function just provides the plumbing and calls out to functions provided in the config to do the actual work.
  The algorithm proceeds in a number of steps:
   - determine the new elite by taking the current rabble and elite, and applying a function
   - decide who is eligible to participate in reproduction, by applying a function to the rabble and elite
   - generate a new rabble from the mating pool, using a given selection procedure and given genetic operations
   - update the scores of the new rabble
   - update metrics for the whole population and store in the zeitgeist
   - run a reporting function to provide the user with information about this generation."
  [zeitgeist config]
  (let [{:keys [ea-config score-functions reporting-function]} config
        {:keys [elite-selector mating-pool-selector reproduction-config]} ea-config
        rabble (:rabble zeitgeist)
        elite (or (:elite zeitgeist) [])
        new-elite (elite-selector rabble elite)
        mating-pool (mating-pool-selector rabble new-elite)
        new-rabble (reproduction/reproduce reproduction-config mating-pool)
        scored-new-rabble (scoring/update-scores new-rabble score-functions)
        new-zg (assoc (assoc zeitgeist :rabble scored-new-rabble) :elite new-elite)
        _ (reporting-function new-zg)]
    (algebolic.evolution.metrics/update-zeitgeist-age new-zg)))

(defn run-evolution
  "Runs the evolutionary algorithm until the stopping-function in the config is satisfied. Returns
  the final zeitgeist. Runs the checkpointing function in the config after each generation."
  [config]
  (let [initial-zg (:initial-zeitgeist config)
        ;; we need to score the initial rabble before we enter the EA loop
        scored-initial-zg (assoc initial-zg :rabble
                                            (scoring/update-scores
                                              (:rabble initial-zg)
                                              (:score-functions config)))
         zeitgeist (atom scored-initial-zg)]
    (while (not ((:stopping-condition config) @zeitgeist))
      (let [new-zg (evolve @zeitgeist config)
            cp-func (:checkpoint-function config)
            _ (swap! zeitgeist (fn [z] new-zg))
            _ (when (not (nil? cp-func)) (cp-func @zeitgeist))]
        ))
    {:final-zeitgeist @zeitgeist}))

(defn make-zeitgeist
  "A helper function for making an initial zeitgeist from a list of genotypes."
  [genotypes-list]
  {:elite []
   :rabble (map (fn [g] {:genotype g}) genotypes-list)
   :age 0})