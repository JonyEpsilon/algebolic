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
  (:require [gorilla-plot.core :as plot]
            [clojure.walk :as walk]
            [algebolic.expression.core :as expression]
            [algebolic.expression.interpreter :as interpreter]
            [algebolic.expression.evaluate :as evaluate]
            [criterium.core :as criterium]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
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
(def f (evaluate/functionalise expr '[x]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/f</span>","value":"#'balmy-hurricane/f"}
;; <=

;; @@
(f 3)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>12</span>","value":"12"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(evaluate/functionalise expr '[x]))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 109.865 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(f 3))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 0.432 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate expr {'x 3}))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 14.552 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(interpreter/evaluate expr {'x 7})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>56</span>","value":"56"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate [:plus 4.0 0.0] {'x 3.0})
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 256430 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 45.82883390584968 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 3104574 in 6 samples of 517429 calls.
;;;       Execution time sample mean : 192.297520 ns
;;;              Execution time mean : 192.304928 ns
;;; Execution time sample std-deviation : 2.435945 ns
;;;     Execution time std-deviation : 2.435171 ns
;;;    Execution time lower quantile : 191.026170 ns ( 2.5%)
;;;    Execution time upper quantile : 196.498901 ns (97.5%)
;;;                    Overhead used : 1.587696 ns
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
(defn raw-fn2
  [x]
  (+ (* x x) x))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/raw-fn2</span>","value":"#'balmy-hurricane/raw-fn2"}
;; <=

;; @@
(criterium/with-progress-reporting (criterium/quick-bench (raw-fn2 3.0) :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 961588 iterations
;;;   compilation occured before 2884664 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 50.91737035327709 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 35560038 in 6 samples of 5926673 calls.
;;;       Execution time sample mean : 15.160964 ns
;;;              Execution time mean : 15.154074 ns
;;; Execution time sample std-deviation : 0.439932 ns
;;;     Execution time std-deviation : 0.455624 ns
;;;    Execution time lower quantile : 14.765155 ns ( 2.5%)
;;;    Execution time upper quantile : 15.658130 ns (97.5%)
;;;                    Overhead used : 1.587696 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/with-progress-reporting (criterium/quick-bench (evaluate/functionalise expr '[x]) :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   classes loaded before 1 iterations
;;;   compilation occured before 1 iterations
;;;   classes loaded before 983 iterations
;;;   compilation occured before 983 iterations
;;;   classes loaded before 1965 iterations
;;;   classes loaded before 2947 iterations
;;;   compilation occured before 2947 iterations
;;;   classes loaded before 3929 iterations
;;;   classes loaded before 4911 iterations
;;;   compilation occured before 4911 iterations
;;;   classes loaded before 5893 iterations
;;;   compilation occured before 5893 iterations
;;;   classes loaded before 6875 iterations
;;;   compilation occured before 6875 iterations
;;;   classes loaded before 7857 iterations
;;;   compilation occured before 7857 iterations
;;;   classes loaded before 8839 iterations
;;;   classes loaded before 9821 iterations
;;;   compilation occured before 9821 iterations
;;;   classes loaded before 10803 iterations
;;;   classes loaded before 11785 iterations
;;;   compilation occured before 11785 iterations
;;;   classes loaded before 12767 iterations
;;;   classes loaded before 13749 iterations
;;;   classes loaded before 14731 iterations
;;;   classes loaded before 15713 iterations
;;;   classes loaded before 16695 iterations
;;;   classes loaded before 17677 iterations
;;;   classes loaded before 18659 iterations
;;;   classes loaded before 19641 iterations
;;;   classes loaded before 20623 iterations
;;;   classes loaded before 21605 iterations
;;;   classes loaded before 22587 iterations
;;;   classes loaded before 23569 iterations
;;;   classes loaded before 24551 iterations
;;;   classes loaded before 25533 iterations
;;;   classes loaded before 26515 iterations
;;;   classes loaded before 27497 iterations
;;;   classes loaded before 28479 iterations
;;;   classes loaded before 29461 iterations
;;;   classes loaded before 30443 iterations
;;;   classes loaded before 31425 iterations
;;;   classes loaded before 32407 iterations
;;;   classes loaded before 33389 iterations
;;; 
;; <-

;; @@

;; @@
