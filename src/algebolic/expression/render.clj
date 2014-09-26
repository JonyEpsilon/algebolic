(ns algebolic.expression.render
  (:require [algebolic.expression.core :as expression]
            [clojure.string :as str]
            [gorilla-renderable.core :as render]))

(defn latexify
  [expr]
  (if (expression/non-terminal? expr)
    (case (first expr)
      :plus (str "(" (str/join " + " (map latexify (rest expr))) ")")
      :minus (str "(- " (latexify (second expr)) ")")
      :times (str/join " \\cdot " (map latexify (rest expr)))
      :div (str "\\frac{" (latexify (first (rest expr))) " }{ " (latexify (second (rest expr))) "}")
      :sin (str "\\sin(" (latexify (second expr)) ")")
      :cos (str "\\cos(" (latexify (second expr))) ")")
    (if (float? expr) (format "%.3f" expr) (pr-str expr))))

(defrecord ExprLatexView [expr])

(defn mathematician-view
  [expr]
  (ExprLatexView. expr))

(extend-type ExprLatexView
  render/Renderable
  (render [self]
    {:type :latex
     :content (latexify (:expr self))
     :value (pr-str self)}))