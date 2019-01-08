(ns mastermind.app.computer-guesses
  (:require [mastermind.domain :as domain]
            [mastermind.app.level :as level]))

(defrecord Game [level]
  domain/Playable
  (get-next-guess [_])
  (get-answer [_ guess])
  (notify [_ guess answer])
  (num-digits [_]))

(defn new-game [] (->Game (:expert level/levels)))
