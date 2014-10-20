(ns algebolic.expression.expression-test
  (:use [clojure.test])
  (:require [algebolic.expression.interpreter :as interpreter]))

;; * Helper functions *

(defn approx-eq
  "Are two numbers nearly equal? The threshold is set pretty arbitrarily, but should do for the purpose of testing."
  [a b]
  (< (Math/abs (- a b)) 1e-10))

(defn approx-eq-l
  "Are two lists approximately equal, meaning that their corresponding elements are approximately equal?"
  [l1 l2]
  (reduce #(and %1 %2) (map approx-eq l1 l2)))


;; * Evaluation tests *

(def coords [[1.0 1.0] [3.0 4.0] [-1.0 -2.0]])
(def arithmetic-expr [:times [:plus 'x 4.0] [:minus 3.0 'y]])

(deftest evaluation
  (testing "Polynomials"
    (is (= (interpreter/evaluate arithmetic-expr '[x y] coords) [10.0 -7.0 15.0])))
  (testing "Simple trigonometry"
    (is (approx-eq-l (interpreter/evaluate [:sin 'x] '[x] [[0.0] [(/ Math/PI 2)]])
                     [0.0 1.0]))
    (is (approx-eq-l (interpreter/evaluate [:cos 'x] '[x] [[0.0] [(/ Math/PI 2)]])
                     [1.0 0.0]))))