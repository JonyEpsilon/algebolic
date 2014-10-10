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
            [algebolic.expression.genetics :as genetics]
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
;;; &quot;Elapsed time: 101.653 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(f 3))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 0.397 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate expr {'x 3}))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 126.945 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(def expr-op [0 [2 'x 'x] 'x])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr-op</span>","value":"#'balmy-hurricane/expr-op"}
;; <=

;; @@
(def expr-rand (genetics/random-full-tree expression/function-symbols (expression/make-terminals '[x]) 3))n
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr-rand</span>","value":"#'balmy-hurricane/expr-rand"}
;; <=

;; @@
expr-rand
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[5 x]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-double'>0.524850705310729</span>","value":"0.524850705310729"},{"type":"html","content":"<span class='clj-double'>1.2698858404456062</span>","value":"1.2698858404456062"}],"value":"[3 0.524850705310729 1.2698858404456062]"}],"value":"[2 [5 x] [3 0.524850705310729 1.2698858404456062]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[3 x x]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-double'>0.37708204299715065</span>","value":"0.37708204299715065"},{"type":"html","content":"<span class='clj-double'>0.07413315735714843</span>","value":"0.07413315735714843"}],"value":"[0 0.37708204299715065 0.07413315735714843]"}],"value":"[3 [3 x x] [0 0.37708204299715065 0.07413315735714843]]"}],"value":"[2 [2 [5 x] [3 0.524850705310729 1.2698858404456062]] [3 [3 x x] [0 0.37708204299715065 0.07413315735714843]]]"}
;; <=

;; @@
(interpreter/evaluate expr-rand {'x 7})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>0.6905615998753486</span>","value":"0.6905615998753486"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate expr-rand {'x 3.0})
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 34945 iterations
;;;   compilation occured before 69885 iterations
;;;   compilation occured before 104825 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 44.68666692567796 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 1125276 in 6 samples of 187546 calls.
;;;       Execution time sample mean : 541.688178 ns
;;;              Execution time mean : 541.610863 ns
;;; Execution time sample std-deviation : 2.203366 ns
;;;     Execution time std-deviation : 2.270891 ns
;;;    Execution time lower quantile : 537.590516 ns ( 2.5%)
;;;    Execution time upper quantile : 543.600375 ns (97.5%)
;;;                    Overhead used : 1.909128 ns
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
(criterium/with-progress-reporting
  (criterium/quick-bench
    (= 1 (nth [1 [1 :x] :x] 0))
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 943446 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 56.05083932026829 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 53900058 in 6 samples of 8983343 calls.
;;;       Execution time sample mean : 9.271787 ns
;;;              Execution time mean : 9.273735 ns
;;; Execution time sample std-deviation : 0.106428 ns
;;;     Execution time std-deviation : 0.108634 ns
;;;    Execution time lower quantile : 9.180841 ns ( 2.5%)
;;;    Execution time upper quantile : 9.403030 ns (97.5%)
;;;                    Overhead used : 1.909128 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(= 'x 'x)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@

;; @@
