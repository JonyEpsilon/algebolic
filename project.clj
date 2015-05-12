;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(defproject algebolic "1.0.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0-alpha5"]
                 [darwin "1.0.0-SNAPSHOT"]
                 [clj-kdtree "1.2.0" :exclusions [org.clojure/clojure]]
                 [gorilla-renderable "2.0.0"]
                 [criterium "0.4.3"]]
  :java-source-paths ["java"]
  :plugins [[lein-gorilla "0.3.4"]]
  :jvm-opts ^:replace ["-server" "-Xmx4g"])
