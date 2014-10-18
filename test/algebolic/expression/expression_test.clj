(ns algebolic.expression.expression-test
  (:use [clojure.test])
  (:require [algebolic.expression.interpreter :as interpreter]))

(def coords [[1.0 1.0] [3.0 4.0] [-1.0 -2.0]])
(def arithmetic-expr [:times [:plus 'x 4.0] [:minus 3.0 'y]])

(deftest evaluation
  (is (= (interpreter/evaluate arithmetic-expr '[x y] coords) [10.0 -7.0 15.0])))
