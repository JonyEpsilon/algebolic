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
                     :binary-ops [{:op genetics/simple-crossover :repeat 35}]
                     :unary-ops [{:op (partial genetics/random-tree-mutation functions terminals 10) :repeat 10}
                                 {:op identity :repeat 20}]})]
    {:initial-zeitgeist      (evolution/make-zeitgeist
                               (genetics/make-initial-population functions terminals 100 5))
     :ea-config              ea-config
     :score-functions        {:complexity (memoize score/size)
                              :error (memoize (partial score/abs-error vars data))
                              :error-pp (memoize (partial score/abs-error-pp vars data 5))}
     :stopping-condition     #(>= (:age %) 50)
     :reporting-function     (fn [z] (print ".") (flush))}))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/run-config</span>","value":"#'algebolic.demo.simple-sr/run-config"}
;; <=

;; @@
(def metrics (atom {}))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/metrics</span>","value":"#'algebolic.demo.simple-sr/metrics"}
;; <=

;; @@
(def result (evolution/run-evolution run-config metrics))
;; @@
;; ->
;;; ..................................................
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/result</span>","value":"#'algebolic.demo.simple-sr/result"}
;; <=

;; @@
(plot/list-plot (:max (:error @metrics)) :joined true)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"1a82af8c-e959-4497-b3e3-593e63999cbf","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"1a82af8c-e959-4497-b3e3-593e63999cbf","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"1a82af8c-e959-4497-b3e3-593e63999cbf"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"#FF29D2"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"1a82af8c-e959-4497-b3e3-593e63999cbf","values":[{"x":0,"y":-619.1323111763414},{"x":1,"y":-396.8450979567548},{"x":2,"y":-23.4217977377592},{"x":3,"y":-23.4217977377592},{"x":4,"y":-23.4217977377592},{"x":5,"y":-23.4217977377592},{"x":6,"y":-22.377410071797286},{"x":7,"y":-21.289033397839557},{"x":8,"y":-23.4217977377592},{"x":9,"y":-23.4217977377592},{"x":10,"y":-23.4217977377592},{"x":11,"y":-23.4217977377592},{"x":12,"y":-23.4217977377592},{"x":13,"y":-23.4217977377592},{"x":14,"y":-23.4217977377592},{"x":15,"y":-23.4217977377592},{"x":16,"y":-23.4217977377592},{"x":17,"y":-23.4217977377592},{"x":18,"y":-23.4217977377592},{"x":19,"y":-23.4217977377592},{"x":20,"y":-23.4217977377592},{"x":21,"y":-23.4217977377592},{"x":22,"y":-23.4217977377592},{"x":23,"y":-23.4217977377592},{"x":24,"y":-23.4217977377592},{"x":25,"y":-23.4217977377592},{"x":26,"y":-23.4217977377592},{"x":27,"y":-23.4217977377592},{"x":28,"y":-23.4217977377592},{"x":29,"y":-23.4217977377592},{"x":30,"y":-23.4217977377592},{"x":31,"y":-23.4217977377592},{"x":32,"y":-23.4217977377592},{"x":33,"y":-23.4217977377592},{"x":34,"y":-23.4217977377592},{"x":35,"y":-23.4217977377592},{"x":36,"y":-23.4217977377592},{"x":37,"y":-23.4217977377592},{"x":38,"y":-23.4217977377592},{"x":39,"y":-23.4217977377592},{"x":40,"y":-23.4217977377592},{"x":41,"y":-23.4217977377592},{"x":42,"y":-23.4217977377592},{"x":43,"y":-23.4217977377592},{"x":44,"y":-23.4217977377592},{"x":45,"y":-23.4217977377592},{"x":46,"y":-23.4217977377592},{"x":47,"y":-23.4217977377592},{"x":48,"y":-23.4217977377592},{"x":49,"y":-23.4217977377592}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"1a82af8c-e959-4497-b3e3-593e63999cbf\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"1a82af8c-e959-4497-b3e3-593e63999cbf\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"1a82af8c-e959-4497-b3e3-593e63999cbf\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"#FF29D2\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"1a82af8c-e959-4497-b3e3-593e63999cbf\", :values ({:x 0, :y -619.1323111763414} {:x 1, :y -396.8450979567548} {:x 2, :y -23.4217977377592} {:x 3, :y -23.4217977377592} {:x 4, :y -23.4217977377592} {:x 5, :y -23.4217977377592} {:x 6, :y -22.377410071797286} {:x 7, :y -21.289033397839557} {:x 8, :y -23.4217977377592} {:x 9, :y -23.4217977377592} {:x 10, :y -23.4217977377592} {:x 11, :y -23.4217977377592} {:x 12, :y -23.4217977377592} {:x 13, :y -23.4217977377592} {:x 14, :y -23.4217977377592} {:x 15, :y -23.4217977377592} {:x 16, :y -23.4217977377592} {:x 17, :y -23.4217977377592} {:x 18, :y -23.4217977377592} {:x 19, :y -23.4217977377592} {:x 20, :y -23.4217977377592} {:x 21, :y -23.4217977377592} {:x 22, :y -23.4217977377592} {:x 23, :y -23.4217977377592} {:x 24, :y -23.4217977377592} {:x 25, :y -23.4217977377592} {:x 26, :y -23.4217977377592} {:x 27, :y -23.4217977377592} {:x 28, :y -23.4217977377592} {:x 29, :y -23.4217977377592} {:x 30, :y -23.4217977377592} {:x 31, :y -23.4217977377592} {:x 32, :y -23.4217977377592} {:x 33, :y -23.4217977377592} {:x 34, :y -23.4217977377592} {:x 35, :y -23.4217977377592} {:x 36, :y -23.4217977377592} {:x 37, :y -23.4217977377592} {:x 38, :y -23.4217977377592} {:x 39, :y -23.4217977377592} {:x 40, :y -23.4217977377592} {:x 41, :y -23.4217977377592} {:x 42, :y -23.4217977377592} {:x 43, :y -23.4217977377592} {:x 44, :y -23.4217977377592} {:x 45, :y -23.4217977377592} {:x 46, :y -23.4217977377592} {:x 47, :y -23.4217977377592} {:x 48, :y -23.4217977377592} {:x 49, :y -23.4217977377592})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(:time @metrics)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>25</span>","value":"25"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>26</span>","value":"26"},{"type":"html","content":"<span class='clj-long'>28</span>","value":"28"},{"type":"html","content":"<span class='clj-long'>30</span>","value":"30"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"},{"type":"html","content":"<span class='clj-long'>23</span>","value":"23"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>20</span>","value":"20"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>139</span>","value":"139"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"}],"value":"[25 22 22 26 28 30 20 24 23 21 20 20 19 17 21 17 17 18 20 21 21 22 19 18 19 20 18 19 20 19 19 19 22 19 20 18 18 19 15 21 18 139 18 17 19 19 17 17 19 18]"}
;; <=

;; @@
(first (sort-by :error > (:rabble result)))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:error-pp</span>","value":":error-pp"},{"type":"html","content":"<span class='clj-double'>-48.4217977377592</span>","value":"-48.4217977377592"}],"value":"[:error-pp -48.4217977377592]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:error</span>","value":":error"},{"type":"html","content":"<span class='clj-double'>-23.4217977377592</span>","value":"-23.4217977377592"}],"value":"[:error -23.4217977377592]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:complexity</span>","value":":complexity"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"}],"value":"[:complexity 5]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:genotype</span>","value":":genotype"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:div</span>","value":":div"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"(:times x x)"},{"type":"html","content":"<span class='clj-double'>0.3044733639912839</span>","value":"0.3044733639912839"}],"value":"(:div (:times x x) 0.3044733639912839)"}],"value":"[:genotype (:div (:times x x) 0.3044733639912839)]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:age</span>","value":":age"},{"type":"html","content":"<span class='clj-long'>50</span>","value":"50"}],"value":"[:age 50]"}],"value":"{:error-pp -48.4217977377592, :error -23.4217977377592, :complexity 5, :genotype (:div (:times x x) 0.3044733639912839), :age 50}"}
;; <=

;; @@

;; @@
