;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; algebolic is licenced to you under the MIT licence. See the file LICENCE.txt for full details.

(defproject algebolic "0.1.0-SNAPSHOT"
  :description "A library for doing symbolic regression with algebraic manipulation."
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-kdtree "1.2.0" :exclusions [org.clojure/clojure]]]
  :plugins [[lein-gorilla "0.3.4-SNAPSHOT"]]
  :jvm-opts ["-server" "-Xmx4g"])
