;;;; This file is part of algebolic. Copyright (C) 2014-, Jony Hudson.
;;;;
;;;; Not for distribution.

(ns algebolic.expression.render
  "Gorilla REPL rendering support for algebolic expressions."
  (:require [algebolic.expression.core :as expression]
            [clojure.string :as str]
            [gorilla-renderable.core :as render]))

(defn- latexify
  "Convert an expression to a LaTeX string. Uses a very simple algorithm, that doesn't generate terribly
  pretty results."
  [expr]
  (if (expression/non-terminal? expr)
    (case (first expr)
      :plus  (str "(" (str/join " + " (map latexify (rest expr))) ")")
      :minus  (str "(" (str/join " - " (map latexify (rest expr))) ")")
      :times  (str/join " \\cdot " (map latexify (rest expr)))
      :div (str "\\frac{" (latexify (first (rest expr))) " }{ " (latexify (second (rest expr))) "}")
      :sin (str "\\sin(" (latexify (second expr)) ")")
      :cos (str "\\cos(" (latexify (second expr))) ")")
    (if (float? expr) (format "%.3f" expr) (pr-str expr))))

(defrecord ExprLatexView [expr])

(defn mathematician-view
  "View an algebolic expression in more traditional mathematical notation."
  [expr]
  (ExprLatexView. expr))

(extend-type ExprLatexView
  render/Renderable
  (render [self]
    {:type :latex
     :content (latexify (:expr self))
     :value (pr-str self)}))