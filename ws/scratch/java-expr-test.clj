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
  (:import [algebolic.expression TestExpressionFactory])
  (:require [gorilla-plot.core :as plot]
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
(def ^algebolic.expression.Expression testExpr (TestExpressionFactory/getTestExpression1))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/testExpr</span>","value":"#'thriving-mountain/testExpr"}
;; <=

;; @@
(def ^doubles arg (double-array [3.0]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/arg</span>","value":"#'thriving-mountain/arg"}
;; <=

;; @@
(.evaluate testExpr arg)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>19.0</span>","value":"19.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (.evaluate testExpr arg)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 595288 iterations
;;;   compilation occured before 1785764 iterations
;;;   compilation occured before 4166716 iterations
;;;   compilation occured before 4761954 iterations
;;;   compilation occured before 76785752 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 63.67554855037633 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 49163586 in 6 samples of 8193931 calls.
;;;       Execution time sample mean : 10.227444 ns
;;;              Execution time mean : 10.227444 ns
;;; Execution time sample std-deviation : 0.524201 ns
;;;     Execution time std-deviation : 0.526168 ns
;;;    Execution time lower quantile : 9.886195 ns ( 2.5%)
;;;    Execution time upper quantile : 11.119486 ns (97.5%)
;;;                    Overhead used : 2.000651 ns
;;; 
;;; Found 1 outliers in 6 samples (16.6667 %)
;;; 	low-severe	 1 (16.6667 %)
;;;  Variance from outliers : 13.9336 % Variance is moderately inflated by outliers
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
