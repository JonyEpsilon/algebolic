;; gorilla-repl.fileformat = 1

;; **
;;; # Expression performance measurements
;;; 
;;; Benchmarks of the performance of algebolic's core operations on expressions.
;; **

;; @@
(ns algebolic.expression-performance
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
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.Plus@23309b91&gt;</span>","value":"#<Plus algebolic.expression.Plus@23309b91>"}
;; <=

;; @@
(criterium/quick-bench (interpreter/->jexpr vars expr))
;; @@
;; ->
;;; WARNING: Final GC required 3.416252335135401 % of runtime
;;; WARNING: Final GC required 30.251490409864978 % of runtime
;;; Evaluation count : 1887402 in 6 samples of 314567 calls.
;;;              Execution time mean : 322.830687 ns
;;;     Execution time std-deviation : 9.692559 ns
;;;    Execution time lower quantile : 312.932018 ns ( 2.5%)
;;;    Execution time upper quantile : 335.803586 ns (97.5%)
;;;                    Overhead used : 1.972235 ns
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
;;; WARNING: Final GC required 28.488310526923076 % of runtime
;;; Evaluation count : 25680 in 6 samples of 4280 calls.
;;;              Execution time mean : 25.110045 µs
;;;     Execution time std-deviation : 532.862647 ns
;;;    Execution time lower quantile : 24.669555 µs ( 2.5%)
;;;    Execution time upper quantile : 25.840506 µs (97.5%)
;;;                    Overhead used : 1.972235 ns
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
;;; WARNING: Final GC required 31.400354618546128 % of runtime
;;; Evaluation count : 18918 in 6 samples of 3153 calls.
;;;              Execution time mean : 32.445631 µs
;;;     Execution time std-deviation : 1.150861 µs
;;;    Execution time lower quantile : 31.041685 µs ( 2.5%)
;;;    Execution time upper quantile : 33.850751 µs (97.5%)
;;;                    Overhead used : 1.972235 ns
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
;;; WARNING: Final GC required 31.31762482441068 % of runtime
;;; Evaluation count : 3191796 in 6 samples of 531966 calls.
;;;              Execution time mean : 189.459771 ns
;;;     Execution time std-deviation : 6.784055 ns
;;;    Execution time lower quantile : 181.562299 ns ( 2.5%)
;;;    Execution time upper quantile : 198.333607 ns (97.5%)
;;;                    Overhead used : 1.972235 ns
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
;;; WARNING: Final GC required 32.65232859398886 % of runtime
;;; Evaluation count : 59220 in 6 samples of 9870 calls.
;;;              Execution time mean : 10.236508 µs
;;;     Execution time std-deviation : 193.307737 ns
;;;    Execution time lower quantile : 9.985481 µs ( 2.5%)
;;;    Execution time upper quantile : 10.445609 µs (97.5%)
;;;                    Overhead used : 2.037987 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/tree-replace expr 7 expr2))
;; @@
;; ->
;;; WARNING: Final GC required 31.452069202766346 % of runtime
;;; Evaluation count : 44496 in 6 samples of 7416 calls.
;;;              Execution time mean : 13.942916 µs
;;;     Execution time std-deviation : 292.332570 ns
;;;    Execution time lower quantile : 13.610102 µs ( 2.5%)
;;;    Execution time upper quantile : 14.226000 µs (97.5%)
;;;                    Overhead used : 2.037987 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-terminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 31.959488819573288 % of runtime
;;; Evaluation count : 55278 in 6 samples of 9213 calls.
;;;              Execution time mean : 11.356485 µs
;;;     Execution time std-deviation : 365.443406 ns
;;;    Execution time lower quantile : 11.042904 µs ( 2.5%)
;;;    Execution time upper quantile : 11.879122 µs (97.5%)
;;;                    Overhead used : 2.037987 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-nonterminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 31.65995660950157 % of runtime
;;; Evaluation count : 44622 in 6 samples of 7437 calls.
;;;              Execution time mean : 13.659610 µs
;;;     Execution time std-deviation : 421.850223 ns
;;;    Execution time lower quantile : 13.189812 µs ( 2.5%)
;;;    Execution time upper quantile : 14.265738 µs (97.5%)
;;;                    Overhead used : 2.037987 ns
;;; 
;;; Found 1 outliers in 6 samples (16.6667 %)
;;; 	low-severe	 1 (16.6667 %)
;;;  Variance from outliers : 13.8889 % Variance is moderately inflated by outliers
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
