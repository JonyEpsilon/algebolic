;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit ctrl+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns thriving-mountain
  (:import [algebolic.expression.interpreter Plus Times Var Constant JExpr])
  (:require [gorilla-plot.core :as plot]
            [algebolic.expression.interpreter :as interpreter]
            [criterium.core :as criterium]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(set! *warn-on-reflection* true)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(def ^doubles arg (double-array [3.0]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/arg</span>","value":"#'thriving-mountain/arg"}
;; <=

;; @@
(def ^JExpr exp (interpreter/->jexpr [:plus [:times 4.0 4.0] 5.0]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/exp</span>","value":"#'thriving-mountain/exp"}
;; <=

;; @@
exp
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.interpreter.Plus@74af1ef3&gt;</span>","value":"#<Plus algebolic.expression.interpreter.Plus@74af1ef3>"}
;; <=

;; @@
(.evaluate exp arg)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>21.0</span>","value":"21.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (.evaluate exp arg)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 1538561 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 58.012153714233975 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 52735296 in 6 samples of 8789216 calls.
;;;              Execution time mean : 9.587289 ns
;;;     Execution time std-deviation : 0.261130 ns
;;;    Execution time lower quantile : 9.305447 ns ( 2.5%)
;;;    Execution time upper quantile : 9.958876 ns (97.5%)
;;;                    Overhead used : 1.929229 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
