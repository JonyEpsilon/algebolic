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
  (:import [algebolic.expression.interpreter Plus Times Var Constant Evaluatable])
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
(def ^JExpr exp (interpreter/->jexpr ['x 'y] [:plus [:times 'x 'x] 'y]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/exp</span>","value":"#'thriving-mountain/exp"}
;; <=

;; @@
exp
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.Plus@427ac75b&gt;</span>","value":"#<Plus algebolic.expression.Plus@427ac75b>"}
;; <=

;; @@
(.evaluate exp [3.0 5.0])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>14.0</span>","value":"14.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (.evaluate exp [3.0 4.0])
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 403250 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 53.22892582244313 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 31753578 in 6 samples of 5292263 calls.
;;;              Execution time mean : 17.583112 ns
;;;     Execution time std-deviation : 0.406516 ns
;;;    Execution time lower quantile : 17.021443 ns ( 2.5%)
;;;    Execution time upper quantile : 18.022315 ns (97.5%)
;;;                    Overhead used : 2.071901 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(.indexOf ['x 'y] 'y)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>1</span>","value":"1"}
;; <=

;; @@

;; @@
