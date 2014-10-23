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
;;; &quot;Elapsed time: 121.040878 msecs&quot;
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
;;;   compilation occured before 22677 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 55.82866244824907 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 28692 in 6 samples of 4782 calls.
;;;              Execution time mean : 21.350788 µs
;;;     Execution time std-deviation : 1.080753 µs
;;;    Execution time lower quantile : 20.338374 µs ( 2.5%)
;;;    Execution time upper quantile : 22.571860 µs (97.5%)
;;;                    Overhead used : 1.964170 ns
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
;;;   compilation occured before 3163 iterations
;;;   compilation occured before 6325 iterations
;;;   compilation occured before 22135 iterations
;;;   compilation occured before 25297 iterations
;;;   compilation occured before 28459 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 51.26189558229463 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 18618 in 6 samples of 3103 calls.
;;;              Execution time mean : 35.448615 µs
;;;     Execution time std-deviation : 2.150887 µs
;;;    Execution time lower quantile : 34.313232 µs ( 2.5%)
;;;    Execution time upper quantile : 39.110337 µs (97.5%)
;;;                    Overhead used : 1.964170 ns
;;; 
;;; Found 1 outliers in 6 samples (16.6667 %)
;;; 	low-severe	 1 (16.6667 %)
;;;  Variance from outliers : 14.7018 % Variance is moderately inflated by outliers
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
;;;   compilation occured before 2418 iterations
;;;   compilation occured before 7252 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 46.592252272984155 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 89316 in 6 samples of 14886 calls.
;;;              Execution time mean : 8.894085 µs
;;;     Execution time std-deviation : 3.040514 µs
;;;    Execution time lower quantile : 6.750802 µs ( 2.5%)
;;;    Execution time upper quantile : 12.695283 µs (97.5%)
;;;                    Overhead used : 1.964170 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def vars '[x y])
(def expr [:plus [:times 2.0 [:times 'x 'x]] [:times 3.0 'y]])
(def coords (map (fn [x] [x (+ x 1.0)]) (range 0.0 100.0 0.1)))
(def ^algebolic.expression.JExpr e (interpreter/->jexpr vars expr))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/e</span>","value":"#'balmy-hurricane/e"}
;; <=

;; @@
(.evaluateD e [3.0 4.0])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;double[] [D@3c080a5&gt;</span>","value":"#<double[] [D@3c080a5>"}
;; <=

;; @@
(criterium/quick-bench (.evaluateD e [2.0 3.0]))
;; @@
;; ->
;;; WARNING: Final GC required 35.123706176942285 % of runtime
;;; Evaluation count : 9128262 in 6 samples of 1521377 calls.
;;;              Execution time mean : 65.295136 ns
;;;     Execution time std-deviation : 1.637121 ns
;;;    Execution time lower quantile : 63.352048 ns ( 2.5%)
;;;    Execution time upper quantile : 66.936482 ns (97.5%)
;;;                    Overhead used : 1.964170 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/quick-bench (interpreter/evaluate-d expr vars coords))
;; @@
;; ->
;;; WARNING: Final GC required 35.52281940916586 % of runtime
;;; Evaluation count : 4416 in 6 samples of 736 calls.
;;;              Execution time mean : 136.218011 µs
;;;     Execution time std-deviation : 3.943339 µs
;;;    Execution time lower quantile : 131.241573 µs ( 2.5%)
;;;    Execution time upper quantile : 141.017827 µs (97.5%)
;;;                    Overhead used : 1.964170 ns
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
