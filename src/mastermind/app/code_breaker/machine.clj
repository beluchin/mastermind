(ns mastermind.app.code-breaker.machine
  (:require [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [mastermind.domain.evaluation :as evaluation]))

(declare ->CodeBreaker)

(defn new-code-breaker [level]
  (->CodeBreaker (atom (combination/all-combinations level))))

(defn get-next-guess [cb] (rand-nth @(:possible-solutions cb)))

(declare filter-solutions)

(defn notify
  [cb guess feeback]
  (swap! (:possible-solutions cb) filter-solutions guess feeback))

(defrecord ^:private CodeBreaker [possible-solutions])

(defn ^:private filter-solutions [solutions guess feedback]
  (filter #(= feedback (evaluation/evaluation guess %)) solutions))

(extend-type CodeBreaker
  domain/CodeBreaker
  (get-next-guess [this] (get-next-guess this))
  (notify [this guess feeback] (notify this guess feeback)))


(comment
  (defprotocol P
    (foo [this]))
  (defrecord R [])
  (extend-type R
    P
    (foo [this] :first))
  (foo (->R)) ;; :first
  (extend-type R
    P
    (foo [_] :second))
  (foo (->R)) ;; :first -- the second implementation is silently ignored 

  )
