;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

;(ns algebolic.scratch)


;(defn make-initial-population
;  [n max-depth]
;  (repeatedly n #(random-full-tree functions terminals (+ 1 (rand-int (- max-depth 1))))))

;(defn score-pp
;  [data ex pressure-coeff]
;  (+ (score data ex) (* pressure-coeff (count-nodes ex))))
;
;
;(defn score-population
;  [population score-func]
;  (map (fn [expr] {:expr expr :score (score-func expr)}) population))



;(def config {:score-func             (memoize #(score-pp data % -0.2))
;             :mutation-new-tree-func #(random-full-tree functions terminals 3)
;             :tournament-size        5
;             :clone-n                40
;             :crossover-n            25
;             :mutate-n               10})
;
;(def init-pop (repeatedly 100 #(random-full-tree functions terminals 3)))

;(defn best-in-generation
;  [pop score-func]
;  (apply max-key :score (score-population pop score-func)))

;(def bests (doall (map #(best-in-generation % (:score-func config)) run-data)))
;
;(defn sample-pop [n gen] (repeatedly n #(rand-nth (nth run-data gen))))
;

