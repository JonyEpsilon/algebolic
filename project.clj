;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(defproject algebolic "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-kdtree "1.2.0" :exclusions [org.clojure/clojure]]
                 [org.clojure/core.match "0.2.1"]
                 [gorilla-renderable "1.0.0"]
                 [criterium "0.4.3"]]
  :plugins [[lein-gorilla "0.3.4-SNAPSHOT"]]
  :jvm-opts ["-server" "-Xmx4g"])
