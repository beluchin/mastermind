(ns mastermind.app.user-guesses-test
  (:require [clojure.test :as t]
            [mastermind.app.level :refer :all]
            [mastermind.app.user-guesses :as sut]))

(t/deftest default-level-is-easy
  (t/is (= (:level (sut/new-game)) (:easy levels))))

(t/deftest guess-or-error
  (t/testing "converts to int"
    (t/is (= (sut/guess-or-error "1234") 1234)))

  (t/testing "error conditions"
    (t/are [in out] (= out (:error (sut/guess-or-error in)))
      "not an int" :not-a-number
      "01234" :zero-in-front)))
