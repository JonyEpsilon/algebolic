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
;;; {"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50},"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"0e3716bb-20ba-4708-b60e-f405916afff6","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"0e3716bb-20ba-4708-b60e-f405916afff6","field":"data.y"}}],"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"data":[{"name":"0e3716bb-20ba-4708-b60e-f405916afff6","values":[{"x":0.5444433565371297,"y":0.5066379484766356},{"x":0.014537577068925778,"y":0.8520974991926715},{"x":0.00781632288817713,"y":0.6002163566503396},{"x":0.879102523681608,"y":0.26811403137283984},{"x":0.789587900513332,"y":0.5279251873135984},{"x":0.0637531135093482,"y":0.7169716284982731},{"x":0.2260855535827896,"y":0.6030954397357967},{"x":0.3848241006225722,"y":0.07026999092639641},{"x":8.742106721699727E-4,"y":0.8766406942201679},{"x":0.025230656830924847,"y":0.5798078489485452},{"x":0.580571527510636,"y":0.27186713724542577},{"x":0.9906200986627993,"y":0.20950642594291846},{"x":0.9785141377902669,"y":0.44298591649165675},{"x":0.23250158775094376,"y":0.4633895421260551},{"x":0.5091478051433297,"y":0.9120461303419357},{"x":0.7618704854490678,"y":0.8820139839506259},{"x":0.9626148798981473,"y":0.03217018944982308},{"x":0.8590707653810301,"y":0.013533222815012014},{"x":0.6768648271523608,"y":0.849387866954031},{"x":0.04911580654424719,"y":0.3306161671540757}]},{"name":"df7b1e68-0c8b-4162-9fce-f8380f57ff22","values":[{"x":0.00781632288817713,"y":0.6002163566503396},{"x":0.3848241006225722,"y":0.07026999092639641},{"x":8.742106721699727E-4,"y":0.8766406942201679},{"x":0.025230656830924847,"y":0.5798078489485452},{"x":0.8590707653810301,"y":0.013533222815012014},{"x":0.04911580654424719,"y":0.3306161671540757}]}],"marks":[{"type":"symbol","from":{"data":"0e3716bb-20ba-4708-b60e-f405916afff6"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"steelblue"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}},{"type":"symbol","from":{"data":"df7b1e68-0c8b-4162-9fce-f8380f57ff22"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"red"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}, :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"0e3716bb-20ba-4708-b60e-f405916afff6\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"0e3716bb-20ba-4708-b60e-f405916afff6\", :field \"data.y\"}}], :axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :data ({:name \"0e3716bb-20ba-4708-b60e-f405916afff6\", :values ({:x 0.5444433565371297, :y 0.5066379484766356} {:x 0.014537577068925778, :y 0.8520974991926715} {:x 0.00781632288817713, :y 0.6002163566503396} {:x 0.879102523681608, :y 0.26811403137283984} {:x 0.789587900513332, :y 0.5279251873135984} {:x 0.0637531135093482, :y 0.7169716284982731} {:x 0.2260855535827896, :y 0.6030954397357967} {:x 0.3848241006225722, :y 0.07026999092639641} {:x 8.742106721699727E-4, :y 0.8766406942201679} {:x 0.025230656830924847, :y 0.5798078489485452} {:x 0.580571527510636, :y 0.27186713724542577} {:x 0.9906200986627993, :y 0.20950642594291846} {:x 0.9785141377902669, :y 0.44298591649165675} {:x 0.23250158775094376, :y 0.4633895421260551} {:x 0.5091478051433297, :y 0.9120461303419357} {:x 0.7618704854490678, :y 0.8820139839506259} {:x 0.9626148798981473, :y 0.03217018944982308} {:x 0.8590707653810301, :y 0.013533222815012014} {:x 0.6768648271523608, :y 0.849387866954031} {:x 0.04911580654424719, :y 0.3306161671540757})} {:name \"df7b1e68-0c8b-4162-9fce-f8380f57ff22\", :values ({:x 0.00781632288817713, :y 0.6002163566503396} {:x 0.3848241006225722, :y 0.07026999092639641} {:x 8.742106721699727E-4, :y 0.8766406942201679} {:x 0.025230656830924847, :y 0.5798078489485452} {:x 0.8590707653810301, :y 0.013533222815012014} {:x 0.04911580654424719, :y 0.3306161671540757})}), :marks ({:type \"symbol\", :from {:data \"0e3716bb-20ba-4708-b60e-f405916afff6\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}} {:type \"symbol\", :from {:data \"df7b1e68-0c8b-4162-9fce-f8380f57ff22\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"red\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}})}}"}
;; <=

;; @@
(plot/list-plot (map (fn [i] [(:a i) (:b i)]) (pareto/non-dominated-individuals [:a :b] popu)) :colour "red")
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95","field":"data.y"}}],"marks":[{"type":"symbol","from":{"data":"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"fill":{"value":"red"},"fillOpacity":{"value":1}},"update":{"shape":"circle","size":{"value":70},"stroke":{"value":"transparent"}},"hover":{"size":{"value":210},"stroke":{"value":"white"}}}}],"data":[{"name":"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95","values":[{"x":0.00781632288817713,"y":0.6002163566503396},{"x":0.3848241006225722,"y":0.07026999092639641},{"x":8.742106721699727E-4,"y":0.8766406942201679},{"x":0.025230656830924847,"y":0.5798078489485452},{"x":0.8590707653810301,"y":0.013533222815012014},{"x":0.04911580654424719,"y":0.3306161671540757}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95\", :field \"data.y\"}}], :marks [{:type \"symbol\", :from {:data \"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :fill {:value \"red\"}, :fillOpacity {:value 1}}, :update {:shape \"circle\", :size {:value 70}, :stroke {:value \"transparent\"}}, :hover {:size {:value 210}, :stroke {:value \"white\"}}}}], :data [{:name \"e416d5a3-4ab2-43e5-8496-5a5fdddc2a95\", :values ({:x 0.00781632288817713, :y 0.6002163566503396} {:x 0.3848241006225722, :y 0.07026999092639641} {:x 8.742106721699727E-4, :y 0.8766406942201679} {:x 0.025230656830924847, :y 0.5798078489485452} {:x 0.8590707653810301, :y 0.013533222815012014} {:x 0.04911580654424719, :y 0.3306161671540757})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; @@

;; @@
