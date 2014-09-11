;; gorilla-repl.fileformat = 1

;; **
;;; # Simple symbolic regression
;;; 
;;; This worksheet demonstrates how to run a symbolic regression with a basic tournament selection GA.
;; **

;; @@
(ns algebolic.demo.simple-sr
  (:require [gorilla-plot.core :as plot]
            [algebolic.expression.core :as expression]
            [algebolic.expression.genetics :as genetics]
            [algebolic.expression.score :as score]
            [algebolic.evolution.core :as evolution]
            [algebolic.evolution.metrics :as metrics]
            [algebolic.evolution.reproduction :as reproduction]
            [algebolic.evolution.scoring :as scoring]
            [algebolic.evolution.selection :as selection]
            [algebolic.algorithms.sso :as sso]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def data (map (fn [x] [[x] (+ (* 2 x) (* 3 x x))]) (range 10)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/data</span>","value":"#'algebolic.demo.simple-sr/data"}
;; <=

;; @@
(def run-config
  (let [vars '[x]
        functions expression/function-symbols
        terminals (expression/make-terminals vars)
        ea-config (sso/sso-ea-config
                    {:tournament-size 5
                     :goal :error-pp
                     :binary-ops [{:op genetics/simple-crossover :repeat 40}]
                     :unary-ops [{:op (partial genetics/random-tree-mutation functions terminals 10) :repeat 2}
                                 {:op identity :repeat 10}]})]
    {:initial-zeitgeist      (evolution/make-zeitgeist
                               (genetics/make-initial-population functions terminals 100 5))
     :ea-config              ea-config
     :score-functions        {:complexity score/size
                              :error (partial score/abs-error vars data)
                              :error-pp (partial score/abs-error-pp vars data 5)}
     :metric-update-function metrics/update-zeitgeist-age
     :stopping-condition     #(>= (:age %) 10)
     :reporting-function     (fn [z] (print ".") (flush))
     :checkpoint-function    nil}))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/run-config</span>","value":"#'algebolic.demo.simple-sr/run-config"}
;; <=

;; @@
(def zf (evolution/run-evolution run-config))
;; @@
;; ->
;;; ..........
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/zf</span>","value":"#'algebolic.demo.simple-sr/zf"}
;; <=

;; @@
(sort (map :error (:rabble zf)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-93471.82114755202</span>","value":"-93471.82114755202"},{"type":"html","content":"<span class='clj-double'>-18135.0</span>","value":"-18135.0"},{"type":"html","content":"<span class='clj-double'>-17945.028537887483</span>","value":"-17945.028537887483"},{"type":"html","content":"<span class='clj-double'>-11205.0</span>","value":"-11205.0"},{"type":"html","content":"<span class='clj-double'>-5725.607160583327</span>","value":"-5725.607160583327"},{"type":"html","content":"<span class='clj-double'>-5700.0</span>","value":"-5700.0"},{"type":"html","content":"<span class='clj-double'>-5700.0</span>","value":"-5700.0"},{"type":"html","content":"<span class='clj-double'>-5700.0</span>","value":"-5700.0"},{"type":"html","content":"<span class='clj-double'>-5652.492427148469</span>","value":"-5652.492427148469"},{"type":"html","content":"<span class='clj-double'>-5429.8976239588765</span>","value":"-5429.8976239588765"},{"type":"html","content":"<span class='clj-double'>-1620.0</span>","value":"-1620.0"},{"type":"html","content":"<span class='clj-double'>-1620.0</span>","value":"-1620.0"},{"type":"html","content":"<span class='clj-double'>-906.7976364828257</span>","value":"-906.7976364828257"},{"type":"html","content":"<span class='clj-double'>-900.558357860826</span>","value":"-900.558357860826"},{"type":"html","content":"<span class='clj-double'>-900.558357860826</span>","value":"-900.558357860826"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-765.0</span>","value":"-765.0"},{"type":"html","content":"<span class='clj-double'>-765.0</span>","value":"-765.0"},{"type":"html","content":"<span class='clj-double'>-765.0</span>","value":"-765.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-660.0</span>","value":"-660.0"},{"type":"html","content":"<span class='clj-double'>-610.4476158962975</span>","value":"-610.4476158962975"},{"type":"html","content":"<span class='clj-double'>-600.212130290904</span>","value":"-600.212130290904"},{"type":"html","content":"<span class='clj-double'>-522.1416963221923</span>","value":"-522.1416963221923"},{"type":"html","content":"<span class='clj-double'>-521.1898985703554</span>","value":"-521.1898985703554"},{"type":"html","content":"<span class='clj-double'>-480.0</span>","value":"-480.0"},{"type":"html","content":"<span class='clj-double'>-480.0</span>","value":"-480.0"},{"type":"html","content":"<span class='clj-double'>-480.0</span>","value":"-480.0"},{"type":"html","content":"<span class='clj-double'>-377.51261037371705</span>","value":"-377.51261037371705"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-364.80626768875766</span>","value":"-364.80626768875766"},{"type":"html","content":"<span class='clj-double'>-363.9978664959308</span>","value":"-363.9978664959308"},{"type":"html","content":"<span class='clj-double'>-343.42073859250206</span>","value":"-343.42073859250206"},{"type":"html","content":"<span class='clj-double'>-317.0608444154908</span>","value":"-317.0608444154908"},{"type":"html","content":"<span class='clj-double'>-197.0</span>","value":"-197.0"},{"type":"html","content":"<span class='clj-double'>-197.0</span>","value":"-197.0"},{"type":"html","content":"<span class='clj-double'>-196.7766568556696</span>","value":"-196.7766568556696"},{"type":"html","content":"<span class='clj-double'>-141.2884634173683</span>","value":"-141.2884634173683"},{"type":"html","content":"<span class='clj-double'>-116.5466153782876</span>","value":"-116.5466153782876"},{"type":"html","content":"<span class='clj-double'>-93.88982472815758</span>","value":"-93.88982472815758"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-90.0</span>","value":"-90.0"},{"type":"html","content":"<span class='clj-double'>-89.98482049119633</span>","value":"-89.98482049119633"},{"type":"html","content":"<span class='clj-double'>-89.98482049119633</span>","value":"-89.98482049119633"},{"type":"html","content":"<span class='clj-double'>-64.28624530933507</span>","value":"-64.28624530933507"},{"type":"html","content":"<span class='clj-double'>-64.28624530933507</span>","value":"-64.28624530933507"},{"type":"html","content":"<span class='clj-double'>-51.07065910157143</span>","value":"-51.07065910157143"},{"type":"html","content":"<span class='clj-double'>-45.558357860826035</span>","value":"-45.558357860826035"},{"type":"html","content":"<span class='clj-double'>-45.558357860826035</span>","value":"-45.558357860826035"},{"type":"html","content":"<span class='clj-double'>-45.558357860826035</span>","value":"-45.558357860826035"}],"value":"(-93471.82114755202 -18135.0 -17945.028537887483 -11205.0 -5725.607160583327 -5700.0 -5700.0 -5700.0 -5652.492427148469 -5429.8976239588765 -1620.0 -1620.0 -906.7976364828257 -900.558357860826 -900.558357860826 -900.0 -900.0 -900.0 -900.0 -900.0 -900.0 -900.0 -765.0 -765.0 -765.0 -660.0 -660.0 -660.0 -660.0 -660.0 -660.0 -610.4476158962975 -600.212130290904 -522.1416963221923 -521.1898985703554 -480.0 -480.0 -480.0 -377.51261037371705 -375.0 -375.0 -375.0 -375.0 -375.0 -364.80626768875766 -363.9978664959308 -343.42073859250206 -317.0608444154908 -197.0 -197.0 -196.7766568556696 -141.2884634173683 -116.5466153782876 -93.88982472815758 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -90.0 -89.98482049119633 -89.98482049119633 -64.28624530933507 -64.28624530933507 -51.07065910157143 -45.558357860826035 -45.558357860826035 -45.558357860826035)"}
;; <=

;; @@

;; @@
