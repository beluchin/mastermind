(ns mastermind.app.console-test
  (:require [clojure.test :refer :all]
            [mastermind.app.console :as console]
            [spy.core :as spy]))

(deftest calls-print-level-when-game-starts
  (with-redefs [console/print-level (spy/mock (fn [_] nil))] 
    (console/game-started "I-guess")
    (is (spy/called-once? console/print-level))))

(deftest digits-are-sorted-on-printable-level
  (let [level {:digits #{1 2 3}}]
    (is (= (:digits (console/printable level)) [1 2 3]))))
