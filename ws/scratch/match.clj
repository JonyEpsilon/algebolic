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
(ns zealous-dawn
  (:require [gorilla-plot.core :as plot]
            [clojure.core.match :as match]
            [algebolic.expression.algebra :as algebra]
            [algebolic.expression.render :as render]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(algebra/expand-all [:times 3 [:plus 5 4]])
;; @@
;; ->
;;; [:times (3
;;; [:plus 5 4]
;;; 3 9)]
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"}],"value":"(3 9)"}],"value":"[:times (3 9)]"}
;; <=

;; @@
(def ex '[:plus [:plus [:times x x] [:plus [:plus [:plus 0.7504784709616623 x] [:times [:plus x x] x]] [:cos [:plus 0.3830874064158682 [:plus [:cos [:minus 0.055046136153585355 0.8797611746687769]] [:plus [:cos [:minus 0.05204904398981943 0.8411797701626479]] 2.0926440371329957]]]]]] x])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;zealous-dawn/ex</span>","value":"#'zealous-dawn/ex"}
;; <=

;; @@
(render/mathematician-view ex)
;; @@
;; =>
;;; {"type":"latex","content":"((x \\cdot x + (((0.750 + x) + (x + x) \\cdot x) + \\cos((0.383 + (\\cos((0.055 - 0.880) + (\\cos((0.052 - 0.841) + 2.093))))) + x)","value":"#algebolic.expression.render.ExprLatexView{:expr [:plus [:plus [:times x x] [:plus [:plus [:plus 0.7504784709616623 x] [:times [:plus x x] x]] [:cos [:plus 0.3830874064158682 [:plus [:cos [:minus 0.055046136153585355 0.8797611746687769]] [:plus [:cos [:minus 0.05204904398981943 0.8411797701626479]] 2.0926440371329957]]]]]] x]}"}
;; <=

;; @@
(def p
  (algebra/expand-full ex))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;zealous-dawn/p</span>","value":"#'zealous-dawn/p"}
;; <=

;; @@
p
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:plus</span>","value":":plus"},{"type":"html","content":"<span class='clj-double'>-0.003060424094311176</span>","value":"-0.003060424094311176"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times x x]"}],"value":"[:plus -0.003060424094311176 [:times x x]]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:plus [:plus -0.003060424094311176 [:times x x]] x]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times x x]"}],"value":"[:plus [:plus [:plus -0.003060424094311176 [:times x x]] x] [:times x x]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:times</span>","value":":times"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:times x x]"}],"value":"[:plus [:plus [:plus [:plus -0.003060424094311176 [:times x x]] x] [:times x x]] [:times x x]]"},{"type":"html","content":"<span class='clj-symbol'>x</span>","value":"x"}],"value":"[:plus [:plus [:plus [:plus [:plus -0.003060424094311176 [:times x x]] x] [:times x x]] [:times x x]] x]"}
;; <=

;; @@
(render/mathematician-view p)
;; @@
;; =>
;;; {"type":"latex","content":"(((((-0.003 + x \\cdot x) + x) + x \\cdot x) + x \\cdot x) + x)","value":"#algebolic.expression.render.ExprLatexView{:expr [:plus [:plus [:plus [:plus [:plus -0.003060424094311176 [:times x x]] x] [:times x x]] [:times x x]] x]}"}
;; <=

;; @@

;; @@
