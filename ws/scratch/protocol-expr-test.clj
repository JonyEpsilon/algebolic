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
(ns icy-winter4
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
  (evaluate ^Double [expr ^Double vars]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-symbol'>Eval</span>","value":"Eval"}
;; <=

;; @@
(deftype Plus [a1 a2]
  Eval
  (evaluate [expr vars] (+ (evaluate a1 vars) (evaluate a2 vars))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter4.Plus</span>","value":"icy_winter4.Plus"}
;; <=

;; @@
(deftype Times [a1 a2]
  Eval
  (evaluate [expr vars] (* (evaluate a1 vars) (evaluate a2 vars))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter4.Times</span>","value":"icy_winter4.Times"}
;; <=

;; @@
(deftype Const [c]
  Eval
  (evaluate [expr vars] c))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter4.Const</span>","value":"icy_winter4.Const"}
;; <=

;; @@
(deftype Var []
  Eval
  (evaluate [expr vars] vars))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>icy_winter4.Var</span>","value":"icy_winter4.Var"}
;; <=

;; @@
(def expr (->Plus (->Times (->Const 3.0) (->Const 4.0)) (->Const 5.0)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;icy-winter4/expr</span>","value":"#'icy-winter4/expr"}
;; <=

;; @@
(evaluate expr 3.0)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>17.0</span>","value":"17.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (evaluate expr 3.0)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 52746 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 45.81696803051546 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 15336132 in 6 samples of 2556022 calls.
;;;       Execution time sample mean : 39.035511 ns
;;;              Execution time mean : 38.981717 ns
;;; Execution time sample std-deviation : 1.462412 ns
;;;     Execution time std-deviation : 1.552678 ns
;;;    Execution time lower quantile : 37.092453 ns ( 2.5%)
;;;    Execution time upper quantile : 40.722459 ns (97.5%)
;;;                    Overhead used : 1.895866 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
Eval
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:on</span>","value":":on"},{"type":"html","content":"<span class='clj-symbol'>icy_winter4.Eval</span>","value":"icy_winter4.Eval"}],"value":"[:on icy_winter4.Eval]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:on-interface</span>","value":":on-interface"},{"type":"html","content":"<span class='clj-class'>icy_winter4.Eval</span>","value":"icy_winter4.Eval"}],"value":"[:on-interface icy_winter4.Eval]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:sigs</span>","value":":sigs"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:evaluate</span>","value":":evaluate"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:doc</span>","value":":doc"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[:doc nil]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:arglists</span>","value":":arglists"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-symbol'>expr</span>","value":"expr"},{"type":"html","content":"<span class='clj-symbol'>vars</span>","value":"vars"}],"value":"[expr vars]"}],"value":"([expr vars])"}],"value":"[:arglists ([expr vars])]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-symbol'>evaluate</span>","value":"evaluate"}],"value":"[:name evaluate]"}],"value":"{:doc nil, :arglists ([expr vars]), :name evaluate}"}],"value":"[:evaluate {:doc nil, :arglists ([expr vars]), :name evaluate}]"}],"value":"{:evaluate {:doc nil, :arglists ([expr vars]), :name evaluate}}"}],"value":"[:sigs {:evaluate {:doc nil, :arglists ([expr vars]), :name evaluate}}]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:var</span>","value":":var"},{"type":"html","content":"<span class='clj-var'>#&#x27;icy-winter4/Eval</span>","value":"#'icy-winter4/Eval"}],"value":"[:var #'icy-winter4/Eval]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:method-map</span>","value":":method-map"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:evaluate</span>","value":":evaluate"},{"type":"html","content":"<span class='clj-keyword'>:evaluate</span>","value":":evaluate"}],"value":"[:evaluate :evaluate]"}],"value":"{:evaluate :evaluate}"}],"value":"[:method-map {:evaluate :evaluate}]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:method-builders</span>","value":":method-builders"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;icy-winter4/evaluate</span>","value":"#'icy-winter4/evaluate"},{"type":"html","content":"<span class='clj-unkown'>#&lt;icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3&gt;</span>","value":"#<icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3>"}],"value":"[#'icy-winter4/evaluate #<icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3>]"}],"value":"{#'icy-winter4/evaluate #<icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3>}"}],"value":"[:method-builders {#'icy-winter4/evaluate #<icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3>}]"}],"value":"{:on icy_winter4.Eval, :on-interface icy_winter4.Eval, :sigs {:evaluate {:doc nil, :arglists ([expr vars]), :name evaluate}}, :var #'icy-winter4/Eval, :method-map {:evaluate :evaluate}, :method-builders {#'icy-winter4/evaluate #<icy_winter4$eval7596$fn__7597 icy_winter4$eval7596$fn__7597@197683f3>}}"}
;; <=

;; @@

;; @@
