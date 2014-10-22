;; gorilla-repl.fileformat = 1

;; **
;;; # Expression performance measurements
;;; 
;;; Benchmarks of the performance of algebolic's core operations on expressions.
;; **

;; @@
(ns algebolic.expression-performance
  (:import [algebolic.expression JExpr])
  (:require [gorilla-plot.core :as plot]
            [algebolic.expression.core :as expression]
            [algebolic.expression.interpreter :as interpreter]
            [algebolic.expression.score :as score]
            [algebolic.expression.tree :as tree]
            [criterium.core :as criterium]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Expression evaluation and scoring
;;; 
;;; We make a test expression to work with, and 1000 test data points.
;; **

;; @@
(def vars '[x])
(def expr [:plus [:times 3.0 [:times 'x 'x]] [:times 2.0 'x]])
(def xs (range 0.0 100.0 0.1))
(def coords (mapv (fn [x] [x]) xs))
(def data (mapv (fn [x] (+ (* 2 x) (* 3 x x))) xs))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.expression-performance/data</span>","value":"#'algebolic.expression-performance/data"}
;; <=

;; **
;;; Evaluation speed:
;; **

;; @@
(interpreter/->jexpr vars expr)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.Plus@560ea43b&gt;</span>","value":"#<Plus algebolic.expression.Plus@560ea43b>"}
;; <=

;; @@
(criterium/quick-bench (interpreter/->jexpr vars expr))
;; @@
;; ->
;;; WARNING: Final GC required 32.09562087113482 % of runtime
;;; Evaluation count : 1252098 in 6 samples of 208683 calls.
;;;              Execution time mean : 517.630096 ns
;;;     Execution time std-deviation : 18.672823 ns
;;;    Execution time lower quantile : 493.348366 ns ( 2.5%)
;;;    Execution time upper quantile : 540.500155 ns (97.5%)
;;;                    Overhead used : 1.964170 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(last (interpreter/evaluate expr vars coords))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>30199.999999999152</span>","value":"30199.999999999152"}
;; <=

;; @@
(criterium/quick-bench (interpreter/evaluate expr vars coords))
;; @@
;; ->
;;; WARNING: Final GC required 33.70842496708887 % of runtime
;;; Evaluation count : 22902 in 6 samples of 3817 calls.
;;;              Execution time mean : 27.058773 µs
;;;     Execution time std-deviation : 1.544751 µs
;;;    Execution time lower quantile : 25.369139 µs ( 2.5%)
;;;    Execution time upper quantile : 28.550102 µs (97.5%)
;;;                    Overhead used : 1.964170 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Accuracy scoring speed:
;; **

;; @@
(score/abs-error vars coords data expr)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>6.720024536832625E-10</span>","value":"6.720024536832625E-10"}
;; <=

;; @@
(criterium/quick-bench (score/abs-error vars coords data expr))
;; @@
;; ->
;;; WARNING: Final GC required 31.79588711321808 % of runtime
;;; Evaluation count : 19278 in 6 samples of 3213 calls.
;;;              Execution time mean : 32.052864 µs
;;;     Execution time std-deviation : 326.871096 ns
;;;    Execution time lower quantile : 31.512308 µs ( 2.5%)
;;;    Execution time upper quantile : 32.442756 µs (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;;; Found 1 outliers in 6 samples (16.6667 %)
;;; 	low-severe	 1 (16.6667 %)
;;;  Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Complexity scoring speed:
;; **

;; @@
(tree/count-nodes expr)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>9</span>","value":"9"}
;; <=

;; @@
(criterium/quick-bench (tree/count-nodes expr))
;; @@
;; ->
;;; WARNING: Final GC required 32.136136775203724 % of runtime
;;; Evaluation count : 2912904 in 6 samples of 485484 calls.
;;;              Execution time mean : 216.050033 ns
;;;     Execution time std-deviation : 4.095084 ns
;;;    Execution time lower quantile : 211.434068 ns ( 2.5%)
;;;    Execution time upper quantile : 219.914967 ns (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Expression manipulation
;; **

;; @@
(def expr2 [:plus 'x [:times 3.0 'x]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.expression-performance/expr2</span>","value":"#'algebolic.expression-performance/expr2"}
;; <=

;; @@
(criterium/quick-bench (tree/sub-tree expr 7))
;; @@
;; ->
;;; WARNING: Final GC required 32.84345894065332 % of runtime
;;; Evaluation count : 55704 in 6 samples of 9284 calls.
;;;              Execution time mean : 10.384139 µs
;;;     Execution time std-deviation : 234.473992 ns
;;;    Execution time lower quantile : 10.051326 µs ( 2.5%)
;;;    Execution time upper quantile : 10.649675 µs (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/tree-replace expr 7 expr2))
;; @@
;; ->
;;; WARNING: Final GC required 29.99938720435366 % of runtime
;;; Evaluation count : 46158 in 6 samples of 7693 calls.
;;;              Execution time mean : 13.687970 µs
;;;     Execution time std-deviation : 517.508157 ns
;;;    Execution time lower quantile : 13.056860 µs ( 2.5%)
;;;    Execution time upper quantile : 14.371536 µs (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-terminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 35.109801929642494 % of runtime
;;; Evaluation count : 50982 in 6 samples of 8497 calls.
;;;              Execution time mean : 11.785890 µs
;;;     Execution time std-deviation : 711.510727 ns
;;;    Execution time lower quantile : 10.996235 µs ( 2.5%)
;;;    Execution time upper quantile : 12.784539 µs (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;;; Found 1 outliers in 6 samples (16.6667 %)
;;; 	low-severe	 1 (16.6667 %)
;;;  Variance from outliers : 14.6818 % Variance is moderately inflated by outliers
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-nonterminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 29.73244068421747 % of runtime
;;; Evaluation count : 45402 in 6 samples of 7567 calls.
;;;              Execution time mean : 14.443179 µs
;;;     Execution time std-deviation : 586.384302 ns
;;;    Execution time lower quantile : 13.767770 µs ( 2.5%)
;;;    Execution time upper quantile : 15.066244 µs (97.5%)
;;;                    Overhead used : 1.928557 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
