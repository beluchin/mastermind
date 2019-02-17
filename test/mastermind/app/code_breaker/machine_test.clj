(ns mastermind.app.code-breaker.machine-test
  (:require [clojure.test :as t]
            [mastermind.app.code-breaker.machine :as sut]
            [mastermind.app.level :as level]
            [mastermind.domain :as domain]))

(defn- new-code-breaker []
  (sut/new-code-breaker (rand-nth (vals level/levels))))

(t/deftest code-breaker
  (t/testing "reduces space of possible solutions"
    (t/is (let [breaker (new-code-breaker)
                num-original-solutions @(:possible-solutions breaker)
                any-guess (domain/get-next-guess breaker)]
            (domain/notify breaker any-guess {:ok 1 :so-so 0}) ;; any feedback other than all-ok
            (< (count @(:possible-solutions breaker)) (count num-original-solutions))))))


(comment
  (rand-nth (vals {1 :a, 2 :b}))
  )
