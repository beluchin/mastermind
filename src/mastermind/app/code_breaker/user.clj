(ns mastermind.app.code-breaker.user
  (:require [mastermind.app.console :as console]
            [mastermind.app.utils :as utils]
            [mastermind.domain :as domain]
            [validation :as v]))

(def ^:private errors-in-spanish
  {:not-a-number "hmm, solo numeros"
   :zero-in-front "no se permiten ceros al inicio"
   :default "algo no esta bien"})

(defn ^:private an-int [s]
  (let [n (utils/int-or-nil s)]
    (if (nil? n)
      {:error :not-a-number}
      n)))

(defn ^:private no-zero-in-front [s]
  (if (not= \0 (first s))
    s
    {:error :zero-in-front}))

(defn guess-or-error [txt]
  (validation/ensure-> txt
              no-zero-in-front
              an-int))

(defrecord ^:private User [level])

(extend-type User
  domain/CodeBreaker
  (get-next-guess [_] 
    (console/read-until-no-error errors-in-spanish guess-or-error))
  (notify [_ feedback] (console/display feedback)))

(defn new-code-breaker [level] (->User level))
