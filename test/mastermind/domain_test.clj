(ns mastermind.domain-test
  (:require [mastermind.domain :as sut]
            [clojure.test :as t]))

(t/deftest game-ends-when-all-are-ok
  (let [game (reify sut/Playable
               (get-next-guess [_] nil)
               (get-answer [_ _] {:ok 42})
               (num-digits [_] 42))]
    (t/is (nil? (sut/play game)))))


(comment 

(defprotocol Playable
  (num-digits [game])
  (not-implemented [_]))
(def game (reify Playable (num-digits [_] 4)))
(num-digits game) ;; => 4
(not-implemented game) ;; Unhandled java.lang.AbstractMethodError

(nil? nil)

)
