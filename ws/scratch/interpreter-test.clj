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
(def expr [:plus [:times [:var 0] [:var 0]] [:var 0]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr</span>","value":"#'balmy-hurricane/expr"}
;; <=

;; @@
expr
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:var</span>","value":":var"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:var 0]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:var</span>","value":":var"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:var 0]"}],"value":"[:times [:var 0] [:var 0]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:var</span>","value":":var"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:var 0]"}],"value":"[:plus [:times [:var 0] [:var 0]] [:var 0]]"}
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

;; @@
(time (do (doall (repeatedly 100 #(evaluate/functionalise expr '[x]))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 106.656 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(f 3))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 0.399 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate expr [3]))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 49.118 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(interpreter/evaluate expr [7])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>56</span>","value":"56"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate expr [3.0])
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 210546 iterations
;;;   compilation occured before 27157874 iterations
;;;   compilation occured before 27368400 iterations
;;;   compilation occured before 27578926 iterations
;;;   compilation occured before 27789452 iterations
;;;   compilation occured before 27999978 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 48.9934217651921 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 3881214 in 6 samples of 646869 calls.
;;;       Execution time sample mean : 154.520584 ns
;;;              Execution time mean : 154.514142 ns
;;; Execution time sample std-deviation : 2.010962 ns
;;;     Execution time std-deviation : 2.107593 ns
;;;    Execution time lower quantile : 152.522755 ns ( 2.5%)
;;;    Execution time upper quantile : 156.804920 ns (97.5%)
;;;                    Overhead used : 1.904185 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
