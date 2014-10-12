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
;;; &quot;Elapsed time: 79.161 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(f 3))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 0.365 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate expr [3]))) :done))
;; @@

;; @@
(interpreter/evaluate expr {'x 7})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>56</span>","value":"56"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate expr {'x 3.0})
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 124237 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 51.87962624405742 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 4054650 in 6 samples of 675775 calls.
;;;       Execution time sample mean : 136.656481 ns
;;;              Execution time mean : 136.492472 ns
;;; Execution time sample std-deviation : 2.006614 ns
;;;     Execution time std-deviation : 2.169094 ns
;;;    Execution time lower quantile : 134.141097 ns ( 2.5%)
;;;    Execution time upper quantile : 138.546410 ns (97.5%)
;;;                    Overhead used : 1.904185 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
