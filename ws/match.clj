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
            [clojure.core.match :as match]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(match/match ['(:times (:plus 3 4) 2)]
  [([:times ([:plus a b] :seq) c] :seq)] (seq [:plus (seq [:times a c]) (seq [:times b c])])
 )
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(:plus (:times 3 2) (:times 4 2))</span>","value":"(:plus (:times 3 2) (:times 4 2))"}
;; <=

;; @@
(==>
  (:times (:plus a b) c)
  (:plus (:times a c) (:times b c)))
;; @@
