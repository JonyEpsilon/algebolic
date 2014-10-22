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
  (:import algebolic.expression.Scores)
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
;;; &quot;Elapsed time: 119.027967 msecs&quot;
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
;;;   compilation occured before 10787 iterations
;;;   compilation occured before 21572 iterations
;;;   compilation occured before 32357 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 52.892391973558226 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 31992 in 6 samples of 5332 calls.
;;;              Execution time mean : 19.138155 µs
;;;     Execution time std-deviation : 1.004002 µs
;;;    Execution time lower quantile : 17.330663 µs ( 2.5%)
;;;    Execution time upper quantile : 19.930432 µs (97.5%)
;;;                    Overhead used : 2.024879 ns
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
;;; {"type":"html","content":"<span class='clj-double'>6.720024536832625E-10</span>","value":"6.720024536832625E-10"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
  (score/abs-error vars coords values ex)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 1 iterations
;;;   compilation occured before 1610 iterations
;;;   compilation occured before 3219 iterations
;;;   compilation occured before 6437 iterations
;;;   compilation occured before 40226 iterations
;;;   compilation occured before 127112 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 48.66595046386299 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 17862 in 6 samples of 2977 calls.
;;;              Execution time mean : 35.263858 µs
;;;     Execution time std-deviation : 1.437114 µs
;;;    Execution time lower quantile : 33.180569 µs ( 2.5%)
;;;    Execution time upper quantile : 36.796642 µs (97.5%)
;;;                    Overhead used : 2.024879 ns
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
(Scores/chiSquaredAbs values fn-vals)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>6.720024536832625E-10</span>","value":"6.720024536832625E-10"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
  (Scores/chiSquaredAbs values fn-vals)
    ))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 2294 iterations
;;;   compilation occured before 4587 iterations
;;;   compilation occured before 6880 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 34.47200384048192 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 84342 in 6 samples of 14057 calls.
;;;              Execution time mean : 7.053405 µs
;;;     Execution time std-deviation : 264.049391 ns
;;;    Execution time lower quantile : 6.818311 µs ( 2.5%)
;;;    Execution time upper quantile : 7.485936 µs (97.5%)
;;;                    Overhead used : 2.024879 ns
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
(def e (interpreter/->jexpr vars 1.0))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/e</span>","value":"#'balmy-hurricane/e"}
;; <=

;; @@
(.evaluateD e [1.0 1.0])
;; @@

;; @@
(interpreter/evaluate-d expr vars coords)
;; @@

;; @@

;; @@
