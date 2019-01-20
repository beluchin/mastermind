(ns mastermind.app.computer-guesses
  (:require [clojure.string :as s]
            [mastermind.app.console :as console]
            [mastermind.app.level :as level]
            [mastermind.app.utils :as utils]
            [mastermind.domain :as domain]
            [mastermind.domain.combination :as combination]))

(def ^:const default-level (:expert level/levels))

(defn answer-or-error [txt]
  (let [[ok so-so] (map utils/int-or-nil (s/split txt #" "))]
    {:ok ok :so-so so-so}))

(defrecord Game [level possible-solutions])

(extend-type Game
  domain/Playable
  (get-next-guess [game] (rand-nth (:possible-solutions game)))
  (get-answer [_ guess]
    (console/display guess)
    (console/read-until-no-error {} answer-or-error))
  (notify [_ guess answer])
  (num-digits [game] (:num-digits (:level game))))

(defn new-game 
  [] 
  (->Game default-level (combination/all-combinations default-level)))
