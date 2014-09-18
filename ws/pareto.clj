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
(ns algebolic.pareto
  (:require [gorilla-plot.core :as plot]
            [algebolic.evolution.pareto :as pareto]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(pareto/dominates [:a :b] {:a 1 :b 1.9} {:a 0.9 :b 2} )
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(pareto/non-dominated-individuals [:a :b] [{:a 1 :b 1.9} {:a 0.9 :b 2} {:a 4 :b 5}])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>1.9</span>","value":"1.9"}],"value":"[:b 1.9]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[:a 1]"}],"value":"{:b 1.9, :a 1}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[:b 2]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.9</span>","value":"0.9"}],"value":"[:a 0.9]"}],"value":"{:b 2, :a 0.9}"}],"value":"({:b 1.9, :a 1} {:b 2, :a 0.9})"}
;; <=

;; @@
(def popu (repeatedly 20 (fn [] {:a (rand) :b (rand)})))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.pareto/popu</span>","value":"#'algebolic.pareto/popu"}
;; <=

;; @@
(plot/compose
  (plot/list-plot (map (fn [i] [(:a i) (:b i)]) popu))
  (plot/list-plot (map (fn [i] [(:a i) (:b i)]) (pareto/non-dominated-individuals [:a :b] popu)) :colour "red"))
;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"abd5eb63-8344-4a80-b1f8-d233f0079e5c","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"abd5eb63-8344-4a80-b1f8-d233f0079e5c","field":"data.y"}}],"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"data":[{"name":"abd5eb63-8344-4a80-b1f8-d233f0079e5c","values":[{"x":0.8786747614631608,"y":0.29804358787604324},{"x":0.7987698704734337,"y":0.20752284672820775},{"x":0.5160864957734468,"y":0.22960330950703078},{"x":0.6688783741262394,"y":0.1536884636482082},{"x":0.37084824994737176,"y":0.01516112347772347},{"x":0.4985511761633734,"y":0.21818341165826016},{"x":0.2562107231021623,"y":0.5582776015103217},{"x":0.9997068185427914,"y":0.03070626870672355},{"x":0.06034736316531342,"y":0.6563477123294704},{"x":0.5367007537955555,"y":0.28384697409944404},{"x":0.7045942203668282,"y":0.39723056693074255},{"x":0.33234937965844147,"y":0.47444367983761393},{"x":0.047033811405222004,"y":0.8975626456520669},{"x":0.26018239632212925,"y":0.9435475373702692},{"x":0.5759356800300488,"y":0.00973807717815367},{"x":0.1343309423247674,"y":0.11590446727565762},{"x":0.8166686640619141,"y":0.43020363663966144},{"x":0.5031855846205571,"y":0.28615308203638923},{"x":0.7360504098875373,"y":0.9786302107024019},{"x":0.8289349346869624,"y":0.7764754530743508}]},{"name":"51d9c1df-6ffb-46c9-bbc2-5d7fba854216","values":[{"x":0.37084824994737176,"y":0.01516112347772347},{"x":0.06034736316531342,"y":0.6563477123294704},{"x":0.047033811405222004,"y":0.8975626456520669},{"x":0.5759356800300488,"y":0.00973807717815367},{"x":0.1343309423247674,"y":0.11590446727565762}]}],"marks":[{"type":"symbol","from":{"data":"abd5eb63-8344-4a80-b1f8-d233f0079e5c"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"steelblue"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}},{"type":"symbol","from":{"data":"51d9c1df-6ffb-46c9-bbc2-5d7fba854216"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"red"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"abd5eb63-8344-4a80-b1f8-d233f0079e5c\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"abd5eb63-8344-4a80-b1f8-d233f0079e5c\", :field \"data.y\"}}], :axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :data ({:name \"abd5eb63-8344-4a80-b1f8-d233f0079e5c\", :values ({:x 0.8786747614631608, :y 0.29804358787604324} {:x 0.7987698704734337, :y 0.20752284672820775} {:x 0.5160864957734468, :y 0.22960330950703078} {:x 0.6688783741262394, :y 0.1536884636482082} {:x 0.37084824994737176, :y 0.01516112347772347} {:x 0.4985511761633734, :y 0.21818341165826016} {:x 0.2562107231021623, :y 0.5582776015103217} {:x 0.9997068185427914, :y 0.03070626870672355} {:x 0.06034736316531342, :y 0.6563477123294704} {:x 0.5367007537955555, :y 0.28384697409944404} {:x 0.7045942203668282, :y 0.39723056693074255} {:x 0.33234937965844147, :y 0.47444367983761393} {:x 0.047033811405222004, :y 0.8975626456520669} {:x 0.26018239632212925, :y 0.9435475373702692} {:x 0.5759356800300488, :y 0.00973807717815367} {:x 0.1343309423247674, :y 0.11590446727565762} {:x 0.8166686640619141, :y 0.43020363663966144} {:x 0.5031855846205571, :y 0.28615308203638923} {:x 0.7360504098875373, :y 0.9786302107024019} {:x 0.8289349346869624, :y 0.7764754530743508})} {:name \"51d9c1df-6ffb-46c9-bbc2-5d7fba854216\", :values ({:x 0.37084824994737176, :y 0.01516112347772347} {:x 0.06034736316531342, :y 0.6563477123294704} {:x 0.047033811405222004, :y 0.8975626456520669} {:x 0.5759356800300488, :y 0.00973807717815367} {:x 0.1343309423247674, :y 0.11590446727565762})}), :marks ({:type \"symbol\", :from {:data \"abd5eb63-8344-4a80-b1f8-d233f0079e5c\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}} {:type \"symbol\", :from {:data \"51d9c1df-6ffb-46c9-bbc2-5d7fba854216\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"red\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}})}}"}
;; <=

