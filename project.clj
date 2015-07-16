;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Released under the MIT license..
;

(defproject algebolic "1.0.1"
            :url "https://github.com/JonyEpsilon/algebolic"
            :license {:name "MIT"}
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [darwin "1.0.1"]
                           [clj-kdtree "1.2.0" :exclusions [org.clojure/clojure]]
                           [gorilla-renderable "2.0.0"]
                           [criterium "0.4.3"]]
            :javac-options ["-target" "1.7" "-source" "1.7"]
            :java-source-paths ["java"]
            :plugins [[lein-gorilla "0.3.4"]]
            :jvm-opts ^:replace ["-server" "-Xmx4g"])
