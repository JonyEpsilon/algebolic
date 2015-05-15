;; gorilla-repl.fileformat = 1

;; **
;;; # SPEA2 benchmarking
;;; 
;;; This notebook tests the performance (in the sense of runtime) of the SPEA2 functions.
;; **

;; @@
(ns algebolic.spea2-performance
  (:require [gorilla-plot.core :as plot]
            [algebolic.evolution.pareto :as pareto]
            [algebolic.algorithms.spea2 :as spea2]
            [criterium.core :as criterium]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; A test population of expressions. This is the scored "rabble" after a few hundred generations of trying to fit some polynomial data.
;; **

;; @@
(def rabble (read-string (slurp "ws/data/spea-test-rabble.clj")))
(def i1 (nth rabble 0))
(def i2 (nth rabble 1))
(def i3 (nth rabble 10))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.spea2-performance/i3</span>","value":"#'algebolic.spea2-performance/i3"}
;; <=

;; @@
(criterium/quick-bench (pareto/dominates [:error :complexity] i1 i3))
;; @@
;; ->
;;; WARNING: Final GC required 34.3241912972104 % of runtime
;;; Evaluation count : 5019600 in 6 samples of 836600 calls.
;;;              Execution time mean : 121.465250 ns
;;;     Execution time std-deviation : 2.407070 ns
;;;    Execution time lower quantile : 119.054047 ns ( 2.5%)
;;;    Execution time upper quantile : 124.465148 ns (97.5%)
;;;                    Overhead used : 1.931523 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (pareto/dominated-count [:error :complexity] rabble i3))
;; @@
;; ->
;;; WARNING: Final GC required 31.7073730982639 % of runtime
;;; Evaluation count : 30684 in 6 samples of 5114 calls.
;;;              Execution time mean : 21.134931 µs
;;;     Execution time std-deviation : 576.999093 ns
;;;    Execution time lower quantile : 20.648928 µs ( 2.5%)
;;;    Execution time upper quantile : 21.994753 µs (97.5%)
;;;                    Overhead used : 1.931523 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
