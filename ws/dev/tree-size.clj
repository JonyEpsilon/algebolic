;; gorilla-repl.fileformat = 1

;; **
;;; # Looking at tree sizes
;;; 
;;; In this notebook I want to look at how tree sizes are affected by the genetic operations. What I want to check is how repeatedly applying the operations affects the distribution of tree sizes in a population. There seems to be a fair bit of research literature on what the best way to generate trees is, and the details of how the genetic operations should be done. But it's hard to get a clear picture of any "best" way to do it. My gut feeling is that as long as the genetic operations promote some size diversity, but don't either compress the trees to nothing, or explode the size rapidly then they'll probably be ok.
;; **

;; @@
(ns algebolic.tree-size
  (:require [gorilla-plot.core :as plot]
            [algebolic.expression.core :as expression]
            [algebolic.expression.genetics :as genetics]
            [algebolic.expression.score :as score]
            [algebolic.expression.tree :as tree]
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

;; **
;;; Choose a particular expression grammar. Note that the choice of grammar affects the results because the arity of the chosen symbols influences the shapes of the trees. Change this and re-run to see how changing the expressions affects the results.
;; **

;; @@
(def vars '[x])
(def functions (expression/get-function-symbols #{:plus :minus :times :cos :sin}))
(def terminals (expression/make-terminals vars))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/terminals</span>","value":"#'algebolic.tree-size/terminals"}
;; <=

;; **
;;; Make an initial population to experiment with.
;; **

;; @@
(def population
  (genetics/ramped-half-and-half-population functions terminals 100 10))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/population</span>","value":"#'algebolic.tree-size/population"}
;; <=

;; @@
(map score/size population)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>21</span>","value":"21"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>40</span>","value":"40"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>136</span>","value":"136"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>89</span>","value":"89"},{"type":"html","content":"<span class='clj-long'>168</span>","value":"168"},{"type":"html","content":"<span class='clj-long'>33</span>","value":"33"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>40</span>","value":"40"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>161</span>","value":"161"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>138</span>","value":"138"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>70</span>","value":"70"},{"type":"html","content":"<span class='clj-long'>215</span>","value":"215"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>73</span>","value":"73"},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>211</span>","value":"211"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>24</span>","value":"24"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-long'>47</span>","value":"47"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>205</span>","value":"205"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"},{"type":"html","content":"<span class='clj-long'>177</span>","value":"177"},{"type":"html","content":"<span class='clj-long'>134</span>","value":"134"},{"type":"html","content":"<span class='clj-long'>51</span>","value":"51"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>219</span>","value":"219"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>135</span>","value":"135"},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>22</span>","value":"22"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>64</span>","value":"64"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>243</span>","value":"243"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>187</span>","value":"187"},{"type":"html","content":"<span class='clj-long'>61</span>","value":"61"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>96</span>","value":"96"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-long'>7</span>","value":"7"},{"type":"html","content":"<span class='clj-long'>17</span>","value":"17"},{"type":"html","content":"<span class='clj-long'>18</span>","value":"18"},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"},{"type":"html","content":"<span class='clj-long'>38</span>","value":"38"},{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-long'>33</span>","value":"33"},{"type":"html","content":"<span class='clj-long'>48</span>","value":"48"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>6</span>","value":"6"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>47</span>","value":"47"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-long'>19</span>","value":"19"},{"type":"html","content":"<span class='clj-long'>5</span>","value":"5"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-long'>74</span>","value":"74"},{"type":"html","content":"<span class='clj-long'>36</span>","value":"36"},{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"},{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"(21 6 1 12 1 40 7 136 19 4 1 89 168 33 2 40 2 161 13 138 6 70 215 3 12 7 1 73 14 1 6 211 1 24 9 47 1 8 4 4 1 205 4 177 134 51 13 219 13 135 36 13 22 6 5 5 11 6 3 64 7 243 2 1 187 61 1 1 96 8 1 6 1 8 8 7 17 18 36 38 15 14 33 48 2 5 6 5 47 5 12 19 5 1 13 74 36 10 14 4)"}
;; <=

;; @@
(/ (apply + (map score/size population)) 100.)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>38.49</span>","value":"38.49"}
;; <=

;; **
;;; Iterate mutation on all population members for 1000 generations. No selection is performed.
;; **

;; @@
(def mutation-sizes
  (map score/size
       (nth
         (iterate #(map (partial genetics/random-tree-mutation functions terminals 5) %) population)
         1000)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/mutation-sizes</span>","value":"#'algebolic.tree-size/mutation-sizes"}
;; <=

;; **
;;; And look at the distribution of resulting tree sizes.
;; **

;; @@
(plot/histogram mutation-sizes :bins 50)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"ac6afe2d-75a9-48f9-89d0-1f682ca7f847","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"ac6afe2d-75a9-48f9-89d0-1f682ca7f847","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"ac6afe2d-75a9-48f9-89d0-1f682ca7f847"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"ac6afe2d-75a9-48f9-89d0-1f682ca7f847","values":[{"x":7.0,"y":0},{"x":16.580000000000002,"y":14.0},{"x":26.160000000000004,"y":9.0},{"x":35.74000000000001,"y":6.0},{"x":45.32000000000001,"y":5.0},{"x":54.900000000000006,"y":6.0},{"x":64.48,"y":8.0},{"x":74.06,"y":7.0},{"x":83.64,"y":1.0},{"x":93.22,"y":7.0},{"x":102.8,"y":3.0},{"x":112.38,"y":3.0},{"x":121.96,"y":2.0},{"x":131.54,"y":2.0},{"x":141.12,"y":4.0},{"x":150.70000000000002,"y":2.0},{"x":160.28000000000003,"y":1.0},{"x":169.86000000000004,"y":4.0},{"x":179.44000000000005,"y":1.0},{"x":189.02000000000007,"y":2.0},{"x":198.60000000000008,"y":0.0},{"x":208.1800000000001,"y":2.0},{"x":217.7600000000001,"y":1.0},{"x":227.34000000000012,"y":1.0},{"x":236.92000000000013,"y":1.0},{"x":246.50000000000014,"y":2.0},{"x":256.08000000000015,"y":1.0},{"x":265.66000000000014,"y":0.0},{"x":275.2400000000001,"y":1.0},{"x":284.8200000000001,"y":0.0},{"x":294.4000000000001,"y":2.0},{"x":303.9800000000001,"y":0.0},{"x":313.56000000000006,"y":0.0},{"x":323.14000000000004,"y":0.0},{"x":332.72,"y":0.0},{"x":342.3,"y":0.0},{"x":351.88,"y":0.0},{"x":361.46,"y":1.0},{"x":371.03999999999996,"y":0.0},{"x":380.61999999999995,"y":0.0},{"x":390.19999999999993,"y":0.0},{"x":399.7799999999999,"y":0.0},{"x":409.3599999999999,"y":0.0},{"x":418.9399999999999,"y":0.0},{"x":428.51999999999987,"y":0.0},{"x":438.09999999999985,"y":0.0},{"x":447.67999999999984,"y":0.0},{"x":457.2599999999998,"y":0.0},{"x":466.8399999999998,"y":0.0},{"x":476.4199999999998,"y":0.0},{"x":485.9999999999998,"y":0.0},{"x":495.57999999999976,"y":1.0},{"x":505.15999999999974,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"ac6afe2d-75a9-48f9-89d0-1f682ca7f847\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"ac6afe2d-75a9-48f9-89d0-1f682ca7f847\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"ac6afe2d-75a9-48f9-89d0-1f682ca7f847\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"ac6afe2d-75a9-48f9-89d0-1f682ca7f847\", :values ({:x 7.0, :y 0} {:x 16.580000000000002, :y 14.0} {:x 26.160000000000004, :y 9.0} {:x 35.74000000000001, :y 6.0} {:x 45.32000000000001, :y 5.0} {:x 54.900000000000006, :y 6.0} {:x 64.48, :y 8.0} {:x 74.06, :y 7.0} {:x 83.64, :y 1.0} {:x 93.22, :y 7.0} {:x 102.8, :y 3.0} {:x 112.38, :y 3.0} {:x 121.96, :y 2.0} {:x 131.54, :y 2.0} {:x 141.12, :y 4.0} {:x 150.70000000000002, :y 2.0} {:x 160.28000000000003, :y 1.0} {:x 169.86000000000004, :y 4.0} {:x 179.44000000000005, :y 1.0} {:x 189.02000000000007, :y 2.0} {:x 198.60000000000008, :y 0.0} {:x 208.1800000000001, :y 2.0} {:x 217.7600000000001, :y 1.0} {:x 227.34000000000012, :y 1.0} {:x 236.92000000000013, :y 1.0} {:x 246.50000000000014, :y 2.0} {:x 256.08000000000015, :y 1.0} {:x 265.66000000000014, :y 0.0} {:x 275.2400000000001, :y 1.0} {:x 284.8200000000001, :y 0.0} {:x 294.4000000000001, :y 2.0} {:x 303.9800000000001, :y 0.0} {:x 313.56000000000006, :y 0.0} {:x 323.14000000000004, :y 0.0} {:x 332.72, :y 0.0} {:x 342.3, :y 0.0} {:x 351.88, :y 0.0} {:x 361.46, :y 1.0} {:x 371.03999999999996, :y 0.0} {:x 380.61999999999995, :y 0.0} {:x 390.19999999999993, :y 0.0} {:x 399.7799999999999, :y 0.0} {:x 409.3599999999999, :y 0.0} {:x 418.9399999999999, :y 0.0} {:x 428.51999999999987, :y 0.0} {:x 438.09999999999985, :y 0.0} {:x 447.67999999999984, :y 0.0} {:x 457.2599999999998, :y 0.0} {:x 466.8399999999998, :y 0.0} {:x 476.4199999999998, :y 0.0} {:x 485.9999999999998, :y 0.0} {:x 495.57999999999976, :y 1.0} {:x 505.15999999999974, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(/ (apply + mutation-sizes) 100.)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>95.05</span>","value":"95.05"}
;; <=

;; **
;;; To summarise a bit of playing around: mutation seems pretty well behaved no matter how you do it, or what the grammar is. In particular using mutation with "full" new tree generation of size one less than the initial population size seems to keep the population size stable, but with increasing spread, in most cases, so is probably a reasonable thing to use.
;;; 
;;; Now, the same for generating new populations with crossover only, random selection.
;; **

;; @@
(defn crossover-population
  [population]
  (reduce into []
    (repeatedly 50 #((partial genetics/ninety-ten-sl-crossover 80) (rand-nth population) (rand-nth population)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/crossover-population</span>","value":"#'algebolic.tree-size/crossover-population"}
;; <=

;; @@
(def crossover-pop (nth (iterate crossover-population population) 200))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/crossover-pop</span>","value":"#'algebolic.tree-size/crossover-pop"}
;; <=

;; @@
(plot/histogram (map score/size crossover-pop) :bins 50)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c","values":[{"x":1.0,"y":0},{"x":1.8000000000000003,"y":13.0},{"x":2.6000000000000005,"y":10.0},{"x":3.400000000000001,"y":12.0},{"x":4.200000000000001,"y":7.0},{"x":5.000000000000001,"y":9.0},{"x":5.800000000000001,"y":0.0},{"x":6.6000000000000005,"y":3.0},{"x":7.4,"y":3.0},{"x":8.200000000000001,"y":7.0},{"x":9.000000000000002,"y":2.0},{"x":9.800000000000002,"y":0.0},{"x":10.600000000000003,"y":7.0},{"x":11.400000000000004,"y":3.0},{"x":12.200000000000005,"y":3.0},{"x":13.000000000000005,"y":2.0},{"x":13.800000000000006,"y":0.0},{"x":14.600000000000007,"y":1.0},{"x":15.400000000000007,"y":0.0},{"x":16.200000000000006,"y":2.0},{"x":17.000000000000007,"y":2.0},{"x":17.800000000000008,"y":0.0},{"x":18.60000000000001,"y":2.0},{"x":19.40000000000001,"y":2.0},{"x":20.20000000000001,"y":3.0},{"x":21.00000000000001,"y":0.0},{"x":21.80000000000001,"y":0.0},{"x":22.600000000000012,"y":1.0},{"x":23.400000000000013,"y":3.0},{"x":24.200000000000014,"y":0.0},{"x":25.000000000000014,"y":0.0},{"x":25.800000000000015,"y":0.0},{"x":26.600000000000016,"y":0.0},{"x":27.400000000000016,"y":0.0},{"x":28.200000000000017,"y":0.0},{"x":29.000000000000018,"y":0.0},{"x":29.80000000000002,"y":0.0},{"x":30.60000000000002,"y":0.0},{"x":31.40000000000002,"y":0.0},{"x":32.20000000000002,"y":1.0},{"x":33.000000000000014,"y":0.0},{"x":33.80000000000001,"y":0.0},{"x":34.60000000000001,"y":0.0},{"x":35.400000000000006,"y":0.0},{"x":36.2,"y":0.0},{"x":37.0,"y":0.0},{"x":37.8,"y":1.0},{"x":38.599999999999994,"y":0.0},{"x":39.39999999999999,"y":0.0},{"x":40.19999999999999,"y":0.0},{"x":40.999999999999986,"y":0.0},{"x":41.79999999999998,"y":1.0},{"x":42.59999999999998,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"3427bde8-fcda-4cf7-9bf2-e5ba15cd263c\", :values ({:x 1.0, :y 0} {:x 1.8000000000000003, :y 13.0} {:x 2.6000000000000005, :y 10.0} {:x 3.400000000000001, :y 12.0} {:x 4.200000000000001, :y 7.0} {:x 5.000000000000001, :y 9.0} {:x 5.800000000000001, :y 0.0} {:x 6.6000000000000005, :y 3.0} {:x 7.4, :y 3.0} {:x 8.200000000000001, :y 7.0} {:x 9.000000000000002, :y 2.0} {:x 9.800000000000002, :y 0.0} {:x 10.600000000000003, :y 7.0} {:x 11.400000000000004, :y 3.0} {:x 12.200000000000005, :y 3.0} {:x 13.000000000000005, :y 2.0} {:x 13.800000000000006, :y 0.0} {:x 14.600000000000007, :y 1.0} {:x 15.400000000000007, :y 0.0} {:x 16.200000000000006, :y 2.0} {:x 17.000000000000007, :y 2.0} {:x 17.800000000000008, :y 0.0} {:x 18.60000000000001, :y 2.0} {:x 19.40000000000001, :y 2.0} {:x 20.20000000000001, :y 3.0} {:x 21.00000000000001, :y 0.0} {:x 21.80000000000001, :y 0.0} {:x 22.600000000000012, :y 1.0} {:x 23.400000000000013, :y 3.0} {:x 24.200000000000014, :y 0.0} {:x 25.000000000000014, :y 0.0} {:x 25.800000000000015, :y 0.0} {:x 26.600000000000016, :y 0.0} {:x 27.400000000000016, :y 0.0} {:x 28.200000000000017, :y 0.0} {:x 29.000000000000018, :y 0.0} {:x 29.80000000000002, :y 0.0} {:x 30.60000000000002, :y 0.0} {:x 31.40000000000002, :y 0.0} {:x 32.20000000000002, :y 1.0} {:x 33.000000000000014, :y 0.0} {:x 33.80000000000001, :y 0.0} {:x 34.60000000000001, :y 0.0} {:x 35.400000000000006, :y 0.0} {:x 36.2, :y 0.0} {:x 37.0, :y 0.0} {:x 37.8, :y 1.0} {:x 38.599999999999994, :y 0.0} {:x 39.39999999999999, :y 0.0} {:x 40.19999999999999, :y 0.0} {:x 40.999999999999986, :y 0.0} {:x 41.79999999999998, :y 1.0} {:x 42.59999999999998, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(/ (apply + (map score/size crossover-pop)) 100.)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>8.35</span>","value":"8.35"}
;; <=

;; @@
(defn reproduce-no-selection
  [population]
  (reproduction/reproduce
    {:selector rand-nth
     :binary-ops [{:op (partial genetics/ninety-ten-sl-crossover 80) :repeat 35}]
     :unary-ops  [{:op (partial genetics/random-tree-mutation functions terminals 5) :repeat 10}
                  {:op identity :repeat 20}]}
    population))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/reproduce-no-selection</span>","value":"#'algebolic.tree-size/reproduce-no-selection"}
;; <=

;; @@
(def repro-sizes
  (map score/size
       (map :genotype (nth (iterate reproduce-no-selection (map (fn [i] {:genotype i}) population)) 500))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.tree-size/repro-sizes</span>","value":"#'algebolic.tree-size/repro-sizes"}
;; <=

;; @@
(plot/histogram repro-sizes :bins 50)
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"e7cfe70b-8037-40e6-ac32-7eeca5333996","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"e7cfe70b-8037-40e6-ac32-7eeca5333996","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"e7cfe70b-8037-40e6-ac32-7eeca5333996"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"e7cfe70b-8037-40e6-ac32-7eeca5333996","values":[{"x":1.0,"y":0},{"x":2.3000000000000003,"y":8.0},{"x":3.6000000000000005,"y":5.0},{"x":4.9,"y":3.0},{"x":6.200000000000001,"y":7.0},{"x":7.500000000000002,"y":4.0},{"x":8.800000000000002,"y":2.0},{"x":10.100000000000003,"y":7.0},{"x":11.400000000000004,"y":1.0},{"x":12.700000000000005,"y":0.0},{"x":14.000000000000005,"y":8.0},{"x":15.300000000000006,"y":1.0},{"x":16.600000000000005,"y":3.0},{"x":17.900000000000006,"y":3.0},{"x":19.200000000000006,"y":6.0},{"x":20.500000000000007,"y":3.0},{"x":21.800000000000008,"y":2.0},{"x":23.10000000000001,"y":4.0},{"x":24.40000000000001,"y":2.0},{"x":25.70000000000001,"y":0.0},{"x":27.00000000000001,"y":4.0},{"x":28.30000000000001,"y":1.0},{"x":29.600000000000012,"y":4.0},{"x":30.900000000000013,"y":2.0},{"x":32.20000000000001,"y":4.0},{"x":33.50000000000001,"y":3.0},{"x":34.800000000000004,"y":1.0},{"x":36.1,"y":3.0},{"x":37.4,"y":0.0},{"x":38.699999999999996,"y":1.0},{"x":39.99999999999999,"y":0.0},{"x":41.29999999999999,"y":0.0},{"x":42.59999999999999,"y":1.0},{"x":43.899999999999984,"y":0.0},{"x":45.19999999999998,"y":1.0},{"x":46.49999999999998,"y":0.0},{"x":47.799999999999976,"y":0.0},{"x":49.09999999999997,"y":1.0},{"x":50.39999999999997,"y":1.0},{"x":51.69999999999997,"y":0.0},{"x":52.999999999999964,"y":0.0},{"x":54.29999999999996,"y":0.0},{"x":55.59999999999996,"y":0.0},{"x":56.899999999999956,"y":0.0},{"x":58.19999999999995,"y":0.0},{"x":59.49999999999995,"y":0.0},{"x":60.79999999999995,"y":0.0},{"x":62.099999999999945,"y":1.0},{"x":63.39999999999994,"y":1.0},{"x":64.69999999999995,"y":1.0},{"x":65.99999999999994,"y":0.0},{"x":67.29999999999994,"y":1.0},{"x":68.59999999999994,"y":0}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"e7cfe70b-8037-40e6-ac32-7eeca5333996\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"e7cfe70b-8037-40e6-ac32-7eeca5333996\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"e7cfe70b-8037-40e6-ac32-7eeca5333996\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"e7cfe70b-8037-40e6-ac32-7eeca5333996\", :values ({:x 1.0, :y 0} {:x 2.3000000000000003, :y 8.0} {:x 3.6000000000000005, :y 5.0} {:x 4.9, :y 3.0} {:x 6.200000000000001, :y 7.0} {:x 7.500000000000002, :y 4.0} {:x 8.800000000000002, :y 2.0} {:x 10.100000000000003, :y 7.0} {:x 11.400000000000004, :y 1.0} {:x 12.700000000000005, :y 0.0} {:x 14.000000000000005, :y 8.0} {:x 15.300000000000006, :y 1.0} {:x 16.600000000000005, :y 3.0} {:x 17.900000000000006, :y 3.0} {:x 19.200000000000006, :y 6.0} {:x 20.500000000000007, :y 3.0} {:x 21.800000000000008, :y 2.0} {:x 23.10000000000001, :y 4.0} {:x 24.40000000000001, :y 2.0} {:x 25.70000000000001, :y 0.0} {:x 27.00000000000001, :y 4.0} {:x 28.30000000000001, :y 1.0} {:x 29.600000000000012, :y 4.0} {:x 30.900000000000013, :y 2.0} {:x 32.20000000000001, :y 4.0} {:x 33.50000000000001, :y 3.0} {:x 34.800000000000004, :y 1.0} {:x 36.1, :y 3.0} {:x 37.4, :y 0.0} {:x 38.699999999999996, :y 1.0} {:x 39.99999999999999, :y 0.0} {:x 41.29999999999999, :y 0.0} {:x 42.59999999999999, :y 1.0} {:x 43.899999999999984, :y 0.0} {:x 45.19999999999998, :y 1.0} {:x 46.49999999999998, :y 0.0} {:x 47.799999999999976, :y 0.0} {:x 49.09999999999997, :y 1.0} {:x 50.39999999999997, :y 1.0} {:x 51.69999999999997, :y 0.0} {:x 52.999999999999964, :y 0.0} {:x 54.29999999999996, :y 0.0} {:x 55.59999999999996, :y 0.0} {:x 56.899999999999956, :y 0.0} {:x 58.19999999999995, :y 0.0} {:x 59.49999999999995, :y 0.0} {:x 60.79999999999995, :y 0.0} {:x 62.099999999999945, :y 1.0} {:x 63.39999999999994, :y 1.0} {:x 64.69999999999995, :y 1.0} {:x 65.99999999999994, :y 0.0} {:x 67.29999999999994, :y 1.0} {:x 68.59999999999994, :y 0})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@
(/ (apply + repro-sizes) 100.)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>19.32</span>","value":"19.32"}
;; <=

;; @@

;; @@
