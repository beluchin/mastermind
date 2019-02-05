(ns mastermind.app.user-guesses-test
  (:require [clojure.test :as t]
            [mastermind.app.level :as level]
            [mastermind.app.user-guesses :as sut]))

(t/deftest default-level-is-easy
  (t/is (= (:level (sut/new-game)) (:easy level/levels))))

