(ns mastermind.app.code-maker.user
  (:require [clojure.string :as s]
            [mastermind.app.console :as console]
            [mastermind.app.utils :as utils]
            [mastermind.domain :as domain]))

(def ^:private errors-in-spanish
  {:not-a-number "hmm, solo numeros"
   :zero-in-front "no se permiten ceros al inicio"
   :too-many-digits "muchos digitos. Son menos"
   :default "algo no esta bien"})

(defrecord ^:private CodeMaker [level])

(defn ^:private answer-or-error [txt]
  (let [[ok so-so] (map utils/int-or-nil (s/split txt #" "))]
    {:ok ok :so-so so-so}))

(extend-type CodeMaker
  domain/CodeMaker
  (get-feedback [_ guess]
    (console/display guess)
    (console/read-until-no-error {} answer-or-error)))

(defn new-code-maker [level] (->CodeMaker level))
