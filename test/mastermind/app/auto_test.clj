(ns mastermind.app.auto-test
  (:require [clojure.test :as t]
            [mastermind.app.auto :as sut]
            [mastermind.app.level :as level]))

(t/deftest default-level-is-expert
  (t/is (= (:level (sut/new-game)) (:expert level/levels))))
