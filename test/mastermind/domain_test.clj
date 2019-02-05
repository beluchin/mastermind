(ns mastermind.domain-test
  (:require [mastermind.domain :as sut]
            [clojure.test :as t]))

(t/deftest game-ends-when-all-are-ok
  (let [game {:level {:num-digits 42}
              :code-breaker (reify sut/CodeBreaker
                              (get-next-guess [_] nil))
              :code-maker (reify sut/CodeMaker
                            (get-feedback [_ _] {:ok 42}))}]
    (t/is (nil? (sut/play game)))))


(comment 

  (defprotocol Playable
    (num-digits [game])
    (not-implemented [_]))
  (def game (reify Playable (num-digits [_] 4)))
  (num-digits game)    ;; => 4
  (not-implemented game) ;; Unhandled java.lang.AbstractMethodError

  (nil? nil)

  )
