(ns mastermind.app.code-maker.user
  (:require [clojure.string :as s]
            [mastermind.app.console :as console]
            [mastermind.app.utils :as utils]
            [mastermind.domain :as domain]))

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
