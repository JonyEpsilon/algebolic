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
;;;   compilation occured before 632961 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 46.6136484687365 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 4428132 in 6 samples of 738022 calls.
;;;       Execution time sample mean : 139.061126 ns
;;;              Execution time mean : 139.119389 ns
;;; Execution time sample std-deviation : 5.463746 ns
;;;     Execution time std-deviation : 5.701333 ns
;;;    Execution time lower quantile : 133.351946 ns ( 2.5%)
;;;    Execution time upper quantile : 147.370668 ns (97.5%)
;;;                    Overhead used : 1.904185 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
