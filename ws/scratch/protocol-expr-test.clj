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
(ns icy-winter2
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
(defprotocol Eval
  (evaluate [expr vars]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-symbol'>Eval</span>","value":"Eval"}
;; <=

;; @@
(defrecord Plus [a1 a2]
  Eval
  (evaluate [expr vars] (+ (evaluate a1 vars) (evaluate a2 vars))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter2.Plus</span>","value":"icy_winter2.Plus"}
;; <=

;; @@
(defrecord Times [a1 a2]
  Eval
  (evaluate [expr vars] (* (evaluate a1 vars) (evaluate a2 vars))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter2.Times</span>","value":"icy_winter2.Times"}
;; <=

;; @@
(defrecord Const [c]
  Eval
  (evaluate [expr vars] c))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter2.Const</span>","value":"icy_winter2.Const"}
;; <=

;; @@
(def expr (->Plus (->Const 3.0) (->Const 4.0)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;icy-winter2/expr</span>","value":"#'icy-winter2/expr"}
;; <=

;; @@
(evaluate expr [])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>7.0</span>","value":"7.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (evaluate expr [])
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 289888 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 47.59581721710602 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 32073558 in 6 samples of 5345593 calls.
;;;       Execution time sample mean : 17.061090 ns
;;;              Execution time mean : 17.060622 ns
;;; Execution time sample std-deviation : 0.272819 ns
;;;     Execution time std-deviation : 0.285657 ns
;;;    Execution time lower quantile : 16.784008 ns ( 2.5%)
;;;    Execution time upper quantile : 17.325692 ns (97.5%)
;;;                    Overhead used : 1.895866 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
