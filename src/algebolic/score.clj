;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(ns algebolic.score
  "A score function is a function that takes an individual and updates a score on it. The score is
   stored on the individual. This namespace contains generic score functions."
  (:require [algebolic.expression.tree :as tree]))

(defn update-size
  "Updates the size of the individual. Uses a simple node count."
  [individual]
  (assoc individual :size (tree/count-nodes (:expression individual))))

(defn update-age
  "Used to track the generational age of an individual. This updates the age by one."
  [individual]
  (assoc individual :age (+ 1 (:age individual))))