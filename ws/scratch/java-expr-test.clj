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
(ns thriving-mountain
  (:import [algebolic.expression.interpreter Plus Times Var Constant JExpr])
  (:require [gorilla-plot.core :as plot]
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
(def ^doubles arg (double-array [3.0]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/arg</span>","value":"#'thriving-mountain/arg"}
;; <=

;; @@
(defn bje
  [expr]
  (cond
    (number? expr) (Constant. expr)
    true (case (nth expr 0)
           :plus (Plus. (bje (nth expr 1)) (bje (nth expr 2)))
           :times (Plus. (bje (nth expr 1)) (bje (nth expr 2))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/bje</span>","value":"#'thriving-mountain/bje"}
;; <=

;; @@
(def ^Expression exp (bje [:plus [:times 3.0 4.0] 7.0]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;thriving-mountain/exp</span>","value":"#'thriving-mountain/exp"}
;; <=

;; @@
exp
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#&lt;Plus algebolic.expression.interpreter.Plus@250c31ed&gt;</span>","value":"#<Plus algebolic.expression.interpreter.Plus@250c31ed>"}
;; <=

;; @@
(.evaluate exp arg)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>14.0</span>","value":"14.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (.evaluate exp arg)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 20836 iterations
;;;   compilation occured before 62502 iterations
;;;   compilation occured before 2999955 iterations
;;;   compilation occured before 3062454 iterations
;;;   compilation occured before 3124953 iterations
;;;   compilation occured before 3166619 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 51.39028764618517 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 51056208 in 6 samples of 8509368 calls.
;;;       Execution time sample mean : 10.190285 ns
;;;              Execution time mean : 10.195749 ns
;;; Execution time sample std-deviation : 0.397543 ns
;;;     Execution time std-deviation : 0.431341 ns
;;;    Execution time lower quantile : 9.845900 ns ( 2.5%)
;;;    Execution time upper quantile : 10.736565 ns (97.5%)
;;;                    Overhead used : 1.895866 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
