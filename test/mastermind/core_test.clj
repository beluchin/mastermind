(ns mastermind.core-test
  (:require [mastermind.core :as sut]
            [clojure.test :as t]
            [spy.core :as spy]
            [mastermind.domain :as domain]))

(t/deftest user-guesses-end-to-end
  (with-redefs [read-line (constantly "1234")
                rand-nth (constantly 1234)] 
    (t/is (nil? (sut/-main "I-guess")))))

(t/deftest prints-level-when-game-starts
  (with-redefs [sut/print-level (spy/mock (fn [_] nil))
                domain/play (spy/mock (fn [_] nil))] 
    (sut/-main "I-guess")
    (t/is (spy/called-once? sut/print-level))))

(t/deftest digits-are-sorted-on-printable-level
  (let [level {:digits #{1 2 3}}]
    (t/is (= (:digits (sut/printable level)) [1 2 3]))))
