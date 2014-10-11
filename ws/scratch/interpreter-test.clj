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
;;; &quot;Elapsed time: 105.516 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100 #(f 3))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 0.485 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(time (do (doall (repeatedly 100000 #(interpreter/evaluate-expr expr {'x 3}))) :done))
;; @@
;; ->
;;; &quot;Elapsed time: 85.005 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:done</span>","value":":done"}
;; <=

;; @@
(def expr-op [0 [2 [7 0] [7 0]] [7 0]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr-op</span>","value":"#'balmy-hurricane/expr-op"}
;; <=

;; @@
(def expr-rand (genetics/random-full-tree expression/function-symbols (expression/make-terminals '[]) 3))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/expr-rand</span>","value":"#'balmy-hurricane/expr-rand"}
;; <=

;; @@
expr-rand
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-double'>0.8256818150528591</span>","value":"0.8256818150528591"}],"value":"[5 0.8256818150528591]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-double'>0.1071526504653082</span>","value":"0.1071526504653082"},{"type":"html","content":"<span class='clj-double'>0.673356164221405</span>","value":"0.673356164221405"}],"value":"[0 0.1071526504653082 0.673356164221405]"}],"value":"[2 [5 0.8256818150528591] [0 0.1071526504653082 0.673356164221405]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-double'>0.1296522244597127</span>","value":"0.1296522244597127"},{"type":"html","content":"<span class='clj-double'>1.3276998394044681</span>","value":"1.3276998394044681"}],"value":"[1 0.1296522244597127 1.3276998394044681]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-double'>0.4085790582679376</span>","value":"0.4085790582679376"},{"type":"html","content":"<span class='clj-double'>1.7005170514497567</span>","value":"1.7005170514497567"}],"value":"[1 0.4085790582679376 1.7005170514497567]"}],"value":"[0 [1 0.1296522244597127 1.3276998394044681] [1 0.4085790582679376 1.7005170514497567]]"}],"value":"[0 [2 [5 0.8256818150528591] [0 0.1071526504653082 0.673356164221405]] [0 [1 0.1296522244597127 1.3276998394044681] [1 0.4085790582679376 1.7005170514497567]]]"}
;; <=

;; @@
(interpreter/evaluate expr-op [7.0])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>56.0</span>","value":"56.0"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate expr-op [3.0])
    :verbose))
;; @@
;; ->
;;; Estimating sampling overhead
;;; Warming up for JIT optimisations 10000000000 ...
;;;   classes loaded before 33 iterations
;;;   compilation occured before 737 iterations
;;;   compilation occured before 1441 iterations
;;;   compilation occured before 2145 iterations
;;;   compilation occured before 16225 iterations
;;;   compilation occured before 16929 iterations
;;;   compilation occured before 27489 iterations
;;;   compilation occured before 28193 iterations
;;;   compilation occured before 28897 iterations
;;;   compilation occured before 36641 iterations
;;;   compilation occured before 38753 iterations
;;;   compilation occured before 48609 iterations
;;;   compilation occured before 61281 iterations
;;;   compilation occured before 64097 iterations
;;;   compilation occured before 68321 iterations
;;;   compilation occured before 69025 iterations
;;;   compilation occured before 77473 iterations
;;;   compilation occured before 78177 iterations
;;;   compilation occured before 78881 iterations
;;;   compilation occured before 79585 iterations
;;;   compilation occured before 80289 iterations
;;;   compilation occured before 80993 iterations
;;;   compilation occured before 81697 iterations
;;;   compilation occured before 82401 iterations
;;;   compilation occured before 83105 iterations
;;;   compilation occured before 83809 iterations
;;;   compilation occured before 84513 iterations
;;;   compilation occured before 85217 iterations
;;;   compilation occured before 85921 iterations
;;;   compilation occured before 86625 iterations
;;;   compilation occured before 87329 iterations
;;;   compilation occured before 88033 iterations
;;;   compilation occured before 88737 iterations
;;;   compilation occured before 89441 iterations
;;;   compilation occured before 95073 iterations
;;;   compilation occured before 95777 iterations
;;;   compilation occured before 96481 iterations
;;;   compilation occured before 97185 iterations
;;;   compilation occured before 97889 iterations
;;;   compilation occured before 98593 iterations
;;;   compilation occured before 99297 iterations
;;;   compilation occured before 100705 iterations
;;;   compilation occured before 101409 iterations
;;;   compilation occured before 102113 iterations
;;;   compilation occured before 102817 iterations
;;;   compilation occured before 104225 iterations
;;;   compilation occured before 104929 iterations
;;;   compilation occured before 105633 iterations
;;;   compilation occured before 107745 iterations
;;;   compilation occured before 108449 iterations
;;;   compilation occured before 111265 iterations
;;;   compilation occured before 111969 iterations
;;;   compilation occured before 130273 iterations
;;;   compilation occured before 130977 iterations
;;;   compilation occured before 137313 iterations
;;;   compilation occured before 138017 iterations
;;;   compilation occured before 138721 iterations
;;;   compilation occured before 161249 iterations
;;;   compilation occured before 180257 iterations
;;;   compilation occured before 182369 iterations
;;;   compilation occured before 183073 iterations
;;;   compilation occured before 188705 iterations
;;;   compilation occured before 189409 iterations
;;;   compilation occured before 190113 iterations
;;;   compilation occured before 192225 iterations
;;;   compilation occured before 194337 iterations
;;;   compilation occured before 197153 iterations
;;;   compilation occured before 197857 iterations
;;;   compilation occured before 198561 iterations
;;;   compilation occured before 199265 iterations
;;;   compilation occured before 199969 iterations
;;;   compilation occured before 200673 iterations
;;;   compilation occured before 202785 iterations
;;;   compilation occured before 203489 iterations
;;;   compilation occured before 204193 iterations
;;;   compilation occured before 204897 iterations
;;;   compilation occured before 206305 iterations
;;;   compilation occured before 208417 iterations
;;;   compilation occured before 210529 iterations
;;;   compilation occured before 213345 iterations
;;;   compilation occured before 222497 iterations
;;;   compilation occured before 247137 iterations
;;;   compilation occured before 247841 iterations
;;;   compilation occured before 268257 iterations
;;;   compilation occured before 268961 iterations
;;;   compilation occured before 269665 iterations
;;;   compilation occured before 270369 iterations
;;;   compilation occured before 271073 iterations
;;;   compilation occured before 272481 iterations
;;;   compilation occured before 276001 iterations
;;;   compilation occured before 282337 iterations
;;;   compilation occured before 292193 iterations
;;;   compilation occured before 350625 iterations
;;;   compilation occured before 359073 iterations
;;;   compilation occured before 361185 iterations
;;;   compilation occured before 362593 iterations
;;;   compilation occured before 365409 iterations
;;;   compilation occured before 377377 iterations
;;;   compilation occured before 382305 iterations
;;;   compilation occured before 391457 iterations
;;;   compilation occured before 412577 iterations
;;;   compilation occured before 427361 iterations
;;;   compilation occured before 428065 iterations
;;;   compilation occured before 439329 iterations
;;;   compilation occured before 465377 iterations
;;;   compilation occured before 469601 iterations
;;;   compilation occured before 473121 iterations
;;;   compilation occured before 481569 iterations
;;;   compilation occured before 482273 iterations
;;;   compilation occured before 485793 iterations
;;;   compilation occured before 490721 iterations
;;;   compilation occured before 491425 iterations
;;;   compilation occured before 492129 iterations
;;;   compilation occured before 492833 iterations
;;;   compilation occured before 510433 iterations
;;;   compilation occured before 513953 iterations
;;;   compilation occured before 520289 iterations
;;;   compilation occured before 520993 iterations
;;;   compilation occured before 521697 iterations
;;;   compilation occured before 522401 iterations
;;;   compilation occured before 543521 iterations
;;;   compilation occured before 548449 iterations
;;;   compilation occured before 557601 iterations
;;;   compilation occured before 564641 iterations
;;;   compilation occured before 570273 iterations
;;;   compilation occured before 570977 iterations
;;;   compilation occured before 582945 iterations
;;;   compilation occured before 628705 iterations
;;;   compilation occured before 630817 iterations
;;;   compilation occured before 631521 iterations
;;;   compilation occured before 632929 iterations
;;;   compilation occured before 637153 iterations
;;;   compilation occured before 639969 iterations
;;;   compilation occured before 655457 iterations
;;;   compilation occured before 671649 iterations
;;;   compilation occured before 672353 iterations
;;;   compilation occured before 680801 iterations
;;;   compilation occured before 713889 iterations
;;;   compilation occured before 731489 iterations
;;;   compilation occured before 746273 iterations
;;;   compilation occured before 747681 iterations
;;;   compilation occured before 751905 iterations
;;;   compilation occured before 756833 iterations
;;;   compilation occured before 757537 iterations
;;;   compilation occured before 775137 iterations
;;;   compilation occured before 776545 iterations
;;;   compilation occured before 777249 iterations
;;;   compilation occured before 780065 iterations
;;;   compilation occured before 794849 iterations
;;;   compilation occured before 800481 iterations
;;;   compilation occured before 839201 iterations
;;;   compilation occured before 858209 iterations
;;;   compilation occured before 870177 iterations
;;;   compilation occured before 886369 iterations
;;;   compilation occured before 889185 iterations
;;;   compilation occured before 889889 iterations
;;;   compilation occured before 893409 iterations
;;;   compilation occured before 1067297 iterations
;;;   compilation occured before 1068705 iterations
;;;   compilation occured before 1081377 iterations
;;;   compilation occured before 1116577 iterations
;;;   compilation occured before 1168673 iterations
;;;   compilation occured before 1197537 iterations
;;;   compilation occured before 1246817 iterations
;;;   compilation occured before 1315809 iterations
;;;   compilation occured before 1318625 iterations
;;;   compilation occured before 1327777 iterations
;;;   compilation occured before 1328481 iterations
;;;   compilation occured before 1348897 iterations
;;;   compilation occured before 1384097 iterations
;;;   compilation occured before 1462945 iterations
;;;   compilation occured before 1488289 iterations
;;;   compilation occured before 1488993 iterations
;;;   compilation occured before 1491105 iterations
;;;   compilation occured before 1495329 iterations
;;;   compilation occured before 1496033 iterations
;;;   compilation occured before 1496737 iterations
;;;   compilation occured before 1497441 iterations
;;;   compilation occured before 1499553 iterations
;;;   compilation occured before 1512929 iterations
;;;   compilation occured before 1564321 iterations
;;;   compilation occured before 1574881 iterations
;;;   compilation occured before 1576993 iterations
;;;   compilation occured before 1595297 iterations
;;;   compilation occured before 1967009 iterations
;;;   compilation occured before 2001505 iterations
;;;   compilation occured before 2112737 iterations
;;;   compilation occured before 2359841 iterations
;;;   compilation occured before 3009633 iterations
;;;   compilation occured before 3011041 iterations
;;;   compilation occured before 3015265 iterations
;;;   compilation occured before 3015969 iterations
;;;   compilation occured before 3016673 iterations
;;;   compilation occured before 3024417 iterations
;;;   compilation occured before 3025121 iterations
;;;   compilation occured before 3047649 iterations
;;;   compilation occured before 3061729 iterations
;;;   compilation occured before 3063137 iterations
;;;   compilation occured before 3228577 iterations
;;;   compilation occured before 3244769 iterations
;;;   compilation occured before 3257441 iterations
;;;   compilation occured before 3332769 iterations
;;;   compilation occured before 3334881 iterations
;;;   compilation occured before 3772769 iterations
;;;   compilation occured before 3875553 iterations
;;;   compilation occured before 3886113 iterations
;;;   compilation occured before 3903009 iterations
;;;   compilation occured before 3995233 iterations
;;;   compilation occured before 4162785 iterations
;;;   compilation occured before 4171233 iterations
;;;   compilation occured before 4235297 iterations
;;;   compilation occured before 4563361 iterations
;;;   compilation occured before 4564065 iterations
;;;   compilation occured before 4614753 iterations
;;;   compilation occured before 4616161 iterations
;;;   compilation occured before 6261409 iterations
;;;   compilation occured before 6262113 iterations
;;;   compilation occured before 6272673 iterations
;;;   compilation occured before 6277601 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 7.911226632409852 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 9617 iterations
;;;   compilation occured before 19232 iterations
;;;   compilation occured before 28847 iterations
;;;   compilation occured before 105767 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 58.859411323863874 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 23070 in 6 samples of 3845 calls.
;;;       Execution time sample mean : 30.397383 µs
;;;              Execution time mean : 30.288194 µs
;;; Execution time sample std-deviation : 2.817246 µs
;;;     Execution time std-deviation : 2.994700 µs
;;;    Execution time lower quantile : 26.537002 µs ( 2.5%)
;;;    Execution time upper quantile : 33.572340 µs (97.5%)
;;;                    Overhead used : 551.945046 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (interpreter/evaluate-expr expr {'x 3.0})
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 59112 iterations
;;;   compilation occured before 709223 iterations
;;;   compilation occured before 4728091 iterations
;;;   compilation occured before 4787192 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 47.77715106005032 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 1066638 in 6 samples of 177773 calls.
;;;       Execution time sample mean : 575.091604 ns
;;;              Execution time mean : 575.034415 ns
;;; Execution time sample std-deviation : 14.042246 ns
;;;     Execution time std-deviation : 15.403887 ns
;;;    Execution time lower quantile : 559.644001 ns ( 2.5%)
;;;    Execution time upper quantile : 590.080288 ns (97.5%)
;;;                    Overhead used : 1.909220 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defn clj-f [x y] (+ (* x x) x))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;balmy-hurricane/clj-f</span>","value":"#'balmy-hurricane/clj-f"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (clj-f 3.0 0.0)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 1176570 iterations
;;;   compilation occured before 2353040 iterations
;;;   compilation occured before 150588260 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 56.113304559123144 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 43507182 in 6 samples of 7251197 calls.
;;;       Execution time sample mean : 12.353299 ns
;;;              Execution time mean : 12.364653 ns
;;; Execution time sample std-deviation : 0.513644 ns
;;;     Execution time std-deviation : 0.540314 ns
;;;    Execution time lower quantile : 11.858714 ns ( 2.5%)
;;;    Execution time upper quantile : 13.156034 ns (97.5%)
;;;                    Overhead used : 1.909220 ns
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(criterium/with-progress-reporting
  (criterium/quick-bench
    (case :one
      :one 1
      :two 2)
    :verbose))
;; @@
;; ->
;;; Warming up for JIT optimisations 5000000000 ...
;;;   compilation occured before 1470638 iterations
;;;   compilation occured before 2941226 iterations
;;; Estimating execution count ...
;;; Sampling ...
;;; Final GC...
;;; Checking GC...
;;; WARNING: Final GC required 58.989921054855465 % of runtime
;;; Finding outliers ...
;;; Bootstrapping ...
;;; Checking outlier significance
;;; x86_64 Mac OS X 10.9.5 4 cpu(s)
;;; Java HotSpot(TM) 64-Bit Server VM 25.5-b02
;;; Runtime arguments: -Dfile.encoding=UTF-8 -Xmx4g -Dclojure.compile.path=/Users/jony/Documents/Work/Computing/ProjectX/Projects/algebolic/target/classes -Dalgebolic.version=0.1.0-SNAPSHOT -Dclojure.debug=false
;;; Evaluation count : 65559402 in 6 samples of 10926567 calls.
;;;       Execution time sample mean : 7.498919 ns
;;;              Execution time mean : 7.495533 ns
;;; Execution time sample std-deviation : 0.268949 ns
;;;     Execution time std-deviation : 0.270323 ns
;;;    Execution time lower quantile : 6.956327 ns ( 2.5%)
;;;    Execution time upper quantile : 7.664693 ns (97.5%)
;;;                    Overhead used : 1.909220 ns
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
(= "plus" "times")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
:plus
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"}
;; <=

;; @@
(meta :times)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(clojure.lang.Keyword/find "plus")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"}
;; <=

;; @@

;; @@
