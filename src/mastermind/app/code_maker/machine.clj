(ns mastermind.app.code-maker.machine
  (:require [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [mastermind.domain.evaluation :as evaluation]))

(declare ->CodeMaker)

(defn new-code-maker [level] (->CodeMaker (combination/get-combination level)))

(defn get-feedback [cm guess] (evaluation/evaluation guess (:code cm)))

(defrecord ^:private CodeMaker [code])

(extend-type CodeMaker
  domain/CodeMaker
  (get-feedback [this guess] (get-feedback this guess)))


(comment
  ;; maps are functions of the keys
  ({:a 1} :a) ;; 1
  ({:a 1}) ;; error
  ({:a 1} 1) ;; nil

  ;; records? no, records are not functions
  (defrecord T [t])
  ((->T 1) :t) ;; error: T cannot be cast to clojure.lang.IFn
  (:t (->T 1)) ;; 1
  )
