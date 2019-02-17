(ns mastermind.app.code-breaker.machine
  (:require [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [mastermind.domain.evaluation :as evaluation]))

(declare ->CodeBreaker)

(defn new-code-breaker [level]
  (->CodeBreaker (atom (combination/all-combinations level))))

(defrecord ^:private CodeBreaker [possible-solutions])

(defn ^:private filter-solutions [solutions guess feedback]
  (filter #(= feedback (evaluation/evaluation guess %)) solutions))

(extend-type CodeBreaker
  domain/CodeBreaker
  (get-next-guess [this] (rand-nth @(:possible-solutions this)))
  (notify [this guess feeback]
    (swap! (:possible-solutions this) filter-solutions guess feeback)))
