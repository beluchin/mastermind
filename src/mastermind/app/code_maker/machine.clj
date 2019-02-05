(ns mastermind.app.code-maker.machine
  (:require [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]
            [mastermind.domain.evaluation :as evaluation]))

(defrecord ^:private CodeMaker [code])

(extend-type CodeMaker
  domain/CodeMaker
  (get-feedback [this guess] (evaluation/evaluation guess (:code this))))

(defn new-code-maker [level] (->CodeMaker (combination/get-combination level)))


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
