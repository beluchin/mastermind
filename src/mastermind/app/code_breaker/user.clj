(ns mastermind.app.code-breaker.user
  (:require [mastermind.app.console :as console]
            [mastermind.app.utils :as utils]
            [mastermind.domain.utils :as domain-utils]
            [validation :as v]))

(def ^:private errors-in-spanish
  {:not-a-number "hmm, solo numeros"
   :zero-in-front "no se permiten ceros al inicio"
   :too-many-digits "muchos digitos. Son menos"
   :dups "sin repetidos"
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

; duplicated in combination-test
(defn ^:private num-digits [n]
  (count (str n)))

(defn ^:private correct-number-of-digits [guess level]
  (if (= (:num-digits level) (num-digits guess))
    guess
    {:error :too-many-digits}))

(defn ^:private check-duplicates [n level]
  (if (and (not (:dups level)) (domain-utils/dups? n))
    {:error :dups}
    n))

(defn guess-or-error [level txt]
  (validation/ensure-> txt
              no-zero-in-front
              an-int
              (correct-number-of-digits level)
              (check-duplicates level)))

(defrecord ^:private CodeBreaker [level])

(extend-type CodeBreaker
  domain/CodeBreaker
  (get-next-guess [this]
    (console/read-until-no-error errors-in-spanish
                                 (partial guess-or-error (:level this))))
  (notify [_ feedback] (console/display feedback)))

(defn new-code-breaker [level] (->CodeBreaker level))
