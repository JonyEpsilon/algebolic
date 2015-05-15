;; gorilla-repl.fileformat = 1

;; **
;;; # Expression performance measurements (1.6.0)
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

;; @@
*clojure-version*
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:major</span>","value":":major"},{"type":"html","content":"<span class='clj-unkown'>1</span>","value":"1"}],"value":"[:major 1]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:minor</span>","value":":minor"},{"type":"html","content":"<span class='clj-unkown'>6</span>","value":"6"}],"value":"[:minor 6]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:incremental</span>","value":":incremental"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"}],"value":"[:incremental 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:qualifier</span>","value":":qualifier"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[:qualifier nil]"}],"value":"{:major 1, :minor 6, :incremental 0, :qualifier nil}"}
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
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.Plus@5fbde658&gt;</span>","value":"#<Plus algebolic.expression.Plus@5fbde658>"}
;; <=

;; @@
(criterium/quick-bench (interpreter/->jexpr vars expr))
;; @@
;; ->
;;; WARNING: Final GC required 49.06967247348696 % of runtime
;;; Evaluation count : 1179636 in 6 samples of 196606 calls.
;;;              Execution time mean : 508.829124 ns
;;;     Execution time std-deviation : 12.828508 ns
;;;    Execution time lower quantile : 494.305484 ns ( 2.5%)
;;;    Execution time upper quantile : 525.611362 ns (97.5%)
;;;                    Overhead used : 2.040094 ns
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
;;; WARNING: Final GC required 27.41328366125912 % of runtime
;;; Evaluation count : 24738 in 6 samples of 4123 calls.
;;;              Execution time mean : 25.949198 µs
;;;     Execution time std-deviation : 750.526707 ns
;;;    Execution time lower quantile : 24.667498 µs ( 2.5%)
;;;    Execution time upper quantile : 26.624048 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
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
;;; WARNING: Final GC required 29.504799356663742 % of runtime
;;; Evaluation count : 18042 in 6 samples of 3007 calls.
;;;              Execution time mean : 34.038224 µs
;;;     Execution time std-deviation : 755.849525 ns
;;;    Execution time lower quantile : 32.833395 µs ( 2.5%)
;;;    Execution time upper quantile : 34.827782 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
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
;;; WARNING: Final GC required 30.280048372930118 % of runtime
;;; Evaluation count : 2663796 in 6 samples of 443966 calls.
;;;              Execution time mean : 223.845401 ns
;;;     Execution time std-deviation : 8.525905 ns
;;;    Execution time lower quantile : 215.397220 ns ( 2.5%)
;;;    Execution time upper quantile : 235.312097 ns (97.5%)
;;;                    Overhead used : 2.040094 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Evaluation with derivatives speed:
;; **

;; @@
(criterium/quick-bench (interpreter/evaluate-d expr vars coords))
;; @@
;; ->
;;; WARNING: Final GC required 31.876373505516298 % of runtime
;;; Evaluation count : 6408 in 6 samples of 1068 calls.
;;;              Execution time mean : 90.961832 µs
;;;     Execution time std-deviation : 3.923658 µs
;;;    Execution time lower quantile : 84.506042 µs ( 2.5%)
;;;    Execution time upper quantile : 94.697407 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
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
;;; WARNING: Final GC required 35.46518329712275 % of runtime
;;; Evaluation count : 59298 in 6 samples of 9883 calls.
;;;              Execution time mean : 10.189827 µs
;;;     Execution time std-deviation : 185.931800 ns
;;;    Execution time lower quantile : 10.001264 µs ( 2.5%)
;;;    Execution time upper quantile : 10.445238 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/tree-replace expr 7 expr2))
;; @@
;; ->
;;; WARNING: Final GC required 29.48352736051892 % of runtime
;;; Evaluation count : 44700 in 6 samples of 7450 calls.
;;;              Execution time mean : 13.788810 µs
;;;     Execution time std-deviation : 266.854755 ns
;;;    Execution time lower quantile : 13.371079 µs ( 2.5%)
;;;    Execution time upper quantile : 14.060991 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-terminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 32.94135896475023 % of runtime
;;; Evaluation count : 55080 in 6 samples of 9180 calls.
;;;              Execution time mean : 11.822565 µs
;;;     Execution time std-deviation : 650.257499 ns
;;;    Execution time lower quantile : 10.779015 µs ( 2.5%)
;;;    Execution time upper quantile : 12.442755 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (tree/random-nonterminal-index expr))
;; @@
;; ->
;;; WARNING: Final GC required 29.72799467401797 % of runtime
;;; Evaluation count : 43992 in 6 samples of 7332 calls.
;;;              Execution time mean : 14.915952 µs
;;;     Execution time std-deviation : 1.023400 µs
;;;    Execution time lower quantile : 13.954964 µs ( 2.5%)
;;;    Execution time upper quantile : 16.166570 µs (97.5%)
;;;                    Overhead used : 2.040094 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
