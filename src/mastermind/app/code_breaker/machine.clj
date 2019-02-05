(ns mastermind.app.code-breaker.machine
  (:require [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]))

(defrecord ^:private CodeBreaker [possible-solutions])

(extend-type CodeBreaker
  domain/CodeBreaker
  (get-next-guess [this] (rand-nth (:possible-solutions this)))
  (notify [this feeback]))

(defn new-code-breaker [level]
  (->CodeBreaker (combination/all-combinations level)))
