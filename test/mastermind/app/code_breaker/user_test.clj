(ns mastermind.app.code-breaker.user-test
  (:require [clojure.test :as t]
            [mastermind.app.code-breaker.user :as sut]))

(t/deftest guess-or-error
  (let [level {:num-digits 3, :dups false, :digits (set (range 0 6))}]
    (t/testing "converts to int"
      (t/is (= (sut/guess-or-error level "123") 123)))

    (t/testing "error conditions"
      (t/are [in out] (= out (:error (sut/guess-or-error level in)))
        "not an int" :not-a-number
        "01234" :zero-in-front
        "1234" :too-many-digits))))
