(ns algebolic.evolution.core
  "The functions in this namespace provide generic plumbing for a generational evolutionary algorithm with explicit
  mating pool generation and maintenance of an elite population. Central to these functions is the zeitgeist data
  structure. The zeitgeist contains all of the state of the evolutionary algorithm at one generation.

  This plumbing is general enough to run simple GA, or more complex multi-objective algorithms like NSGA-II
  or SPEA2. This namespace doesn't, though, implement any of those algorithms itself. See other namespaces in
  this package for specific implementations."
  (:require [algebolic.evolution.reproduction :as reproduction]))

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
  (let [{:keys [elite-selector mating-pool-selector reproduction-config score-update
                metric-update-function reporting-function]} config
        rabble (:rabble zeitgeist)
        elite (or (:elite zeitgeist) [])
        new-elite (elite-selector rabble elite)
        mating-pool (mating-pool-selector rabble new-elite)
        new-rabble (reproduction/reproduce reproduction-config mating-pool)
        scored-new-rabble (score-update new-rabble)
        new-zg (assoc (assoc zeitgeist :rabble scored-new-rabble) :elite new-elite)
        updated-zg (metric-update-function new-zg)
        _ (reporting-function updated-zg)]
    updated-zg))

(defn run-evolution
  "Runs the evolutionary algorithm until the stopping-function in the config is satisfied."
  [config]
  (let [zeitgeist (atom (:initial-zeitgeist config))]
    (while (not ((:stopping-condition config) @zeitgeist))
      (let [new-zg (evolve @zeitgeist config)
            _ (:checkpoint-function config)]
        (swap! zeitgeist (fn [z] new-zg))))
    @zeitgeist))

(defn make-zeitgeist
  "A helper function for making an initial zeitgeist from a list of genotypes."
  [genotypes-list]
  {:elite []
   :rabble (map (fn [g] {:genotype g}) genotypes-list)
   :age 0})