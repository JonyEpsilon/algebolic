;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.evolution.reproduction
  "The purpose of a reproduction step is to take a mating pool - a set of individuals that have
  somehow been selected from the population - and generate a new generation of the population.
  In the simplest case the mating pool is just the previous population, but in more complex
  algorithms it may also be made up from members of an archive etc.")

(defn reproduce
  "Generates a population from a mating pool."
  [config pool]
  pool)