;; @@
(time (doall (pareto/non-dominated-individuals [:a :b] popu)))
;; @@
;; ->
;;; &quot;Elapsed time: 0.388 msecs&quot;
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.37084824994737176</span>","value":"0.37084824994737176"}],"value":"[:a 0.37084824994737176]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>0.01516112347772347</span>","value":"0.01516112347772347"}],"value":"[:b 0.01516112347772347]"}],"value":"{:a 0.37084824994737176, :b 0.01516112347772347}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.06034736316531342</span>","value":"0.06034736316531342"}],"value":"[:a 0.06034736316531342]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>0.6563477123294704</span>","value":"0.6563477123294704"}],"value":"[:b 0.6563477123294704]"}],"value":"{:a 0.06034736316531342, :b 0.6563477123294704}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.047033811405222004</span>","value":"0.047033811405222004"}],"value":"[:a 0.047033811405222004]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>0.8975626456520669</span>","value":"0.8975626456520669"}],"value":"[:b 0.8975626456520669]"}],"value":"{:a 0.047033811405222004, :b 0.8975626456520669}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.5759356800300488</span>","value":"0.5759356800300488"}],"value":"[:a 0.5759356800300488]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>0.00973807717815367</span>","value":"0.00973807717815367"}],"value":"[:b 0.00973807717815367]"}],"value":"{:a 0.5759356800300488, :b 0.00973807717815367}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:a</span>","value":":a"},{"type":"html","content":"<span class='clj-double'>0.1343309423247674</span>","value":"0.1343309423247674"}],"value":"[:a 0.1343309423247674]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:b</span>","value":":b"},{"type":"html","content":"<span class='clj-double'>0.11590446727565762</span>","value":"0.11590446727565762"}],"value":"[:b 0.11590446727565762]"}],"value":"{:a 0.1343309423247674, :b 0.11590446727565762}"}],"value":"({:a 0.37084824994737176, :b 0.01516112347772347} {:a 0.06034736316531342, :b 0.6563477123294704} {:a 0.047033811405222004, :b 0.8975626456520669} {:a 0.5759356800300488, :b 0.00973807717815367} {:a 0.1343309423247674, :b 0.11590446727565762})"}
;; <=

;; @@
(pareto/dominated-count [:a :b] [{:a 0.8 :b 1.8} {:a 1 :b 1.9} {:a 0.9 :b 2} {:a 4 :b 5}] {:a 0.8 :b 1.8})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>3</span>","value":"3"}
;; <=

;; @@

;; @@
