(ns mastermind.app.code-maker.user-test
  (:require [mastermind.app.code-maker.user :as sut]
            [clojure.test :as t]))

(t/deftest feedback-or-error
  (t/testing "converts two tokens to int"
    (t/is (= (sut/feedback-or-error "1 1") {:ok 1 :so-so 1})))

  (t/testing "error conditions"
    (t/are [in out] (= out (:error (sut/feedback-or-error in)))
      "more than two tokens" :two-tokens
      "123 not-a-digit" :only-digits)))
