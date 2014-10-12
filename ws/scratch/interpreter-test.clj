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
;;;   compilation occured before 129884 iterations
;;;   compilation occured before 259754 iterations
;;;   compilation occured before 389624 iterations
;;;   compilation occured before 11038964 iterations
;;;   compilation occured before 11168834 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 63.09181317676532 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 3901110 in 6 samples of 650185 calls.
;;;       Execution time sample mean : 150.557777 ns
;;;              Execution time mean : 150.531630 ns
;;; Execution time sample std-deviation : 2.173764 ns
;;;     Execution time std-deviation : 2.264377 ns
;;;    Execution time lower quantile : 148.153076 ns ( 2.5%)
;;;    Execution time upper quantile : 153.176454 ns (97.5%)
;;;                    Overhead used : 1.902678 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (:op q)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 1449375 iterations
;;;   compilation occured before 143478325 iterations
;;;   compilation occured before 144927600 iterations
;;;   compilation occured before 146376875 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 60.42802284031966 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 65660418 in 6 samples of 10943403 calls.
;;;       Execution time sample mean : 7.074298 ns
;;;              Execution time mean : 7.074298 ns
;;; Execution time sample std-deviation : 0.086107 ns
;;;     Execution time std-deviation : 0.091621 ns
;;;    Execution time lower quantile : 6.963394 ns ( 2.5%)
;;;    Execution time upper quantile : 7.205287 ns (97.5%)
;;;                    Overhead used : 1.902678 ns
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
(def ir interpreter/itr)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/ir</span>","value":"#'balmy-hurricane/ir"}
;; <=

;; @@
(def q (ir 0 (ir 2 (ir 7 0) (ir 7 0)) (ir 7 0)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/q</span>","value":"#'balmy-hurricane/q"}
;; <=

;; @@
q
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-record'>#algebolic.expression.interpreter.Instruction{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:op</span>","value":":op"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:op 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg1</span>","value":":arg1"},{"type":"list-like","open":"<span class='clj-record'>#algebolic.expression.interpreter.Instruction{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:op</span>","value":":op"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[:op 2]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg1</span>","value":":arg1"},{"type":"list-like","open":"<span class='clj-record'>#algebolic.expression.interpreter.Instruction{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:op</span>","value":":op"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:op 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg1</span>","value":":arg1"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:arg1 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg2</span>","value":":arg2"},{"type":"html","content":"<span class='clj-double'>0.0</span>","value":"0.0"}],"value":"[:arg2 0.0]"}],"value":"#algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}"}],"value":"[:arg1 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg2</span>","value":":arg2"},{"type":"list-like","open":"<span class='clj-record'>#algebolic.expression.interpreter.Instruction{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:op</span>","value":":op"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:op 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg1</span>","value":":arg1"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:arg1 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg2</span>","value":":arg2"},{"type":"html","content":"<span class='clj-double'>0.0</span>","value":"0.0"}],"value":"[:arg2 0.0]"}],"value":"#algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}"}],"value":"[:arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}]"}],"value":"#algebolic.expression.interpreter.Instruction{:op 2, :arg1 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}, :arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}}"}],"value":"[:arg1 #algebolic.expression.interpreter.Instruction{:op 2, :arg1 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}, :arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}}]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg2</span>","value":":arg2"},{"type":"list-like","open":"<span class='clj-record'>#algebolic.expression.interpreter.Instruction{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:op</span>","value":":op"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"}],"value":"[:op 7]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg1</span>","value":":arg1"},{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"}],"value":"[:arg1 0]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arg2</span>","value":":arg2"},{"type":"html","content":"<span class='clj-double'>0.0</span>","value":"0.0"}],"value":"[:arg2 0.0]"}],"value":"#algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}"}],"value":"[:arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}]"}],"value":"#algebolic.expression.interpreter.Instruction{:op 0, :arg1 #algebolic.expression.interpreter.Instruction{:op 2, :arg1 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}, :arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}}, :arg2 #algebolic.expression.interpreter.Instruction{:op 7, :arg1 0, :arg2 0.0}}"}
;; <=

;; @@
(interpreter/evaluate-rec q [3.0])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>12.0</span>","value":"12.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate-rec q [3.0])
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 33 iterations
;;;   compilation occured before 149509 iterations
;;;   compilation occured before 448461 iterations
;;;   compilation occured before 1345317 iterations
;;;   compilation occured before 19282437 iterations
;;;   compilation occured before 19431913 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 32.98901319450617 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 4671618 in 6 samples of 778603 calls.
;;;       Execution time sample mean : 129.318231 ns
;;;              Execution time mean : 129.282911 ns
;;; Execution time sample std-deviation : 2.842865 ns
;;;     Execution time std-deviation : 2.955372 ns
;;;    Execution time lower quantile : 125.914485 ns ( 2.5%)
;;;    Execution time upper quantile : 132.860098 ns (97.5%)
;;;                    Overhead used : 1.904185 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
