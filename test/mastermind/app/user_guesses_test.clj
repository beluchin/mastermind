(ns mastermind.app.user-guesses-test
  (:require [clojure.test :refer :all]
            [mastermind.app.level :refer :all]
            [mastermind.app.user-guesses :refer :all]))

(deftest default-level
  (is (= (:easy levels) (:level (new-game)))))
