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
                     :unary-ops [{:op (partial genetics/random-tree-mutation functions terminals 10) :repeat 10}
                                 {:op identity :repeat 10}]})]
    {:initial-zeitgeist      (evolution/make-zeitgeist
                               (genetics/make-initial-population functions terminals 100 5))
     :ea-config              ea-config
     :score-functions        {:complexity (memoize score/size)
                              :error (memoize (partial score/abs-error vars data))
                              :error-pp (memoize (partial score/abs-error-pp vars data 10))}
     :stopping-condition     #(>= (:age %) 50)
     :reporting-function     (fn [z] (print ".") (flush))
     :checkpoint-function    nil}))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/run-config</span>","value":"#'algebolic.demo.simple-sr/run-config"}
;; <=

;; @@
(def result (evolution/run-evolution run-config))
;; @@
;; ->
;;; ..................................................
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;algebolic.demo.simple-sr/result</span>","value":"#'algebolic.demo.simple-sr/result"}
;; <=

;; @@
(sort (map :error (:rabble (:final-zeitgeist result))))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-58248.2339416146</span>","value":"-58248.2339416146"},{"type":"html","content":"<span class='clj-double'>-11503.950438600428</span>","value":"-11503.950438600428"},{"type":"html","content":"<span class='clj-double'>-7259.826831648719</span>","value":"-7259.826831648719"},{"type":"html","content":"<span class='clj-double'>-6688.121919078636</span>","value":"-6688.121919078636"},{"type":"html","content":"<span class='clj-double'>-1205.1674526387872</span>","value":"-1205.1674526387872"},{"type":"html","content":"<span class='clj-double'>-1182.2754700057912</span>","value":"-1182.2754700057912"},{"type":"html","content":"<span class='clj-double'>-927.4405709498144</span>","value":"-927.4405709498144"},{"type":"html","content":"<span class='clj-double'>-901.5388552923237</span>","value":"-901.5388552923237"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-900.0</span>","value":"-900.0"},{"type":"html","content":"<span class='clj-double'>-886.3419053761118</span>","value":"-886.3419053761118"},{"type":"html","content":"<span class='clj-double'>-878.3261126801735</span>","value":"-878.3261126801735"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139098</span>","value":"-875.9744047139098"},{"type":"html","content":"<span class='clj-double'>-875.9744047139097</span>","value":"-875.9744047139097"},{"type":"html","content":"<span class='clj-double'>-865.6069040517078</span>","value":"-865.6069040517078"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-855.0</span>","value":"-855.0"},{"type":"html","content":"<span class='clj-double'>-851.8133150371285</span>","value":"-851.8133150371285"},{"type":"html","content":"<span class='clj-double'>-845.6522253603471</span>","value":"-845.6522253603471"},{"type":"html","content":"<span class='clj-double'>-840.3109469978474</span>","value":"-840.3109469978474"},{"type":"html","content":"<span class='clj-double'>-827.6522253603471</span>","value":"-827.6522253603471"},{"type":"html","content":"<span class='clj-double'>-827.6522253603471</span>","value":"-827.6522253603471"},{"type":"html","content":"<span class='clj-double'>-827.6522253603471</span>","value":"-827.6522253603471"},{"type":"html","content":"<span class='clj-double'>-823.4972210395625</span>","value":"-823.4972210395625"},{"type":"html","content":"<span class='clj-double'>-809.8233337197361</span>","value":"-809.8233337197361"},{"type":"html","content":"<span class='clj-double'>-624.6794779602594</span>","value":"-624.6794779602594"},{"type":"html","content":"<span class='clj-double'>-573.3987136410993</span>","value":"-573.3987136410993"},{"type":"html","content":"<span class='clj-double'>-541.4051454356025</span>","value":"-541.4051454356025"},{"type":"html","content":"<span class='clj-double'>-496.8959346806619</span>","value":"-496.8959346806619"},{"type":"html","content":"<span class='clj-double'>-463.7785175796789</span>","value":"-463.7785175796789"},{"type":"html","content":"<span class='clj-double'>-460.48239991722926</span>","value":"-460.48239991722926"},{"type":"html","content":"<span class='clj-double'>-432.1356923733715</span>","value":"-432.1356923733715"},{"type":"html","content":"<span class='clj-double'>-414.4119264602432</span>","value":"-414.4119264602432"},{"type":"html","content":"<span class='clj-double'>-402.78687144309265</span>","value":"-402.78687144309265"},{"type":"html","content":"<span class='clj-double'>-401.80851259740285</span>","value":"-401.80851259740285"},{"type":"html","content":"<span class='clj-double'>-401.80851259740285</span>","value":"-401.80851259740285"},{"type":"html","content":"<span class='clj-double'>-401.80851259740285</span>","value":"-401.80851259740285"},{"type":"html","content":"<span class='clj-double'>-401.80851259740285</span>","value":"-401.80851259740285"},{"type":"html","content":"<span class='clj-double'>-393.1503376954615</span>","value":"-393.1503376954615"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-375.0</span>","value":"-375.0"},{"type":"html","content":"<span class='clj-double'>-364.04689800229914</span>","value":"-364.04689800229914"},{"type":"html","content":"<span class='clj-double'>-319.6236714696866</span>","value":"-319.6236714696866"},{"type":"html","content":"<span class='clj-double'>-319.6236714696866</span>","value":"-319.6236714696866"},{"type":"html","content":"<span class='clj-double'>-319.6236714696866</span>","value":"-319.6236714696866"},{"type":"html","content":"<span class='clj-double'>-319.6236714696866</span>","value":"-319.6236714696866"},{"type":"html","content":"<span class='clj-double'>-313.2548650726843</span>","value":"-313.2548650726843"},{"type":"html","content":"<span class='clj-double'>-201.79742728219873</span>","value":"-201.79742728219873"},{"type":"html","content":"<span class='clj-double'>-201.79742728219873</span>","value":"-201.79742728219873"},{"type":"html","content":"<span class='clj-double'>-201.79742728219873</span>","value":"-201.79742728219873"},{"type":"html","content":"<span class='clj-double'>-197.0</span>","value":"-197.0"},{"type":"html","content":"<span class='clj-double'>-175.4823999172293</span>","value":"-175.4823999172293"},{"type":"html","content":"<span class='clj-double'>-121.29015863169212</span>","value":"-121.29015863169212"},{"type":"html","content":"<span class='clj-double'>-113.67047044524075</span>","value":"-113.67047044524075"},{"type":"html","content":"<span class='clj-double'>-88.88111355832868</span>","value":"-88.88111355832868"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"},{"type":"html","content":"<span class='clj-double'>-40.027789604374824</span>","value":"-40.027789604374824"}],"value":"(-58248.2339416146 -11503.950438600428 -7259.826831648719 -6688.121919078636 -1205.1674526387872 -1182.2754700057912 -927.4405709498144 -901.5388552923237 -900.0 -900.0 -900.0 -886.3419053761118 -878.3261126801735 -875.9744047139098 -875.9744047139098 -875.9744047139098 -875.9744047139098 -875.9744047139098 -875.9744047139098 -875.9744047139098 -875.9744047139097 -865.6069040517078 -855.0 -855.0 -855.0 -855.0 -855.0 -855.0 -851.8133150371285 -845.6522253603471 -840.3109469978474 -827.6522253603471 -827.6522253603471 -827.6522253603471 -823.4972210395625 -809.8233337197361 -624.6794779602594 -573.3987136410993 -541.4051454356025 -496.8959346806619 -463.7785175796789 -460.48239991722926 -432.1356923733715 -414.4119264602432 -402.78687144309265 -401.80851259740285 -401.80851259740285 -401.80851259740285 -401.80851259740285 -393.1503376954615 -375.0 -375.0 -375.0 -375.0 -364.04689800229914 -319.6236714696866 -319.6236714696866 -319.6236714696866 -319.6236714696866 -313.2548650726843 -201.79742728219873 -201.79742728219873 -201.79742728219873 -197.0 -175.4823999172293 -121.29015863169212 -113.67047044524075 -88.88111355832868 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824 -40.027789604374824)"}
;; <=

;; @@

;; @@
