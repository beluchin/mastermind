(ns mastermind.app.computer-guesses-test
  (:require [mastermind.app.computer-guesses :as sut]
            [clojure.test :as t]
            [mastermind.app.level :as level]))

(t/deftest default-level-is-expert
  (t/is (= (:expert level/levels) (:level (sut/new-game)))))
