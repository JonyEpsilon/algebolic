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
(ns balmy-hurricane
  (:import algebolic.expression.interpreter.Evaluator
           algebolic.expression.score.ScoreUtils)
  (:require [gorilla-plot.core :as plot]
            [clojure.walk :as walk]
            [algebolic.expression.core :as expression]
            [algebolic.expression.interpreter :as interpreter]
            [algebolic.expression.score :as score]
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
(def expr [:plus [:times 'x 'x] 'x])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr</span>","value":"#'balmy-hurricane/expr"}
;; <=

;; @@
expr
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times x x]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:plus [:times x x] x]"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate expr ['x 'y] [[3.0 4.0]]))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 43.232 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(interpreter/evaluate expr ['x 'y] [[3.0 4.0]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>[12.0]</span>","value":"[12.0]"}
;; <=

;; @@
(def coords (mapv (fn [i] [(+ i 1.0) (+ i 2.0)]) (range 1000)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/coords</span>","value":"#'balmy-hurricane/coords"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate expr ['x 'y] coords)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 11076 iterations
;;;   compilation occured before 22150 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 48.199180826166035 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 35682 in 6 samples of 5947 calls.
;;;              Execution time mean : 17.073475 µs
;;;     Execution time std-deviation : 231.070069 ns
;;;    Execution time lower quantile : 16.823517 µs ( 2.5%)
;;;    Execution time upper quantile : 17.290644 µs (97.5%)
;;;                    Overhead used : 1.940878 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def data (mapv (fn [x] [[x] (+ (* 2 x) (* 3 x x))]) (range 0.0 100.0 0.1)))
(def vars ['x])
(def coords (mapv first data))
(def values (mapv second data))
(def ex [:plus [:times 2.0 'x] [:times 3.0 [:times 'x 'x]]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/ex</span>","value":"#'balmy-hurricane/ex"}
;; <=

;; @@
(score/abs-error vars coords values ex)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>4639.759059650429</span>","value":"4639.759059650429"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
  (score/abs-error vars coords values ex)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 6491 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 33.107417058173 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 936 in 6 samples of 156 calls.
;;;              Execution time mean : 633.054688 µs
;;;     Execution time std-deviation : 9.701450 µs
;;;    Execution time lower quantile : 620.100628 µs ( 2.5%)
;;;    Execution time upper quantile : 640.743256 µs (97.5%)
;;;                    Overhead used : 1.940878 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def fn-vals (interpreter/evaluate ex vars coords))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/fn-vals</span>","value":"#'balmy-hurricane/fn-vals"}
;; <=

;; @@
(ScoreUtils/chiSquaredAbs values fn-vals)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>6.720024536832625E-10</span>","value":"6.720024536832625E-10"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
  (ScoreUtils/chiSquaredAbs values fn-vals)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 47088 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 50.691493943451015 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 92616 in 6 samples of 15436 calls.
;;;              Execution time mean : 6.714005 µs
;;;     Execution time std-deviation : 132.081525 ns
;;;    Execution time lower quantile : 6.571653 µs ( 2.5%)
;;;    Execution time upper quantile : 6.871480 µs (97.5%)
;;;                    Overhead used : 1.940878 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
