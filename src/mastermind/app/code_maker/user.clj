(ns mastermind.app.code-maker.user
  (:require [clojure.string :as s]
            [mastermind.app.console :as console]
            [mastermind.app.utils :as utils]
            [mastermind.domain :as domain]
            [validation :as validation]))

(declare ->CodeMaker)

(defn new-code-maker [level] (->CodeMaker level))

(def ^:private errors-in-spanish
  {:default "algo no esta bien"
   :two-tokens "solo 2 resultados: ok y so-so"
   :only-digits "solo digitos, si?"})

(defn ^:private feedback [[ok so-so]] {:ok ok :so-so so-so})

(defn ^:private two-tokens [txt]
  (let [tokens (remove s/blank? (s/split txt #" "))]
    (if-not (= 2 (count tokens))
      {:error :two-tokens}
      tokens)))

(defn ^:private only-digits [tokens]
  (let [integers (map utils/int-or-nil tokens)
        digit? #(<= 0 % 9)]
    (if (every? digit? integers)
      integers
      {:error :only-digits})))

(defn feedback-or-error [txt]
  (validation/ensure-> txt
                       two-tokens
                       only-digits
                       feedback))

(defrecord ^:private CodeMaker [level])

(extend-type CodeMaker 
  domain/CodeMaker
  (get-feedback [_ guess]
    (console/display guess)
    (console/read-until-no-error errors-in-spanish feedback-or-error)))
