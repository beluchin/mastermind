(ns mastermind.computer-guesses.impl-test
  (:require [clojure.test :refer :all]
            [mastermind.computer-guesses.impl :refer [filter-solutions]]))

(deftest filter-solutions-test
  (are [x y] (= x (into #{} (apply filter-solutions y)))
             #{1234} [#{1234 5678} 1567 {:ok 1, :so-so 0}]
             #{}     [#{1234 5678} 1567 {:ok 2, :so-so 0}]
             #{5678} [#{1234 5678} 1567 {:ok 0, :so-so 3}]))