;
; This file is part of algebolic.
;
; Copyright (C) 2014-, Imperial College, London, All rights reserved.
;
; Contributors: Jony Hudson
;
; Not for distribution.
;

(ns algebolic.expression.expression-test
  (:use [clojure.test])
  (:require [algebolic.expression.interpreter :as interpreter]))

;; * Helper functions *

(defn approx-eq
  "Are two numbers nearly equal? The threshold is set pretty arbitrarily, but should do for the purpose of testing."
  [a b]
  (< (Math/abs (- a b)) 1e-5))

(defn approx-eq-l
  "Are two lists approximately equal, meaning that their corresponding elements are approximately equal?"
  [l1 l2]
  (reduce #(and %1 %2) (mapv approx-eq l1 l2)))

(defn approx-eq-ll
  "Are two lists of lists approximately equal, meaning that their corresponding elements (lists) are approximately
  equal?"
  [l1 l2]
  (reduce #(and %1 %2) (mapv approx-eq-l l1 l2)))


;; * Evaluation tests *

(def poly-coords [[1.0 1.0] [3.0 4.0] [-1.0 -2.0]])
(def poly1 [:times [:plus 'x 4.0] [:minus 3.0 'y]])
(def poly2 [:div [:minus 'x 'y] [:plus 'x 'y]])
(def trig1 [:cos [:plus 'y [:times 3.0 'x]]])
(def trig2 [:cos [:sin [:plus 'x [:times 'y 'x]]]])
(def power [:plus [:square 'x] 'y])

(deftest evaluation
  (testing "Polynomials"
    (is (= (interpreter/evaluate poly1 '[x y] poly-coords) [10.0 -7.0 15.0]))
    (is (approx-eq-l (interpreter/evaluate poly2 '[x y] poly-coords) [0.0 -0.142857 -0.333333])))
  (testing "Simple trigonometry"
    (is (approx-eq-l (interpreter/evaluate [:sin 'x] '[x] [[0.0] [(/ Math/PI 2)]])
                     [0.0 1.0]))
    (is (approx-eq-l (interpreter/evaluate [:cos 'x] '[x] [[0.0] [(/ Math/PI 2)]])
                     [1.0 0.0])))
  (testing "Trigonometry"
    (is (approx-eq-l (interpreter/evaluate trig1 '[x y] poly-coords)
                     [-0.653644 0.907447 0.283662]))
    (is (approx-eq-l (interpreter/evaluate trig2 '[x y] poly-coords)
                     [0.6143 0.79591 0.666367])))
  (testing "Powers"
    (is (= (interpreter/evaluate power '[x y] poly-coords) [2.0 13.0 -1.0]))))


(deftest evaluation-d
  (testing "Polynomials"
    (is (= (interpreter/evaluate-d poly1 '[x y] poly-coords) [[10.0 2.0 -5.0] [-7.0 -1.0 -7.0] [15.0 5.0 -3.0]]))
    (is (approx-eq-ll (interpreter/evaluate-d poly2 '[x y] poly-coords)
                      [[0.0 0.5 -0.5] [-0.142857 0.163265 -0.122449] [-0.333333 -0.444444 0.222222]])))
  (testing "Trigonometry"
    (is (approx-eq-ll (interpreter/evaluate-d trig1 '[x y] poly-coords)
                      [[-0.653644 2.27041 0.756802] [0.907447 -1.2605 -0.420167] [0.283662 -2.87677 -0.958924]]))
    (is (approx-eq-ll (interpreter/evaluate-d trig2 '[x y] poly-coords)
                      [[0.6143 0.65674 0.32837] [0.79591 2.29963 1.37978] [0.666367 0.402862 0.402862]])))
  (testing "Powers"
    (is (= (interpreter/evaluate-d power '[x y] poly-coords) [[2.0 2.0 1.0] [13.0 6.0 1.0] [-1.0 -2.0 1.0]]))))