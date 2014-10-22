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
;;; &quot;Elapsed time: 132.709106 msecs&quot;
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
;;;   compilation occured before 8255 iterations
;;;   compilation occured before 16509 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 49.35991414323788 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 33768 in 6 samples of 5628 calls.
;;;              Execution time mean : 18.100671 µs
;;;     Execution time std-deviation : 904.961726 ns
;;;    Execution time lower quantile : 17.094821 µs ( 2.5%)
;;;    Execution time upper quantile : 19.338785 µs (97.5%)
;;;                    Overhead used : 1.948220 ns
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
;;;   compilation occured before 1599 iterations
;;;   compilation occured before 6393 iterations
;;;   compilation occured before 52735 iterations
;;;   compilation occured before 76705 iterations
;;;   compilation occured before 78303 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 50.22652185594409 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 18456 in 6 samples of 3076 calls.
;;;              Execution time mean : 32.889433 µs
;;;     Execution time std-deviation : 1.284243 µs
;;;    Execution time lower quantile : 31.354063 µs ( 2.5%)
;;;    Execution time upper quantile : 34.500190 µs (97.5%)
;;;                    Overhead used : 1.948220 ns
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
;;;   compilation occured before 2620 iterations
;;;   compilation occured before 5239 iterations
;;;   compilation occured before 7858 iterations
;;;   compilation occured before 261901 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 48.89832184412512 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Evaluation count : 91296 in 6 samples of 15216 calls.
;;;              Execution time mean : 6.728451 µs
;;;     Execution time std-deviation : 196.027500 ns
;;;    Execution time lower quantile : 6.540199 µs ( 2.5%)
;;;    Execution time upper quantile : 6.938209 µs (97.5%)
;;;                    Overhead used : 1.948220 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
;;[:plus [:times 2.0 [:times 'x 'x]] [:times 3.0 'y]]
(def ^algebolic.expression.JExpr e (interpreter/->jexpr '[x y] 'x))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/e</span>","value":"#'balmy-hurricane/e"}
;; <=

;; @@
(.evaluateD e [2.0 4.0])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;double[] [D@4c92d5f8&gt;</span>","value":"#<double[] [D@4c92d5f8>"}
;; <=

;; @@
(criterium/quick-bench (.evaluate e [2.0 3.0]))
;; @@
;; ->
;;; WARNING: Final GC required 37.776830749692245 % of runtime
;;; Evaluation count : 55321560 in 6 samples of 9220260 calls.
;;;              Execution time mean : 9.124624 ns
;;;     Execution time std-deviation : 0.466675 ns
;;;    Execution time lower quantile : 8.313401 ns ( 2.5%)
;;;    Execution time upper quantile : 9.519116 ns (97.5%)
;;;                    Overhead used : 1.940249 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(interpreter/evaluate-d expr vars coords)
;; @@

;; @@

;; @@
